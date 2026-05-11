package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateProductsTable {
       
        // Method to create the products table
        public static void createProductsTable() {
       
            try {
            // Connects to the inventory_db database
            String url = "jdbc:mysql://localhost:3306/inventory_db";
            
            // Establish connection with MySQL
            Connection conn = DriverManager.getConnection(url, "root", "root");

            // Create Statement object to execute SQL queries
            Statement stmt = conn.createStatement();

            // SQL query to create products table
            String createTable = "CREATE TABLE IF NOT EXISTS products ("
                    + "id VARCHAR(10) PRIMARY KEY, "
                    + "name VARCHAR(50), "
                    + "category VARCHAR(50), "
                    + "price DOUBLE, "
                    + "quantity INT, "
                    + "min_stock INT)";

            // Execute SQL query
            stmt.executeUpdate(createTable);

            System.out.println("Products table created successfully!");

            // Close database connection
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
