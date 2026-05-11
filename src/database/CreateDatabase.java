package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateDatabase {
    
      // Method to create the inventory database
      public static void createDatabase() {
          
        try {
            // Database server URL
            String url = "jdbc:mysql://localhost:3306";
            
            // Establish connection to MySQL server
            Connection conn = DriverManager.getConnection(url, "root", "root");
        
            // Create Statement object to execute SQL queries
            Statement stmt = conn.createStatement();

            // SQL query to create database if it does not already exist
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS inventory_db");

            System.out.println("Database created successfully!");

            // Close database connection
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }  
}

    
    

