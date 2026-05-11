package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//This class manages the connection between the application and the database.
public class DBConnection {

    // Database URL
    private static final String URL = "jdbc:mysql://localhost:3306/inventory_db";
    private static final String USER = "root";   // MySQL username
    private static final String PASS = "Aa1124728146";   // MySQL password

    // Method to establish and return database connection
    public static Connection connect() {
    
    try {
        // Create connection with MySQL database
        return DriverManager.getConnection(URL, USER, PASS);
   
    } catch (SQLException e) {
        // Throw runtime exception if connection fails
        throw new RuntimeException("Database connection failed!", e);
    }
  }
}