package model;

public class Admin {
    public static void main(String[] args) {
        // Example admin user creation
        int userId = 1;
        String name = "RYAN-admin";
        String email = "RYAN-admin";
        String password = "RYAN-sdm24!";

        // Pass 'true' for isAdmin since this is an admin user
        User adminUser = new User(userId, name, email, password, true);

        System.out.println("Admin User Created: " + adminUser.getName());
    }
}
