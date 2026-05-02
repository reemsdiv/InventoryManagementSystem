package thread;

import database.ProductDAO;
import model.Product;
import java.util.List;
import javax.swing.JOptionPane;
import model.ProductManager;

public class LowStockMonitorThread extends Thread {
 
    private final LowStockListener listener;
 
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
        try {
            Thread.sleep(1000);
 
            ProductManager pm = new ProductManager();
            List<Product> lowStock = pm.getLowStockProducts();
 
            if (!lowStock.isEmpty()) {
                listener.onLowStockDetected(lowStock);
            }
 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("[LowStockMonitor] Error: " + e.getMessage());
        }
    }
}