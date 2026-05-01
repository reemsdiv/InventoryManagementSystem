package gui;

import database.CreateDatabase;
import database.CreateProductsTable;
import javax.swing.*;
import model.ProductManager;
import thread.LowStockMonitorThread;

public class NewMain {

      public static void main(String[] args) {
         
        // run database
//        CreateDatabase.createDatabase();
//        CreateProductsTable.createProductsTable();
        
        // run thread 
        LowStockMonitorThread monitor = new LowStockMonitorThread(products ->
            SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(
                    null,
                    products.size() + " product(s) are low on stock!\nGo to 'View Low Stock' for details.",
                    "Low Stock Warning",
                    JOptionPane.WARNING_MESSAGE
                )
            )
        );
        monitor.start();

        // run GUI
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}