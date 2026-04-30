package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateDatabase {
      public static void createDatabase() {
          
        try {
            String url = "jdbc:mysql://localhost:3306";
            Connection conn = DriverManager.getConnection(url, "root", "1234");

            Statement stmt = conn.createStatement();

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS inventory_db");

            System.out.println("Database created successfully!");

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }  
}

    
    

