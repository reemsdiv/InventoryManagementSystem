/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventorymanagement;

/**
 *
 * @author User
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateProductsTable {
        public static void createProductsTable() {

        try {
            String url = "jdbc:mysql://localhost:3306/inventory_db";
            Connection conn = DriverManager.getConnection(url, "root", "reemwa");

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
