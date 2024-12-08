import controller.AuthController;
import controller.AdminController;
import controller.UserController;
import model.User;
import view.MenuView;
import util.DatabaseConnection;

import java.sql.Connection;
import java.util.Scanner;

/**
 * The entry point of the application, handling the main program flow and user interaction.
 */
public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            AuthController authController = new AuthController();
            boolean isRunning = true;

            while (isRunning) {
                int roleChoice = MenuView.displayRoleChoice();
                switch (roleChoice) {
                    case 1 -> handleAdminLogin(authController, connection);
                    case 2 -> handleUserProcess(authController, connection);
                    case 3 -> {
                        System.out.println("Exiting... Thank you for using RYAN-Movies!");
                        isRunning = false;
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleAdminLogin(AuthController authController, Connection connection) {
        System.out.print("Enter Admin Username: ");
        String username = new java.util.Scanner(System.in).nextLine();
        System.out.print("Enter Admin Password: ");
        String password = new java.util.Scanner(System.in).nextLine();

        if ("RYAN-admin".equals(username) && "RYAN-sdm24!".equals(password)) {
            System.out.println("Admin Login successful! Welcome RYAN-admin");
            AdminController adminController = new AdminController(connection);
            handleAdminActions(adminController);
        } else {
            System.out.println("Not an admin account!");
        }
    }

    private static void handleAdminActions(AdminController adminController) {
        boolean adminRunning = true;
        while (adminRunning) {
            System.out.println("\n===== Admin Options =====");
            System.out.println("1. Add New Movie");
            System.out.println("2. Remove Movie");
            System.out.println("3. View Bookings");
            System.out.println("4. View Movies");
            System.out.println("5. Remove Booking");
            System.out.println("6. Exit Admin Panel");
            System.out.print("Select an option: ");

            String option = new Scanner(System.in).nextLine();
            switch (option) {
                case "1" -> adminController.addMovieInteractive();
                case "2" -> adminController.removeMovieInteractive();
                case "3" -> adminController.viewBookings();
                case "4" -> adminController.viewMovies();
                case "5" -> adminController.removeBooking();
                case "6" -> {
                    System.out.println("Exiting Admin Panel...");
                    adminRunning = false;
                }
                default -> System.out.println("Invalid option! Try again.");
            }
        }
    }


    private static void handleUserProcess(AuthController authController, Connection connection) {
        int userChoice = MenuView.displayUserInitialMenu();
        switch (userChoice) {
            case 1 -> registerUser(authController);
            case 2 -> loginUserAndView(authController, connection);
            default -> System.out.println("Invalid option. Try again.");
        }
    }

    private static void registerUser(AuthController authController) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String name, email, password;

        // Validate name input
        while (true) {
            System.out.print("Enter Name: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please enter a valid name.");
            } else {
                break;
            }
        }

        // Validate email input
        while (true) {
            System.out.print("Enter Email: ");
            email = scanner.nextLine().trim();
            if (email.isEmpty()) {
                System.out.println("Email cannot be empty. Please enter a valid email.");
            } else {
                break;
            }
        }

        // Validate password input
        while (true) {
            System.out.print("Enter Password: ");
            password = scanner.nextLine().trim();
            if (password.isEmpty()) {
                System.out.println("Password cannot be empty. Please enter a valid password.");
            } else {
                break;
            }
        }

        // Proceed with registration
        if (authController.register(name, email, password)) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed.");
        }
    }


    private static void loginUserAndView(AuthController authController, Connection connection) {
        System.out.print("Enter Email: ");
        String email = new java.util.Scanner(System.in).nextLine();
        System.out.print("Enter Password: ");
        String password = new java.util.Scanner(System.in).nextLine();

        User user = authController.login(email, password);
        if (user != null) {
            System.out.println("Login successful! Welcome " + user.getName());
            UserController userController = new UserController(connection);
            userController.handleUserActions(user.getUserId()); // Pass userId instead of User object
        } else {
            System.out.println("Invalid login credentials.");
        }
    }
}
