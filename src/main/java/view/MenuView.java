package view;

import java.util.Scanner;

/**
 * Handles the display and interaction of the initial menu choices for the application.
 */
public class MenuView {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the role choice menu and captures the user's selection.
     * @return the integer representing the user's role choice
     */
    public static int displayRoleChoice() {
        System.out.println("\n===== Welcome to RYAN-Movies =====");
        System.out.println("1. Admin");
        System.out.println("2. User");
        System.out.println("3. Exit");
        System.out.print("Select your role: ");
        return scanner.nextInt();
    }

    /**
     * Displays the initial user menu for login or registration options.
     * @return the user's choice as an integer
     */
    public static int displayUserInitialMenu() {
        System.out.println("\n===== User Options =====");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Select an option: ");
        return scanner.nextInt();
    }
}


