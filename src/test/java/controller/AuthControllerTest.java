package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class AuthControllerTest {
    private AuthController authController;

    @BeforeEach
    void setup() throws SQLException {
        // Clear database before tests
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM reservations");
            stmt.execute("DELETE FROM users");
        }
        authController = new AuthController();
    }

    @Test
    void testRegisterUser_Success() {
        assertTrue(authController.register("Mike", "mike@example.com", "password123"),
                "User should be registered successfully");
    }

    @Test
    void testRegisterUser_DuplicateEmail() {
        authController.register("Mike", "mike@example.com", "password123");
        assertFalse(authController.register("Mike", "mike@example.com", "password123"),
                "Duplicate email registration should fail");
    }

    @Test
    void testLogin_Success() {
        authController.register("Jane", "jane@example.com", "password456");
        assertNotNull(authController.login("jane@example.com", "password456"),
                "User should log in successfully");
    }

    @Test
    void testLogin_Failure() {
        assertNull(authController.login("invalid@example.com", "wrongpassword"),
                "Invalid login should fail");
    }
}

