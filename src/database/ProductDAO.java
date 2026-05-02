package database;

import model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Add product
    public void add(Product p) throws Exception {
        String sql = "INSERT INTO products VALUES (?,?,?,?,?,?)";
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getId());
            ps.setString(2, p.getName());
            ps.setString(3, p.getCategory());
            ps.setDouble(4, p.getPrice());
            ps.setInt(5, p.getQuantity());
            ps.setInt(6, p.getMinStock());
            ps.executeUpdate();
        }
    }

    // Get all products 
    public List<Product> getAll() throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection con = DBConnection.connect();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Product(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getInt("min_stock")
                ));
            }
        }
        return list;
    }

    // Search product by ID
    public Product search(String id) throws Exception {
        String sql = "SELECT * FROM products WHERE id=?";
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("category"),
                            rs.getDouble("price"),
                            rs.getInt("quantity"),
                            rs.getInt("min_stock")
                    );
                }
            }
        }
        return null;
    }

    // Delete product
    public void delete(String id) throws Exception {
        String sql = "DELETE FROM products WHERE id=?";
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    // Update product
    public void update(Product p) throws Exception {
        String sql = "UPDATE products SET name=?, category=?, price=?, quantity=?, min_stock=? WHERE id=?";
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getCategory());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getQuantity());
            ps.setInt(5, p.getMinStock());
            ps.setString(6, p.getId());
            ps.executeUpdate();
        }
    }

    // Update quantity only
    public void updateQuantity(String id, int qty) throws Exception {
        String sql = "UPDATE products SET quantity=? WHERE id=?";
        try (Connection con = DBConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, qty);
            ps.setString(2, id);
            ps.executeUpdate();
        }
    }

    // Low stock product
    public List<Product> getLowStock() throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE quantity <= min_stock";
        try (Connection con = DBConnection.connect();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Product(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getInt("min_stock")
                ));
            }
        }
        return list;
    }
}