package controller;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/resetpassword")
public class ResetPasswordServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;

    @Override
    public void init() { userDAO = new UserDAO(); }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        if (token == null || token.isEmpty()) {
        	request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }

        try {
            User user = userDAO.getUserByResetToken(token);
            if (user == null) {
                request.setAttribute("error", "Invalid or expired token.");
                request.getRequestDispatcher("/WEB-INF/resetpassword.jsp").forward(request, response);
                return;
            }

            request.setAttribute("token", token);
            request.getRequestDispatcher("/WEB-INF/resetpassword.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/WEB-INF/login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        try {
            User user = userDAO.getUserByResetToken(token);
            if (user == null) {
                request.setAttribute("error", "Invalid or expired token.");
                request.getRequestDispatcher("/WEB-INF/resetpassword.jsp").forward(request, response);
                return;
            }

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            userDAO.updatePassword(user.getId(), hashedPassword);

            request.setAttribute("success", "Password updated successfully. You can now login.");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Something went wrong.");
            request.getRequestDispatcher("/WEB-INF/resetpassword.jsp").forward(request, response);
        }
    }
}
