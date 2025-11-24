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

    // Register a new user (password should already be hashed before calling)
    public void register(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email, password, role, birthdate) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword()); // hashed password
            stmt.setString(4, user.getRole());

            if (user.getBirthdate() != null) {
                stmt.setDate(5, user.getBirthdate());
            } else {
                stmt.setNull(5, Types.DATE);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // duplicate email
                throw new SQLException("duplicate");
            }
            throw e;
        }
    }

    // Validate login by email + raw password
    public User validateLogin(String email, String rawPassword) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");

                // ✅ Compare raw password with hashed one
                if (BCrypt.checkpw(rawPassword, hashedPassword)) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(hashedPassword);
                    user.setRole(rs.getString("role"));
                    user.setBirthdate(rs.getDate("birthdate"));
                    return user;
                }
            }
        }
        return null;
    }
}
