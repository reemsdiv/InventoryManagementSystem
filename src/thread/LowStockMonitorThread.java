package thread;

import model.Product;
import java.util.List;
import model.ProductManager;

public class LowStockMonitorThread extends Thread {
 
    private final LowStockListener listener;
    private static final int INTERVAL = 90 * 1000; 

    public interface LowStockListener {
        void onLowStockDetected(List<Product> lowStockProducts);
    }
 
    public LowStockMonitorThread(LowStockListener listener) {
        this.listener = listener;
        setDaemon(true);
        setName("LowStockMonitorThread");
    }
 
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ProductManager pm = new ProductManager();
                List<Product> lowStock = pm.getLowStockProducts();
 
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
                    Thread.sleep(5000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}