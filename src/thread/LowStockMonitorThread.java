package thread;

import database.ProductDAO;
import model.Product;
import java.util.List;
import javax.swing.JOptionPane;

/** 
 * 
 * @author Jory 
 */
public class LowStockMonitorThread extends Thread {

    private volatile boolean running = true;
    private final ProductDAO productDAO;
    private final long intervalMillis;

    // Constructor
    public LowStockMonitorThread(long intervalSeconds) {
        this.productDAO = new ProductDAO();
        this.intervalMillis = intervalSeconds * 1000;
    }

    public void stopMonitoring() {
        running = false;
        this.interrupt();
    }

    @Override
    public void run() {

        while (running && !Thread.currentThread().isInterrupted()) {
            try {

                List<Product> lowStockList = productDAO.getLowStock();

                if (lowStockList != null && !lowStockList.isEmpty()) {

                    StringBuilder message = new StringBuilder();
                    message.append("LOW STOCK ALERT!\n\n");

                    for (Product p : lowStockList) {
                        message.append("ID: ").append(p.getId())
                               .append(" | Name: ").append(p.getName())
                               .append(" | Qty: ").append(p.getQuantity())
                               .append("\n");
                    }

                    message.append("\nCheck inventory immediately!");

                    JOptionPane.showMessageDialog(
                            null,
                            message.toString(),
                            "Low Stock Warning",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

                Thread.sleep(intervalMillis);

            } catch (InterruptedException e) {
                running = false;
                Thread.currentThread().interrupt();

            } catch (Exception e) {
                System.err.println("LowStockMonitorThread Error: " + e.getMessage());
            }
        }
    }
}