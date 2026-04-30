package model;

import database.ProductDAO;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author remys
 */
public class ReportGenerator {
    private ProductManager productManager;
    private static final DateTimeFormatter FILE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private ProductDAO productDAO;

    public ReportGenerator(ProductManager productManager) {
        this.productManager = productManager;
    }
    
    //Stock Reports
    public String generateStockReport() throws Exception {

        LocalDateTime timeNow = LocalDateTime.now();
        List<Product> products = productManager.getAllProducts();
        String fileName = "Stock_Report_" + timeNow.format(FILE_FORMATTER) + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            writer.write("========================================");
            writer.newLine();
            writer.write("           STOCK REPORT");
            writer.newLine();
            writer.write("Date: " + timeNow.format(DISPLAY_FORMATTER));
            writer.newLine();
            writer.write("========================================");
            writer.newLine();
            writer.newLine();

            writer.write(String.format("%-10s %-20s %-15s %-10s %-8s %-10s %-12s",
                    "ID", "Name", "Category", "Price", "Qty", "Min Stock", "Status"));
            writer.newLine();
            writer.write("-----------------------------------------------------------------------");
            writer.newLine();

            for (Product p : products) {
                writer.write(String.format("%-10s %-20s %-15s %-10.2f %-8d %-10d %-12s",
                        p.getId(),
                        p.getName(),
                        p.getCategory(),
                        p.getPrice(),
                        p.getQuantity(),
                        p.getMinStock(),
                        productManager.classifyStockStatus(p)));
                writer.newLine();
            }

            writer.newLine();
            writer.write("----------------------------------------");
            writer.newLine();
            writer.write(String.format("Total Products       : %d", products.size()));
            writer.newLine();
            writer.write(String.format("Total Inventory Value: $%.2f",
                    productManager.calculateTotalInventoryValue()));
            writer.newLine();
            writer.write("========================================");
            writer.newLine();

        } catch (IOException e) {
            throw new Exception("Failed to write stock report: " + e.getMessage());
        }

        return fileName;
    }
    
    //Low Stock Reports
    public String generateLowStockReport() throws Exception {

        LocalDateTime timeNow = LocalDateTime.now();
        List<Product> lowStock = productManager.getLowStockProducts();
        String fileName = "LowStock_Report_" + timeNow.format(FILE_FORMATTER) + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            writer.write("========================================");
            writer.newLine();
            writer.write("         LOW STOCK REPORT");
            writer.newLine();
            writer.write("Date: " + timeNow.format(DISPLAY_FORMATTER));
            writer.newLine();
            writer.write("========================================");
            writer.newLine();
            writer.newLine();

            if (lowStock.isEmpty()) {
                writer.write("No low stock products found.");
                writer.newLine();
            } else {
                writer.write(String.format("%-10s %-20s %-8s %-10s %-12s",
                        "ID", "Name", "Qty", "Min Stock", "Status"));
                writer.newLine();
                writer.write("----------------------------------------------------");
                writer.newLine();

                for (Product p : lowStock) {
                    writer.write(String.format("%-10s %-20s %-8d %-10d %-12s",
                            p.getId(),
                            p.getName(),
                            p.getQuantity(),
                            p.getMinStock(),
                            productManager.classifyStockStatus(p)));
                    writer.newLine();
                }
            }

            writer.newLine();
            writer.write("----------------------------------------");
            writer.newLine();
            writer.write(String.format("Total Low Stock Products: %d", lowStock.size()));
            writer.newLine();
            writer.write("========================================");
            writer.newLine();

        } catch (IOException e) {
            throw new Exception("Failed to write low stock report: " + e.getMessage());
        }

        return fileName;
    }
    
    //Daily Reports
    public String generateDailySalesReport() throws Exception {

        LocalDateTime timeNow = LocalDateTime.now();
        List<Product> products = productManager.getAllProducts();
        String fileName = "DailySales_Report_" + timeNow.format(FILE_FORMATTER) + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            writer.write("========================================");
            writer.newLine();
            writer.write("       DAILY SALES REPORT");
            writer.newLine();
            writer.write("Date: " + timeNow.format(DISPLAY_FORMATTER));
            writer.newLine();
            writer.write("========================================");
            writer.newLine();
            writer.newLine();

            writer.write(String.format("%-10s %-20s %-10s %-12s",
                    "ID", "Name", "Price", "Total Value"));
            writer.newLine();
            writer.write("----------------------------------------------------");
            writer.newLine();

            for (Product p : products) {
                writer.write(String.format("%-10s %-20s %-10.2f %-12.2f",
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getPrice() * p.getQuantity()));
                writer.newLine();
            }

            writer.newLine();
            writer.write("----------------------------------------");
            writer.newLine();
            writer.write(String.format("Total Products       : %d", products.size()));
            writer.newLine();
            writer.write(String.format("Total Inventory Value: $%.2f",
                    productManager.calculateTotalInventoryValue()));
            writer.newLine();
            writer.write("========================================");
            writer.newLine();

        } catch (IOException e) {
            throw new Exception("Failed to write daily sales report: " + e.getMessage());
        }

        return fileName;
    }
    
}
