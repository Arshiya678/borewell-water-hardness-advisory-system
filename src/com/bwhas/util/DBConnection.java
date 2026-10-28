package com.bwhas.util;

import com.bwhas.exception.DatabaseConnectionException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton utility class that manages the JDBC connection.
 * Configure DB_URL, DB_USER, and DB_PASSWORD as environment variables.
 */
public class DBConnection {

    private static final String DB_URL = envOrDefault("DB_URL", "jdbc:mysql://localhost:3306/bwhas_db");
    private static final String DB_USER = envOrDefault("DB_USER", "root");
    private static final String DB_PASS = System.getenv("DB_PASSWORD");

    private static Connection connection;

    private DBConnection() {
    }

    private static String envOrDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }

    public static Connection getConnection() {
        try {
            if (DB_PASS == null || DB_PASS.isBlank()) {
                throw new DatabaseConnectionException(
                    "DB_PASSWORD environment variable is not set. Configure your MySQL credentials before running BWHAS.");
            }
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DatabaseConnectionException("Could not connect to bwhas_db", e);
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
