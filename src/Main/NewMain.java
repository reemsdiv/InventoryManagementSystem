package Main;

import database.CreateDatabase;
import database.CreateProductsTable;
import database.CreateUsersTable;
import gui.LoginFrame;
import javax.swing.*;

public class NewMain {

    public static void main(String[] args) {

        // Initialize database and tables
        CreateDatabase.createDatabase();
        CreateProductsTable.createProductsTable();
        CreateUsersTable.createUsersTable();   // ← new: sets up users table + default admin

        // Launch GUI (Login screen)
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}