
package inventorymanagement;

import javax.swing.*;

public class NewMain {

      public static void main(String[] args) {
         
        // run database
        CreateDatabase.createDatabase();
        CreateProductsTable.createProductsTable();
        
        // run GUI
        //SwingUtilities.invokeLater(() ->
             //   new LoginFrame().setVisible(true));
    }
}