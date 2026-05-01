package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/inventory_db";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static Connection connect() {
    try {
        return DriverManager.getConnection(URL, USER, PASS);
    } catch (SQLException e) {
        throw new RuntimeException("Database connection failed!", e);
    }
  }
}