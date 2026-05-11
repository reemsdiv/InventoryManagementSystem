package gui;

import network.ReorderClient;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import database.ProductDAO;
import model.Product;
import model.ReorderService;
import java.util.List;

public class SupplierReorderFrame extends JFrame {
    // Table used to display low-stock products
private JTable table;

// Table model for managing table data
private DefaultTableModel model;

public SupplierReorderFrame() {

    // Set application logo icon
    ImageIcon icon = new ImageIcon("logo.png");
    Image img = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
    setIconImage(img);

    // Frame settings
    setTitle("Supplier Reorder Requests");
    setSize(950, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // Set background image
    ImageIcon bgIcon = new ImageIcon("background3.jpg");
    Image bgImage = bgIcon.getImage().getScaledInstance(950, 600, Image.SCALE_SMOOTH);
    JLabel background = new JLabel(new ImageIcon(bgImage));
    background.setLayout(new BorderLayout());
    setContentPane(background);

    // Title label
    JLabel title = new JLabel("Supplier Reorder Requests", JLabel.CENTER);
    title.setFont(new Font("SansSerif", Font.BOLD, 24));
    title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

    // Create table model and allow editing only for Requested Qty column
    model = new DefaultTableModel(
            new String[]{"Product ID", "Product Name", "Current Qty", "Minimum Stock", "Requested Qty"},
            0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 4;
        }
    };

    // Create and style table
    table = new JTable(model);
    table.setRowHeight(28);
    table.setFont(new Font("SansSerif", Font.PLAIN, 14));
    table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

    // Set table selection colors
    table.setSelectionBackground(new Color(214, 176, 186));
    table.setSelectionForeground(Color.BLACK);

    // Create scroll pane for table
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 90, 20, 90));
    scrollPane.setOpaque(false);
    scrollPane.getViewport().setOpaque(true);
    scrollPane.getViewport().setBackground(Color.WHITE);

    // Create buttons
    JButton sendButton = new JButton("Send Reorder Request");
    JButton backButton = new JButton("Back");

    // Apply custom button style
    styleButton(sendButton);
    styleButton(backButton);

    // Send reorder request when button is clicked
    sendButton.addActionListener(e -> sendReorderRequest());

    // Return to HomeFrame
    backButton.addActionListener(e -> {
        dispose();
        new HomeFrame().setVisible(true);
    });

    // Panel for buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.setOpaque(false);
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
    buttonPanel.add(sendButton);
    buttonPanel.add(backButton);

    // Add components to frame
    add(title, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    // Load low-stock products from database
    loadLowStockProducts();
}

    // Load low-stock products into the table
    private void loadLowStockProducts() {
    model.setRowCount(0);

    try {
        ProductDAO dao = new ProductDAO();
        List<Product> lowStockList = dao.getLowStock();

        for (Product p : lowStockList) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getQuantity(),
                    p.getMinStock(),
                    ""
            });
        }

        // Show message if no low-stock products exist
        if (lowStockList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No low stock products found.");
        }

    } catch (Exception e) {

        // Handle database loading errors
        JOptionPane.showMessageDialog(this, "Error loading low stock products: " + e.getMessage());
    }
}

    // Send reorder request to supplier server
    private void sendReorderRequest() {

    // Save edited cell value before processing
    if (table.isEditing()) {
        table.getCellEditor().stopCellEditing();
    }

    int selectedRow = table.getSelectedRow();

    // Validate product selection
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a product and enter the requested quantity.");
        return;
    }

    // Retrieve product data from selected row
    String productId = model.getValueAt(selectedRow, 0).toString();
    String productName = model.getValueAt(selectedRow, 1).toString();

    int currentQty = Integer.parseInt(model.getValueAt(selectedRow, 2).toString());
    int minStock = Integer.parseInt(model.getValueAt(selectedRow, 3).toString());

    // Get requested quantity entered by user
    String requestedQtyText = model.getValueAt(selectedRow, 4).toString();

    // Process reorder request using ReorderService
    ReorderService service = new ReorderService();

    String response = service.processReorder(
            productId,
            productName,
            currentQty,
            minStock,
            requestedQtyText
    );

    // Display server response message
    JOptionPane.showMessageDialog(
            this,
            response,
            "Success",
            JOptionPane.INFORMATION_MESSAGE
    );
}

    // Apply custom style to buttons
    private void styleButton(JButton b) {
    b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    b.setPreferredSize(new Dimension(170, 28));
    b.setFocusPainted(false);
}        
}
