package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateUsersTable {
   
    // Method to create the users table
    public static void createUsersTable() {
      
        try {
            // Connects to the inventory_db database
            String url = "jdbc:mysql://localhost:3306/inventory_db";
            
            // Establish connection with MySQL
            Connection conn = DriverManager.getConnection(url, "root", "Aa1124728146");

            // Create Statement object to execute SQL queries
            Statement stmt = conn.createStatement();

            // SQL query to create users table
            String createTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "username VARCHAR(20) NOT NULL UNIQUE, "
                    + "password VARCHAR(30) NOT NULL"
                    + ")";

            // Execute create table query
            stmt.executeUpdate(createTable);

            // Insert default admin account
            String insertAdmin = "INSERT IGNORE INTO users (username, password) "
                    + "VALUES ('admin', '1234')";

            // Execute insert query
            stmt.executeUpdate(insertAdmin);

            System.out.println("Users table created successfully!");
           
            // Close database connection
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}