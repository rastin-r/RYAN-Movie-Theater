package controller;

import model.User;
import util.DatabaseConnection;

import java.sql.*;

/**
 * Handles user authentication including login and registration processes.
 */
public class AuthController {

    /**
     * Authenticates a user based on email and password.
     * @param email The user's email.
     * @param password The user's password.
     * @return A User object if authentication is successful; otherwise, null.
     */
    public User login(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String name = rs.getString("name");
                String userEmail = rs.getString("email");
                String userPassword = rs.getString("password");
                boolean isAdmin = rs.getBoolean("is_admin");

                // Pass the isAdmin flag to the User constructor
                return new User(userId, name, userEmail, userPassword, isAdmin);
            }
        } catch (SQLException e) {
            System.out.println("Error logging in: " + e.getMessage());
        }
        return null;
    }

    /**
     * Registers a new user in the system.
     * @param name The user's name.
     * @param email The user's email.
     * @param password The user's password.
     * @return true if registration is successful, otherwise false.
     */
    public boolean register(String name, String email, String password) {
        String query = "INSERT INTO users (name, email, password, is_admin) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setBoolean(4, false); // Regular users are not admins by default

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        }
        return false;
    }
}




