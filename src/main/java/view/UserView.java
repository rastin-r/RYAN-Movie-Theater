package view;

import model.Movie;

import java.util.List;
import java.util.Scanner;

public class UserView {
    private static final Scanner scanner = new Scanner(System.in);

    public static void displayMovies(List<Movie> movies) {
        System.out.println("\n===== Available Movies =====");
        for (Movie movie : movies) {
            System.out.printf("ID: %d | Title: %s | Showtime: %s\n", movie.getMovieId(), movie.getTitle(), movie.getShowTime());
        }
    }

    public static int getMovieIdInput() {
        System.out.print("Enter the Movie ID: ");
        return scanner.nextInt();
    }

    public static String getSeatSelection() {
        System.out.print("Enter seat number (e.g., 2F): ");
        return scanner.next();
    }

    public static int getDiscountOption() {
        System.out.println("1. Student Discount (20%)");
        System.out.println("2. Retiree Discount (30%)");
        System.out.println("3. No Discount");
        System.out.print("Select your discount option: ");
        return scanner.nextInt();
    }

    public static String getStudentId() {
        System.out.print("Enter your 12-digit Student ID: ");
        return scanner.next();
    }

    public static void displayConfirmation(String movieName, String discountType, int reservationId) {
        System.out.println("Your Ticket has been successfully generated!");
        System.out.printf("Movie: %s, Discount: %s, Reservation Number: %d\n", movieName, discountType, reservationId);
        System.out.println("Thank you for using RYAN-Movies. See you next time! :)");
    }
}