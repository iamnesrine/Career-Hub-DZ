package controller;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;

// ✅ Add these imports at the top of the file
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/forgotpassword")
public class ForgotPasswordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() { userDAO = new UserDAO(); }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/forgotpassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        HttpSession session = request.getSession();

        try {
            User user = userDAO.getUserByEmail(email);
            if (user == null) {
                session.setAttribute("error", "No account exists with this email.");
                request.getRequestDispatcher("/WEB-INF/forgotpassword.jsp").forward(request, response);
                return;
            }

            String token = UUID.randomUUID().toString();
            userDAO.saveResetToken(user.getId(), token);

            String resetLink = request.getScheme() + "://" +
                               request.getServerName() + ":" +
                               request.getServerPort() +
                               request.getContextPath() +
                               "/resetpassword?token=" + token;

            try {
                sendResetEmail(email, resetLink);
            } catch (MessagingException e) {
                e.printStackTrace();
                session.setAttribute("error", "Failed to send email. Please try again.");
                request.getRequestDispatcher("/WEB-INF/forgotpassword.jsp").forward(request, response);
                return;
            }

            session.setAttribute("success", "A reset link has been sent to your email.");
            request.getRequestDispatcher("/WEB-INF/forgotpassword.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "An error occurred.");
            request.getRequestDispatcher("/WEB-INF/forgotpassword.jsp").forward(request, response);
        }
    }

    // ✅ Put this method inside the class (at the bottom is fine)
    private void sendResetEmail(String recipientEmail, String resetLink) throws MessagingException {
        String host = "smtp.gmail.com"; // or your SMTP server
        String from = "kouchihnessrine@gmail.com"; // your email
        String password = "ogrl juvy dxpn qepk"; // Gmail app password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("Password Reset Request");
        message.setText("Hello,\n\nClick the link below to reset your password:\n" + resetLink + "\n\nIf you did not request this, please ignore.");

        Transport.send(message);
        System.out.println("Reset email sent to " + recipientEmail);
    }
}
