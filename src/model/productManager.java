package model;

import database.ProductDAO;
import java.util.List;


// Handles business logic for managing products, including validation and interaction with the database

public class ProductManager {
    
    // Data Access Object for performing CRUD operations on the products database
    private ProductDAO productDAO;

    public ProductManager() {
        this.productDAO = new ProductDAO();
    }
    
    //Add Product
    public void addProduct(String id, String name, String category, String price, String quantity, String minStock) throws Exception {

        // Validate all input fields before proceeding
        validateProduct(id, name, category, price, quantity, minStock);

        // Prevent duplicate entries by checking if the ID is already in use
        if (productDAO.search(id) != null) {
            throw new Exception("A product with ID " + id + " already exists.");
        }

        // Create a new Product object
        Product p = new Product(id, name, category,
                Double.parseDouble(price),
                Integer.parseInt(quantity),
                Integer.parseInt(minStock));

        // Add Product to the DB
        productDAO.add(p);
    }
    
    //Update Product
    public void updateProduct(String id, String name, String category, String price, String quantity, String minStock)
        throws Exception {
        
        // Validate all input fields before proceeding
        validateProduct(id, name, category, price, quantity, minStock);
        
        // Ensure the product exists before attempting an update
        if (productDAO.search(id) == null) {
            throw new Exception("Product with ID " + id + " does not exist.");
        }
        
        // Build the updated Product object
        Product p = new Product(
                id,
                name,
                category,
                Double.parseDouble(price),
                Integer.parseInt(quantity),
                Integer.parseInt(minStock)
        );

        // Save the updated product details to the DB
        productDAO.update(p);
    }
    
    //Update Stock Quanitiy
    //Adjusts the stock quantity of an existing product by adding or reducing a given amount.
    public void updateStock(String id, int quantity, boolean isAdding) throws Exception {

        // Ensure a product ID was provided
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
        
        // Ensure a product ID was provided
        if (id == null || id.trim().isEmpty()) {
            throw new Exception("Please select a product to delete.");
        }
        
        // Check if the product exists before attempting deletion
        if (productDAO.search(id) == null) {
            throw new Exception("Product with ID " + id + " does not exist.");
        }
        
        // Remove the product from the database
        productDAO.delete(id);
    }
     
    //Search Product
    //Searches for a product by its ID and returns the matching Product object.
    public Product searchProduct(String id) throws Exception {

        // Query the database with a trimmed ID to avoid whitespace issues
        Product product = productDAO.search(id.trim());

        if (product == null) {
            throw new Exception("No product found with ID: " + id.trim());
        }

        return product;
    }
    
    //Total Inventory Value
    //Calculates the total value of all inventory.
    //Total value = sum of (price × quantity) for each product.
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
    //Classifies the stock status based on the product current quantity relative to its minimum stock threshold.
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
        
        // Ensure no field has been left blank
        if (id.isEmpty() || name.isEmpty() || category.isEmpty() ||
                price.isEmpty() || quantity.isEmpty() || minStock.isEmpty()) {
            throw new Exception("All fields are required.");
        }

        // Validate price: must be and non-negative
        try {
            double p = Double.parseDouble(price);
            if (p < 0) throw new Exception("Price cannot be negative.");
        } catch (NumberFormatException e) {
            throw new Exception("Price must be a valid number.");
        }

        // Validate quantity: must be an integer and non-negative
        try {
            int q = Integer.parseInt(quantity);
            if (q < 0) throw new Exception("Quantity cannot be negative.");
        } catch (NumberFormatException e) {
            throw new Exception("Quantity must be a whole number.");
        }

        // Validate minimum stock: must be an integer and non-negative
        try {
            int m = Integer.parseInt(minStock);
            if (m < 0) throw new Exception("Minimum stock cannot be negative.");
        } catch (NumberFormatException e) {
            throw new Exception("Minimum stock must be a whole number.");
        }
    }  
}