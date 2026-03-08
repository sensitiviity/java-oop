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
     * Saves Clothes object to database using PreparedStatement.
     * Handles all subclasses (Pants, Shirts, Jeans, TShirts).
     *
     * @param clothes Clothes instance to save
     * @throws SQLException on database errors
     */
    public void saveClothes(Clothes clothes, int quantity) throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("No active database connection");
        }

        String sql = "insert into clothes (name, color, size, price, brand, material, type, extra1, extra2, quantity) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, clothes.getName());
            pstmt.setString(2, clothes.getColor());
            pstmt.setString(3, clothes.getSize().toString());
            pstmt.setDouble(4, clothes.getPrice());
            pstmt.setString(5, clothes.getBrand());
            pstmt.setString(6, clothes.getMaterial());

            // Set type-specific fields
            if(clothes instanceof Jeans) {
                pstmt.setString(7, "Jeans");
                pstmt.setBoolean(8, ((Jeans) clothes).getHasPockets());
                pstmt.setBoolean(9, ((Jeans) clothes).isRipped());
            } else if(clothes instanceof TShirts) {
                pstmt.setString(7, "TShirts");
                pstmt.setBoolean(8, ((TShirts) clothes).getLongSleeve());
                pstmt.setBoolean(9, ((TShirts) clothes).getHasPrints());
            } else if(clothes instanceof Pants) {
                pstmt.setString(7, "Pants");
                pstmt.setBoolean(8, ((Pants) clothes).getHasPockets());
                pstmt.setNull(9, Types.BOOLEAN);
            } else if(clothes instanceof Shirts) {
                pstmt.setString(7, "Shirts");
                pstmt.setBoolean(8, ((Shirts) clothes).getLongSleeve());
                pstmt.setNull(9, Types.BOOLEAN);
            }
            else {
                pstmt.setString(7, "Unknown");
                pstmt.setNull(8, Types.BOOLEAN);
                pstmt.setNull(9, Types.BOOLEAN);
            }
            pstmt.setInt(10, quantity);
            pstmt.executeUpdate();
            System.out.println("Saved to database.");
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
