package model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


// This class is responsible for generating different types of reports:
//      (stock report, daily sales report, and low stock report)

public class ReportGenerator {

    private ProductManager productManager;

    private static final DateTimeFormatter FILE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ReportGenerator(ProductManager productManager) {
        this.productManager = productManager;
    }

    
    private String center(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }

    private final String LINE = "------------------------------------------------------------";

    
    // STOCK REPORT - Generates a full stock report including all products and inventory value
    public String getStockReport() throws Exception {

        StringBuilder sb = new StringBuilder();

        LocalDateTime now = LocalDateTime.now();
        List<Product> products = productManager.getAllProducts();

        sb.append(LINE).append("\n");
        sb.append(center("STOCK REPORT", LINE.length())).append("\n");
        sb.append(center("Date: " + now.format(DISPLAY_FORMATTER), LINE.length())).append("\n");
        sb.append(LINE).append("\n\n");

        sb.append(String.format("%-8s %-9s %-12s %-8s %-6s %-6s %-6s\n",
                "ID", "Name", "Category", "Price", "Qty", "Min", "Status"));

        sb.append(LINE).append("\n");

        for (Product p : products) {
            sb.append(String.format("%-8s %-9s %-12s %-8.2f %-6d %-6d %-6s\n",
                    p.getId(),
                    p.getName(),
                    p.getCategory(),
                    p.getPrice(),
                    p.getQuantity(),
                    p.getMinStock(),
                    productManager.classifyStockStatus(p)));
        }

        sb.append("\n").append(LINE).append("\n");
        sb.append("Total Products: ").append(products.size()).append("\n");
        sb.append(String.format("Total Inventory Value: $%.2f",
                productManager.calculateTotalInventoryValue())).append("\n");
        sb.append(LINE);

        return sb.toString();
    }
    
    // DAILY SALES REPORT - Generates a daily sales report based on product price and quantity
    public String getDailySalesReport() throws Exception {

        StringBuilder sb = new StringBuilder();

        LocalDateTime now = LocalDateTime.now();
        List<Product> products = productManager.getAllProducts();

        sb.append(LINE).append("\n");
        sb.append(center("DAILY SALES REPORT", LINE.length())).append("\n");
        sb.append(center("Date: " + now.format(DISPLAY_FORMATTER), LINE.length())).append("\n");
        sb.append(LINE).append("\n\n");

        sb.append(String.format("%-8s %-9s %-8s %-12s\n",
                "ID", "Name", "Price", "Total"));

        sb.append(LINE).append("\n");

        for (Product p : products) {
            sb.append(String.format("%-8s %-9s %-8.2f %-12.2f\n",
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getPrice() * p.getQuantity()));
        }

        sb.append("\n").append(LINE).append("\n");
        sb.append("Total Products: ").append(products.size()).append("\n");
        sb.append(String.format("Total Inventory Value: $%.2f",
                productManager.calculateTotalInventoryValue())).append("\n");
        sb.append(LINE);

        return sb.toString();
    }

    // LOW STOCK REPORT - Generates a report for products that are below minimum stock level
    public String getLowStockReport() throws Exception {

        StringBuilder sb = new StringBuilder();

        LocalDateTime now = LocalDateTime.now();
        List<Product> lowStock = productManager.getLowStockProducts();

        sb.append(LINE).append("\n");
        sb.append(center("LOW STOCK REPORT", LINE.length())).append("\n");
        sb.append(center("Date: " + now.format(DISPLAY_FORMATTER), LINE.length())).append("\n");
        sb.append(LINE).append("\n\n");

        sb.append(String.format("%-8s %-9s %-6s %-6s %-6s\n",
                "ID", "Name", "Qty", "Min", "Status"));

        sb.append(LINE).append("\n");

        if (lowStock.isEmpty()) {
            sb.append("No low stock products.\n");
        } else {
            for (Product p : lowStock) {
                sb.append(String.format("%-8s %-9s %-6d %-6d %-6s\n",
                        p.getId(),
                        p.getName(),
                        p.getQuantity(),
                        p.getMinStock(),
                        productManager.classifyStockStatus(p)));
            }
        }

        sb.append("\n").append(LINE).append("\n");
        sb.append("Total Low Stock Products: ").append(lowStock.size()).append("\n");
        sb.append(LINE);

        return sb.toString();
    }

    // Saves the report to a text file
    public void saveToFile(String content, String fileName) throws Exception {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        } catch (IOException e) {
            throw new Exception("Error saving file: " + e.getMessage());
        }
    }
}