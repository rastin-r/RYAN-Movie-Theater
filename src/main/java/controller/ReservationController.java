package controller;

import model.Seat;
import util.DatabaseConnection;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Manages reservations including making new reservations and displaying available seats.
 */
public class ReservationController {
    private final Scanner scanner = new Scanner(System.in);
    private static final LocalDate MIN_DATE = LocalDate.of(2024, 12, 10);
    private static final LocalDate MAX_DATE = LocalDate.of(2024, 12, 31);

    /**
     * Handles the process of making a reservation for a movie.
     * @param userId The ID of the user making the reservation.
     * @param movieId The ID of the movie for which the reservation is made.
     * @param duration The duration of the movie in minutes.
     */
    public void makeReservation(int userId, int movieId, int duration) {
        try {
            // Step 1: Prompt for a valid date
            LocalDate chosenDate = getValidDate();

            // Step 2: Ensure showtimes exist for this date
            ensureShowtimesExistForDate(movieId, chosenDate, duration);

            // Step 3: Fetch and display showtimes for the selected date
            int showtimeId = selectShowtime(movieId, chosenDate);
            if (showtimeId == -1) {
                System.out.println("No available showtimes on this date.");
                return;
            }

            // Step 4: Display available seats
            List<Seat> seats = Seat.initializeSeats();
            displayAvailableSeats(seats);

            // Step 5: Seat selection
            String seatNumber = selectSeat(seats);

            // Step 6: Choose discount
            String discountType = chooseDiscount();
            String discountId = null;
            if ("Student".equals(discountType)) {
                discountId = getValidInput("Enter 12-digit Student ID: ", "\\d{12}");
            } else if ("Retiree".equals(discountType)) {
                discountId = getValidInput("Enter 8-digit Retiree ID: ", "\\d{8}");
            }

            // Step 7: Save reservation
            int reservationId = saveReservation(userId, movieId, showtimeId, chosenDate, seatNumber, discountType, discountId);
            String reservationNumber = generateReservationNumber(reservationId);

            // Step 8: Display success message
            String movieName = getMovieTitle(movieId);
            String showtime = getShowtime(showtimeId);
            System.out.println("\nYour Ticket has been successfully generated!");
            System.out.printf("Movie: %s\nShowtime: %s\nDiscount: %s\nReservation Number: %s\n",
                    movieName, showtime, discountType, reservationNumber);
        } catch (SQLException e) {
            System.out.println("Error making reservation: " + e.getMessage());
        }
    }

    private LocalDate getValidDate() {
        while (true) {
            System.out.print("Enter a date (dd/MM/yyyy, between 10/12/2024 and 31/12/2024): ");
            String input = scanner.nextLine();
            try {
                LocalDate date = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if (!date.isBefore(MIN_DATE) && !date.isAfter(MAX_DATE)) {
                    return date;
                } else {
                    System.out.println("Invalid date! Please pick a date between 10/12/2024 and 31/12/2024.");
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Use dd/MM/yyyy.");
            }
        }
    }

    private void ensureShowtimesExistForDate(int movieId, LocalDate date, int duration) throws SQLException {
        String checkQuery = "SELECT COUNT(*) FROM showtimes WHERE movie_id = ? AND DATE(showtime) = ?";
        String insertQuery = "INSERT INTO showtimes (movie_id, showtime) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            checkStmt.setInt(1, movieId);
            checkStmt.setDate(2, java.sql.Date.valueOf(date));
            ResultSet rs = checkStmt.executeQuery();
            rs.next();

            if (rs.getInt(1) == 0) { // Generate showtimes dynamically
                System.out.println("Generating showtimes for " + date + "...");
                LocalTime startTime = LocalTime.of(8, 0); // Start at 08:00
                while (startTime.plusMinutes(duration).isBefore(LocalTime.of(23, 0))) {
                    LocalDateTime showtime = LocalDateTime.of(date, startTime);
                    insertStmt.setInt(1, movieId);
                    insertStmt.setTimestamp(2, Timestamp.valueOf(showtime));
                    insertStmt.executeUpdate();
                    startTime = startTime.plusMinutes(duration).plusMinutes(10); // Add buffer time
                }
            }
        }
    }

    private int selectShowtime(int movieId, LocalDate date) throws SQLException {
        String query = "SELECT showtime_id, showtime FROM showtimes WHERE movie_id = ? AND DATE(showtime) = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, movieId);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();

            Map<Integer, String> showtimeMap = new HashMap<>();
            while (rs.next()) {
                int id = rs.getInt("showtime_id");
                String time = rs.getTimestamp("showtime").toLocalDateTime().toString();
                showtimeMap.put(id, time);
                System.out.printf("ID: %d | Time: %s\n", id, time);
            }

            if (showtimeMap.isEmpty()) return -1;

            System.out.print("Enter Showtime ID: ");
            int selectedId = Integer.parseInt(scanner.nextLine());
            return showtimeMap.containsKey(selectedId) ? selectedId : -1;
        }
    }

    private void displayAvailableSeats(List<Seat> seats) {
        System.out.println("\n===== Available Seats =====");
        for (Seat seat : seats) {
            if (seat.isAvailable()) {
                System.out.print(seat.getSeatNumber() + " ");
            }
        }
        System.out.println();
    }

    private String selectSeat(List<Seat> seats) {
        while (true) {
            System.out.print("Enter seat number (e.g., 2B): ");
            String seatNumber = scanner.nextLine().toUpperCase();
            if (seats.stream().anyMatch(s -> s.getSeatNumber().equals(seatNumber) && s.isAvailable())) {
                return seatNumber;
            }
            System.out.println("Invalid or unavailable seat. Try again.");
        }
    }

    private String chooseDiscount() {
        while (true) {
            System.out.println("Choose Discount:\n1. Student Discount\n2. Retiree Discount\n3. No Discount");
            System.out.print("Enter choice: ");
            switch (scanner.nextLine()) {
                case "1": return "Student";
                case "2": return "Retiree";
                case "3": return "None";
                default: System.out.println("Invalid input! Try again.");
            }
        }
    }

    private String getValidInput(String prompt, String regex) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.matches(regex)) return input;
            System.out.println("Invalid format. Try again.");
        }
    }

    private int saveReservation(int userId, int movieId, int showtimeId, LocalDate date, String seat, String discount, String discountId) throws SQLException {
        String query = "INSERT INTO reservations (user_id, movie_id, showtime_id, reservation_date, seat_numbers, discount_type, discount_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, movieId);
            stmt.setInt(3, showtimeId);
            stmt.setDate(4, java.sql.Date.valueOf(date));
            stmt.setString(5, seat);
            stmt.setString(6, discount);
            stmt.setString(7, discountId);
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            return keys.next() ? keys.getInt(1) : -1;
        }
    }

    private String generateReservationNumber(int id) {
        return "RYAN-" + String.format("%06d", id);
    }

    private String getMovieTitle(int movieId) throws SQLException {
        String query = "SELECT title FROM movies WHERE movie_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getString("title") : "Unknown";
        }
    }

    private String getShowtime(int showtimeId) throws SQLException {
        String query = "SELECT showtime FROM showtimes WHERE showtime_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, showtimeId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getTimestamp("showtime").toString() : "Unknown";
        }
    }
}
