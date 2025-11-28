package model;

import java.sql.Date;
import java.sql.Timestamp;

public class User {
    private int id;            // maps to users.id
    private String name;       // maps to users.name
    private String email;      // maps to users.email
    private String password;   // maps to users.password
    private String role;       // maps to users.role
    private Date birthdate;    // maps to users.birthdate (DATE type in SQL)

    // 🔒 Password reset support
    private String resetToken;         // maps to users.reset_token
    private Timestamp resetTokenExpiry; // maps to users.reset_token_expiry

    public User() {}

    public User(String name, String email, String password, String role, Date birthdate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.birthdate = birthdate;
    }

    // -------------------- Getters & Setters --------------------

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Date getBirthdate() { return birthdate; }
    public void setBirthdate(Date birthdate) { this.birthdate = birthdate; }

    public String getResetToken() { return resetToken; }
    public void setResetToken(String resetToken) { this.resetToken = resetToken; }

    public Timestamp getResetTokenExpiry() { return resetTokenExpiry; }
    public void setResetTokenExpiry(Timestamp resetTokenExpiry) { this.resetTokenExpiry = resetTokenExpiry; }
}
