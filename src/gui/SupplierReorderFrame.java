
package gui;


import network.ReorderClient;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import database.ProductDAO;
import model.Product;
import java.util.List;

public class SupplierReorderFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public SupplierReorderFrame() {
        ImageIcon icon = new ImageIcon("logo.png");
        Image img = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        setIconImage(img);
        setTitle("Supplier Reorder Requests");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Background image
        ImageIcon bgIcon = new ImageIcon("background3.jpg");
        Image bgImage = bgIcon.getImage().getScaledInstance(950, 600, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        JLabel title = new JLabel("Supplier Reorder Requests", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        model = new DefaultTableModel(
            new String[]{"Product ID", "Product Name", "Current Qty", "Minimum Stock", "Requested Qty"},
            0
    );

    table = new JTable(model);
    table.setRowHeight(28);
    table.setFont(new Font("SansSerif", Font.PLAIN, 14));
    table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
    
    table.setSelectionBackground(new Color(214, 176, 186)); // وردي
    table.setSelectionForeground(Color.BLACK);

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 90, 20, 90));
    scrollPane.setOpaque(false); 
    scrollPane.getViewport().setOpaque(true); 
    scrollPane.getViewport().setBackground(Color.WHITE);

    JButton sendButton = new JButton("Send Reorder Request");
    JButton backButton = new JButton("Back");

    styleButton(sendButton);
    styleButton(backButton);

    sendButton.addActionListener(e -> sendReorderRequest());

    backButton.addActionListener(e -> {
        dispose();
        new HomeFrame().setVisible(true);
    });

    JPanel buttonPanel = new JPanel();
    buttonPanel.setOpaque(false);
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
    buttonPanel.add(sendButton);
    buttonPanel.add(backButton);

    add(title, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    loadLowStockProducts();       
}
    
    private void loadLowStockProducts() {
        model.setRowCount(0);

        try {
        ProductDAO dao = new ProductDAO();
        List<Product> lowStockList = dao.getLowStock();

        for (Product p : lowStockList) {
            int requestedQty = (p.getMinStock() * 2) - p.getQuantity();

            model.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getQuantity(),
                    p.getMinStock(),
                    requestedQty
            });
        }

        if (lowStockList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No low stock products found.");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading low stock products: " + e.getMessage());
    }
}
    private void sendReorderRequest() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product first.");
            return;
        }

        String productId = model.getValueAt(selectedRow, 0).toString();
        String productName = model.getValueAt(selectedRow, 1).toString();
        int currentQty = Integer.parseInt(model.getValueAt(selectedRow, 2).toString());
        int minStock = Integer.parseInt(model.getValueAt(selectedRow, 3).toString());
        int requestedQty = Integer.parseInt(model.getValueAt(selectedRow, 4).toString());

        String response = ReorderClient.sendReorderRequest(
                productId,
                productName,
                currentQty,
                minStock,
                requestedQty
        );

        JOptionPane.showMessageDialog(this, response);
    }
    private void styleButton(JButton b) {
    b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    b.setPreferredSize(new Dimension(170, 28));
    b.setFocusPainted(false);
}
    
}
