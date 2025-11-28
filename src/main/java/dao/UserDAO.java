package dao;

import model.User;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }

        String url = "jdbc:mysql://localhost:3306/careerhubdz_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "nesrine2003/mysql";

        return DriverManager.getConnection(url, user, password);
    }

    // ------------------- EXISTING METHODS -------------------
    public void register(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email, password, role, birthdate) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            if (user.getBirthdate() != null) {
                stmt.setDate(5, user.getBirthdate());
            } else {
                stmt.setNull(5, Types.DATE);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) throw new SQLException("duplicate");
            throw e;
        }
    }

    public User validateLogin(String email, String rawPassword) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                if (BCrypt.checkpw(rawPassword, hashedPassword)) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(hashedPassword);
                    user.setRole(rs.getString("role"));
                    user.setBirthdate(rs.getDate("birthdate"));
                    user.setResetToken(rs.getString("reset_token"));
                    user.setResetTokenExpiry(rs.getTimestamp("reset_token_expiry"));
                    return user;
                }
            }
        }
        return null;
    }

    // ------------------- PASSWORD RESET METHODS -------------------

    // Find user by email
    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setBirthdate(rs.getDate("birthdate"));
                user.setResetToken(rs.getString("reset_token"));
                user.setResetTokenExpiry(rs.getTimestamp("reset_token_expiry"));
                return user;
            }
        }
        return null;
    }

    // Save reset token + expiry
    public void saveResetToken(int userId, String token) throws SQLException {
        String sql = "UPDATE users SET reset_token = ?, reset_token_expiry = DATE_ADD(NOW(), INTERVAL 30 MINUTE) WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    // Get user by token (only if not expired)
    public User getUserByResetToken(String token) throws SQLException {
        String sql = "SELECT * FROM users WHERE reset_token = ? AND reset_token_expiry > NOW()";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setBirthdate(rs.getDate("birthdate"));
                user.setResetToken(rs.getString("reset_token"));
                user.setResetTokenExpiry(rs.getTimestamp("reset_token_expiry"));
                return user;
            }
        }
        return null;
    }

    // Update password and clear token
    public void updatePassword(int userId, String newHashedPassword) throws SQLException {
        String sql = "UPDATE users SET password = ?, reset_token = NULL, reset_token_expiry = NULL WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newHashedPassword);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }
}

