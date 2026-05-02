package Main;

import database.CreateDatabase;
import database.CreateProductsTable;
import gui.LoginFrame;
import javax.swing.*;


public class NewMain {

      public static void main(String[] args) {
         
        // Initialize database
          CreateDatabase.createDatabase();
          CreateProductsTable.createProductsTable();
        
         // Launch GUI (Login screen)
         // Username: admin | Password: 1234
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        

       
    }
}