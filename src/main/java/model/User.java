package model;

public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private boolean isAdmin;

    // Constructor
    public User(int userId, String name, String email, String password, boolean isAdmin) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}

