package model;

import database.ProductDAO;
import java.util.List;

/**
 *
 * @author remys
 */
public class ProductManager {
    private ProductDAO productDAO;

    public ProductManager() {
        this.productDAO = new ProductDAO();
    }
    
    //Add Product
    public void addProduct(String id, String name, String category, String price, String quantity, String minStock) throws Exception {

        validateProduct(id, name, category, price, quantity, minStock);

        if (productDAO.search(id) != null) {
            throw new Exception("A product with ID " + id + " already exists.");
        }

        Product p = new Product(id, name, category,
                Double.parseDouble(price),
                Integer.parseInt(quantity),
                Integer.parseInt(minStock));

        productDAO.add(p);
    }
    
    //Update Product
    public void updateProduct(String id, String name, String category, String price, String quantity, String minStock)
        throws Exception {

        validateProduct(id, name, category, price, quantity, minStock);
        if (productDAO.search(id) == null) {
            throw new Exception("Product with ID " + id + " does not exist.");
        }
        
        Product p = new Product(
                id,
                name,
                category,
                Double.parseDouble(price),
                Integer.parseInt(quantity),
                Integer.parseInt(minStock)
        );

        productDAO.update(p);
    }
    
    //Update Stock Quanitiy
    public void updateStock(String id, int quantity, boolean isAdding) throws Exception {

        if (id == null || id.trim().isEmpty()) {
            throw new Exception("Product ID cannot be empty.");
        }

        //Check product exists
        Product product = productDAO.search(id.trim());
        if (product == null) {
            throw new Exception("No product found with ID: " + id.trim());
        }

        //Validate quantity input
        if (quantity <= 0) {
            throw new Exception("Quantity must be greater than zero.");
        }

        //Calculate new quantity based on add or reduce
        int newQuantity;
        if (isAdding) {
            newQuantity = product.getQuantity() + quantity;
        } else {
            newQuantity = product.getQuantity() - quantity;
            if (newQuantity < 0) {
                throw new Exception("Cannot reduce stock below zero. Current stock: "
                        + product.getQuantity());
            }
        }

        //Save to database
        productDAO.updateQuantity(id.trim(), newQuantity);
    }

    
    //Delete Product
    public void deleteProduct(String id) throws Exception {
        if (id == null || id.trim().isEmpty()) {
            throw new Exception("Please select a product to delete.");
        }
        if (productDAO.search(id) == null) {
            throw new Exception("Product with ID " + id + " does not exist.");
        }
        productDAO.delete(id);
    }
     
    //Search Product
    public Product searchProduct(String id) throws Exception {

        Product product = productDAO.search(id.trim());

        if (product == null) {
            throw new Exception("No product found with ID: " + id.trim());
        }

        return product;
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
    
    //Get All Products
    public List<Product> getAllProducts() throws Exception {
        return productDAO.getAll();
    }
    
    //Get Low Stock Products
    public List<Product> getLowStockProducts() throws Exception {
        return productDAO.getLowStock();
    }
    
    //Stock Status Classification
    public String classifyStockStatus(Product p) {
        if (p.getQuantity() <= 0) {
            return "Out of Stock";
        } else if (p.getQuantity() <= p.getMinStock()) {
            return "Low Stock";
        } else {
            return "In Stock";
        }
    }
    
    
    //Vaildate Product
    public void validateProduct(String id, String name, String category,String price, String quantity, String minStock) throws Exception {
        
        if (id.isEmpty() || name.isEmpty() || category.isEmpty() ||
                price.isEmpty() || quantity.isEmpty() || minStock.isEmpty()) {
            throw new Exception("All fields are required.");
        }

        try {
            double p = Double.parseDouble(price);
            if (p < 0) throw new Exception("Price cannot be negative.");
        } catch (NumberFormatException e) {
            throw new Exception("Price must be a valid number.");
        }

        try {
            int q = Integer.parseInt(quantity);
            if (q < 0) throw new Exception("Quantity cannot be negative.");
        } catch (NumberFormatException e) {
            throw new Exception("Quantity must be a whole number.");
        }

        try {
            int m = Integer.parseInt(minStock);
            if (m < 0) throw new Exception("Minimum stock cannot be negative.");
        } catch (NumberFormatException e) {
            throw new Exception("Minimum stock must be a whole number.");
        }
    }
    
}
