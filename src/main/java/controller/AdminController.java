package controller;

import java.sql.*;
import java.util.Scanner;

/**
 * This class handles the administrative tasks related to movies and bookings in the system.
 */
public class AdminController {
    private final Connection connection;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs an AdminController with a database connection.
     *
     * @param connection the database connection used for all database operations
     */
    public AdminController(Connection connection) {
        this.connection = connection;
    }

    /**
     * Interactively adds a new movie to the database.
     */
    public void addMovieInteractive() {
        try {
            String title = getNonEmptyInput("Enter Movie Title: ");
            String description = getNonEmptyInput("Enter Description: ");
            String genre = getNonEmptyInput("Enter Genre: ");
            String cast = getNonEmptyInput("Enter Cast: ");
            String music = getNonEmptyInput("Enter Music: ");
            String screenplay = getNonEmptyInput("Enter Screenplay: ");
            int duration = getValidIntInput("Enter Duration (minutes): ");

            String query = "INSERT INTO movies (title, description, genre, cast, music, screenplay, duration) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, title);
                stmt.setString(2, description);
                stmt.setString(3, genre);
                stmt.setString(4, cast);
                stmt.setString(5, music);
                stmt.setString(6, screenplay);
                stmt.setInt(7, duration);

                stmt.executeUpdate();
                System.out.println("\nMovie successfully added!");
            }
        } catch (SQLException e) {
            System.out.println("Error adding movie: " + e.getMessage());
        }
    }

    /**
     * Interactively removes a movie from the database by its ID.
     */
    public void removeMovieInteractive() {
        try {
            int movieId = getValidIntInput("Enter the Movie ID to remove: ");
            String query = "DELETE FROM movies WHERE movie_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, movieId);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Movie successfully removed!");
                } else {
                    System.out.println("No movie found with the given ID.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error removing movie: " + e.getMessage());
        }
    }

    /**
     * Displays all current bookings in a formatted manner.
     */
    public void viewBookings() {
        String query = """
                SELECT r.reservation_id, u.name AS user_name, m.title AS movie_title, 
                       s.showtime, r.seat_numbers, r.discount_type, r.discount_id
                FROM reservations r
                JOIN users u ON r.user_id = u.user_id
                JOIN movies m ON r.movie_id = m.movie_id
                JOIN showtimes s ON r.showtime_id = s.showtime_id
                """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n===== All Bookings =====");
            while (rs.next()) {
                System.out.printf("Reservation ID: %d | User: %s | Movie: %s | Showtime: %s | Seat: %s | Discount: %s | Discount ID: %s\n",
                        rs.getInt("reservation_id"), rs.getString("user_name"), rs.getString("movie_title"),
                        rs.getTimestamp("showtime").toString(), rs.getString("seat_numbers"),
                        rs.getString("discount_type"), rs.getString("discount_id"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving bookings: " + e.getMessage());
        }
    }

    // Remove Booking by ID
    public void removeBooking() {
        try {
            int reservationId = getValidIntInput("Enter the Reservation ID to remove: ");
            String query = "DELETE FROM reservations WHERE reservation_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, reservationId);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Booking successfully removed!");
                } else {
                    System.out.println("No booking found with the given ID.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error removing booking: " + e.getMessage());
        }
    }

    // View All Movies
    public void viewMovies() {
        String query = "SELECT * FROM movies";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n===== All Movies =====");
            while (rs.next()) {
                System.out.printf("ID: %d | Title: %s | Duration: %d minutes\n",
                        rs.getInt("movie_id"), rs.getString("title"), rs.getInt("duration"));
                System.out.printf("Description: %s | Genre: %s\n", rs.getString("description"), rs.getString("genre"));
                System.out.println("-----------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving movies: " + e.getMessage());
        }
    }

    // Helper method to get non-empty input
    private String getNonEmptyInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    // Helper method to get valid integer input
    private int getValidIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid integer.");
            }
        }
    }
}