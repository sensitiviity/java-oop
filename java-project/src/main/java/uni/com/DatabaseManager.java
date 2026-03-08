package uni.com;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Manages database connection and clothes persistence.
 * Handles INSERT operations after object creation.
 */
public class DatabaseManager {
    private Connection connection;
    private final String url;
    private final String user;
    private final String password;

    /**
     * Loads database configuration from properties file.
     *
     * @param configPath Path to db.properties file
     * @throws IOException if config file cannot be read
     */
    public DatabaseManager(String configPath) throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(configPath)) {
            props.load(fis);
        }
        this.url = props.getProperty("db.url");
        this.user = props.getProperty("db.user");
        this.password = props.getProperty("db.password");

        if (url == null || user == null || password == null) {
            throw new IOException("Missing required properties: db.url, db.user, db.password");
        }
    }

    /**
     * Establishes database connection.
     *
     * @throws SQLException if connection fails
     */
    public void connect() throws SQLException {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database successfully.");
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    /**
     * Closes database connection.
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
