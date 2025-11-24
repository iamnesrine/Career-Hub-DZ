package controller;

import java.io.IOException;
import java.sql.SQLException;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ Move error from session to request (so it shows only once)
        HttpSession session = request.getSession(false);
        if (session != null) {
            String error = (String) session.getAttribute("error");
            if (error != null) {
                request.setAttribute("error", error);
                session.removeAttribute("error"); // clear after showing
            }
        }

        request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Collect form fields
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role"); // seeker | organization
        String bd = request.getParameter("birthdate");

        java.sql.Date birthdate = null;
        if (bd != null && !bd.isEmpty()) {
            birthdate = java.sql.Date.valueOf(bd); // yyyy-MM-dd format
        }

        // ✅ Require birthdate if role = seeker
        if ("seeker".equals(role) && birthdate == null) {
            HttpSession session = request.getSession();
            session.setAttribute("error", "Les chercheurs doivent fournir leur date de naissance.");
            response.sendRedirect("register");
            return;
        }

        // Build User bean
        User user = new User(name, email, password, role, birthdate);

        UserDAO dao = new UserDAO();
        try {
            dao.register(user);

            // SUCCESS → redirect to login page
            response.sendRedirect("login");

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
