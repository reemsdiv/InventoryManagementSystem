package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateProductsTable {
        public static void createProductsTable() {
            
        try {
            String url = "jdbc:mysql://localhost:3306/inventory_db";
            Connection conn = DriverManager.getConnection(url, "root", "1234");

            Statement stmt = conn.createStatement();

            String createTable = "CREATE TABLE IF NOT EXISTS products ("
                    + "id VARCHAR(10) PRIMARY KEY, "
                    + "name VARCHAR(50), "
                    + "category VARCHAR(50), "
                    + "price DOUBLE, "
                    + "quantity INT, "
                    + "min_stock INT)";

            stmt.executeUpdate(createTable);

            System.out.println("Products table created successfully!");

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
