package controller;

import org.junit.jupiter.api.*;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationControllerTest {

    private ReservationController reservationController;
    private Connection connection;

    @BeforeAll
    public void setup() throws Exception {
        connection = DatabaseConnection.getConnection();
        reservationController = new ReservationController();
        cleanupDatabase();
        setupTestData();
    }

    @AfterAll
    public void tearDown() throws Exception {
        cleanupDatabase();
        if (connection != null) connection.close();
    }

    private void cleanupDatabase() throws Exception {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM reservations")) {
            stmt.executeUpdate();
        }
    }

    private void setupTestData() throws Exception {
        // Insert a sample movie and showtime for reservation testing
        try (PreparedStatement movieStmt = connection.prepareStatement(
                "INSERT INTO movies (title, description, genre, duration) VALUES ('Test Movie', 'Test Description', 'Action', 120)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            movieStmt.executeUpdate();
            ResultSet rs = movieStmt.getGeneratedKeys();
            if (rs.next()) {
                int movieId = rs.getInt(1);

                try (PreparedStatement showtimeStmt = connection.prepareStatement(
                        "INSERT INTO showtimes (movie_id, showtime) VALUES (?, '2024-12-10 10:00:00')",
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
                    showtimeStmt.setInt(1, movieId);
                    showtimeStmt.executeUpdate();
                }
            }
        }
    }

    @Test
    @Order(1)
    public void testMakeReservation() {
        System.out.println("Running testMakeReservation...");

        int userId = 1; // Assume this user exists in the test DB
        int movieId = 1; // Movie inserted in setup
        int showtimeId = 1; // Showtime inserted in setup

        // Ensure no exception is thrown
        assertDoesNotThrow(() -> reservationController.makeReservation(userId, movieId, showtimeId));

        // Verify that reservation is saved in the database
        String query = "SELECT * FROM reservations WHERE user_id = ? AND movie_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, movieId);
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next(), "Reservation should exist in the database.");
        } catch (Exception e) {
            fail("Error verifying reservation in database: " + e.getMessage());
        }
    }
}

