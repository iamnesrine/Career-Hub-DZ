package controller;

import dao.UserDAO; // access to the data (database)
import model.User;  // represents the user

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*; // HTTP requests/responses and sessions
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login") // servlet accessible via URL /login
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Show login form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Just forward to login.jsp (errors are request-scoped now)
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    // Handle login submission
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        try {
            User user = userDAO.validateLogin(email, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // ✅ Redirect instead of forward (PRG pattern)
                request.getRequestDispatcher("/WEB-INF/mainpage.jsp").forward(request, response);
            } else {
                // ✅ Error only in request scope (disappears on refresh)
                request.setAttribute("error", "Invalid email or password");

                // Forward back to login.jsp (error shows once)
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error during login", e);
        }
    }
}