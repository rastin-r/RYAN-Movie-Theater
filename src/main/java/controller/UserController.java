package controller;

import java.sql.*;
import java.util.Scanner;

/**
 * Manages user actions within the system, such as viewing and managing reservations.
 */
public class UserController {
    private final Scanner scanner = new Scanner(System.in);
    private final Connection connection;

    /**
     * Constructs a UserController with a database connection.
     *
     * @param connection the database connection used for all database operations
     */
    public UserController(Connection connection) {
        this.connection = connection;
    }

    /**
     * Handles the main user menu interactions.
     * @param userId The ID of the user performing actions.
     */
    public void handleUserActions(int userId) {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n===== User Menu =====");
            System.out.println("1. View Now Showing and Leaving Soon");
            System.out.println("2. View My Reservations");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> displayMovies(userId);
                case "2" -> viewReservations(userId);
                case "3" -> {
                    System.out.println("Logging out... Goodbye!");
                    isRunning = false;
                }
                default -> System.out.println("Invalid option! Try again.");
            }
        }
    }

    private void displayMovies(int userId) {
        try {
            System.out.println("\n===== Now Showing =====");
            displayMovieCategory(0, 3);

            System.out.println("\n===== Leaving Soon =====");
            displayMovieCategory(3, 5);

            System.out.print("Enter the Movie ID to view details or 0 to return: ");
            int movieId = Integer.parseInt(scanner.nextLine());
            if (movieId == 0) return;

            int duration = displayMovieDetails(movieId);
            if (duration == -1) return;

            System.out.print("Ready to book? (yes/no): ");
            if ("yes".equalsIgnoreCase(scanner.nextLine().trim())) {
                proceedWithBooking(userId, movieId, duration);
            } else {
                System.out.println("Returning to menu...");
            }
        } catch (SQLException e) {
            System.out.println("Error displaying movies: " + e.getMessage());
        }
    }

    private void displayMovieCategory(int start, int limit) throws SQLException {
        String query = "SELECT movie_id, title FROM movies LIMIT ?, ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, start);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.printf("ID: %d | Title: %s\n", rs.getInt("movie_id"), rs.getString("title"));
            }
        }
    }

    private int displayMovieDetails(int movieId) throws SQLException {
        String query = "SELECT * FROM movies WHERE movie_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("\n===== Movie Details =====");
                System.out.printf("Title: %s\n", rs.getString("title"));
                System.out.printf("Description: %s\n", rs.getString("description"));
                System.out.printf("Genre: %s\n", rs.getString("genre"));
                System.out.printf("Cast: %s\n", rs.getString("cast"));
                System.out.printf("Music: %s\n", rs.getString("music"));
                System.out.printf("Screenplay: %s\n", rs.getString("screenplay"));
                System.out.printf("Duration: %d minutes\n", rs.getInt("duration"));
                return rs.getInt("duration");
            } else {
                System.out.println("Invalid Movie ID. Returning to menu...");
                return -1;
            }
        }
    }

    private void proceedWithBooking(int userId, int movieId, int duration) {
        ReservationController reservationController = new ReservationController();
        reservationController.makeReservation(userId, movieId, duration);
    }

    private void viewReservations(int userId) {
        String query = """
            SELECT r.reservation_id, m.title AS movie_title, s.showtime, r.seat_numbers, r.discount_type, r.discount_id
            FROM reservations r
            JOIN movies m ON r.movie_id = m.movie_id
            JOIN showtimes s ON r.showtime_id = s.showtime_id
            WHERE r.user_id = ?
            """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n===== Your Reservations =====");
            if (!rs.isBeforeFirst()) {
                System.out.println("You have no reservations.");
                return;
            }

            while (rs.next()) {
                int reservationId = rs.getInt("reservation_id");
                String movieTitle = rs.getString("movie_title");
                String showtime = rs.getTimestamp("showtime").toString();
                String discountType = rs.getString("discount_type");
                String discountId = rs.getString("discount_id");

                // Generate reservation number based on reservation ID
                String reservationNumber = String.format("RYAN-%06d", reservationId);

                // Display reservation details
                System.out.printf("Movie: %s\n", movieTitle);
                System.out.printf("Showtime: %s\n", showtime);
                System.out.printf("Discount: %s\n", discountType);
                if (discountId != null && !discountId.isEmpty()) {
                    System.out.printf("ID: %s\n", discountId);
                }
                System.out.printf("Reservation Number: %s\n", reservationNumber);
                System.out.println("-----------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving reservations: " + e.getMessage());
        }
    }

}