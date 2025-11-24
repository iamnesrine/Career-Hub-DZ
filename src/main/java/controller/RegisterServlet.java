package controller;

import java.io.IOException;
import java.sql.SQLException;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import org.mindrot.jbcrypt.BCrypt;   // ✅ Add this import

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            String error = (String) session.getAttribute("error");
            if (error != null) {
                request.setAttribute("error", error);
                session.removeAttribute("error");
            }
        }

        request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String rawPassword = request.getParameter("password"); // raw password
        String role = request.getParameter("role");
        String bd = request.getParameter("birthdate");

        java.sql.Date birthdate = null;
        if (bd != null && !bd.isEmpty()) {
            birthdate = java.sql.Date.valueOf(bd);
        }

        if ("seeker".equals(role) && birthdate == null) {
            HttpSession session = request.getSession();
            session.setAttribute("error", "Les chercheurs doivent fournir leur date de naissance.");
            response.sendRedirect("register");
            return;
        }

        // ✅ Hash the password before saving
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));

        User user = new User(name, email, hashedPassword, role, birthdate);

        UserDAO dao = new UserDAO();
        try {
            dao.register(user);

            // ✅ Put success message in session, then forward to login
            HttpSession session = request.getSession();
            session.setAttribute("success", "Account created successfully! Please log in.");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

        } catch (SQLException e) {
            HttpSession session = request.getSession();
            if ("duplicate".equals(e.getMessage())) {
                session.setAttribute("error", "Un compte avec cet email existe déjà.");
            } else {
                session.setAttribute("error", "Une erreur est survenue. Veuillez réessayer.");
            }
            response.sendRedirect("register");
        }
    }
}