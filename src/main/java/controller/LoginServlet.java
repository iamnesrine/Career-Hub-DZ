package controller;

import dao.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Move success/error from session to request (so they show only once)
        HttpSession session = request.getSession(false);
        if (session != null) {
            String success = (String) session.getAttribute("success");
            if (success != null) {
                request.setAttribute("success", success);
                session.removeAttribute("success");
            }
            String error = (String) session.getAttribute("error");
            if (error != null) {
                request.setAttribute("error", error);
                session.removeAttribute("error");
            }
        }

        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String rawPassword = request.getParameter("password"); // ✅ raw password

        UserDAO userDAO = new UserDAO();
        try {
            // ✅ DAO will check rawPassword against hashed password in DB
            User user = userDAO.validateLogin(email, rawPassword);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // Forward to main page (no redirect)
                request.getRequestDispatcher("/WEB-INF/mainpage.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error during login", e);
        }
    }
}