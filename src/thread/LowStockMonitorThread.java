package thread;

import model.Product;
import java.util.List;
import model.ProductManager;

/**
 * This thread continuously monitors product stock levels
 * and notifies a listener when low-stock products are detected.
 */
public class LowStockMonitorThread extends Thread {
    
    // Listener interface used to send alerts to the GUI 
    private final LowStockListener listener;
    
    // Time interval between each stock check (90 seconds)
    private static final int INTERVAL = 90 * 1000; 

    //Listener interface for handling low stock events.
    public interface LowStockListener {
        void onLowStockDetected(List<Product> lowStockProducts);
    }
 
    public LowStockMonitorThread(LowStockListener listener) {
        this.listener = listener;
        
        // Run as daemon so it stops automatically when the app closes
        setDaemon(true);
        
        setName("LowStockMonitorThread");
    }
 
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ProductManager pm = new ProductManager();   // Create ProductManager to access product data
                List<Product> lowStock = pm.getLowStockProducts();   // Retrieve products that are below stock threshold
 
                // If low stock products exist, notify the listener
                if (!lowStock.isEmpty()) {
                    listener.onLowStockDetected(lowStock);
                }
                // Wait for 90 seconds before the next check
                Thread.sleep(INTERVAL);
 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
               
                try {
                    Thread.sleep(5000); //delay
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}