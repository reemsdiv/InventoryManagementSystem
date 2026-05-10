package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateUsersTable {

    public static void createUsersTable() {
        try {
            String url = "jdbc:mysql://localhost:3306/inventory_db";
            Connection conn = DriverManager.getConnection(url, "root", "root");

            Statement stmt = conn.createStatement();

            String createTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "username VARCHAR(20) NOT NULL UNIQUE, "
                    + "password VARCHAR(30) NOT NULL"
                    + ")";

            stmt.executeUpdate(createTable);

            //insert admin
            String insertAdmin = "INSERT IGNORE INTO users (username, password) "
                    + "VALUES ('admin', '1234')";

            stmt.executeUpdate(insertAdmin);

            System.out.println("Users table created successfully!");
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}