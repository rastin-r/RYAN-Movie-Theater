package view;

import java.util.Scanner;

/**
 * Provides the view layer for admin-specific interactions.
 */
public class AdminView {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Prompts for and captures new movie details from the admin.
     * @return an array of Strings containing the movie title and show time.
     */
    public static String[] getNewMovieDetails() {
        System.out.print("Enter Movie Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Show Time (yyyy-MM-ddTHH:mm): ");
        String showtime = scanner.nextLine();
        return new String[]{title, showtime};
    }

    /**
     * Prompts for and captures the movie ID for removal.
     * @return the movie ID as an integer.
     */
    public static int getMovieIdInput() {
        System.out.print("Enter the Movie ID to remove: ");
        return scanner.nextInt();
    }
}

