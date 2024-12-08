package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides a centralized method to retrieve a database connection using JDBC.
 */
public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://sdm24-db.cryoawqmgfhw.eu-north-1.rds.amazonaws.com:3306/sdm_db?user=admin";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "RYAN-sdm24!";

    /**
     * Creates and returns a connection to the database.
     * @return a Connection object to the configured database
     * @throws SQLException if a database access error occurs or the url is null
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
