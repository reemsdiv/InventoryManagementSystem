package model;

import database.ProductDAO;
import java.util.List;

/**
 *
 * @author remys
 */
public class productManager {
    private ProductDAO productDAO;

    public productManager() {
        this.productDAO = new ProductDAO();
    }

    //Total Inventory Value
    public double calculateTotalInventoryValue() throws Exception {
        List<Product> products = productDAO.getAll();
        double total = 0;
        for (Product p : products) {
            total += ( p.getPrice() * p.getQuantity() ); 
        }
        return total;
    }
    
    //Low Stock Products
    public List<Product> getLowStockProducts() throws Exception {
        return productDAO.getLowStock();
    }
    
    
    //Search Product
    public Product searchProduct(String id) throws Exception {

        Product product = productDAO.search(id.trim());

        if (product == null) {
            throw new Exception("No product found with ID: " + id.trim());
        }

        return product;
    }
    
}
