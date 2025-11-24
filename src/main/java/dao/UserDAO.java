package dao;

import model.User;
import java.sql.*;

public class UserDAO {

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }

        // Use your actual DB name and credentials
        String url = "jdbc:mysql://localhost:3306/careerhubdz_db?useSSL=false&serverTimezone=UTC";
        String user = "root";          // change if needed
        String password = "nesrine2003/mysql"; // change if needed

        return DriverManager.getConnection(url, user, password);
    }

    // Register a new user
    public void register(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email, password, role, birthdate) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());

            if (user.getBirthdate() != null) {
                stmt.setDate(5, user.getBirthdate()); // java.sql.Date
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

    // Validate login by email + password
    public User validateLogin(String email, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setBirthdate(rs.getDate("birthdate"));
                return user;
            }
        }

        return null;
    }
}
