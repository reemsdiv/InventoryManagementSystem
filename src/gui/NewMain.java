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
        
        // run GUI
//        SwingUtilities.invokeLater(() ->
//        new LoginFrame().setVisible(true));
        
        // run thread
        ProductManager manager = new ProductManager();

        LowStockMonitorThread monitor =
                new LowStockMonitorThread(60); // 60 seconds

        monitor.setDaemon(true);
        monitor.start();

        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}