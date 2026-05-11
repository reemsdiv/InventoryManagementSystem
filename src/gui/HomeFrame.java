package gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import model.ProductManager;
import thread.LowStockMonitorThread;

public class HomeFrame extends JFrame {
   
private static boolean alertShown = false;    // Prevents multiple low stock alert threads from starting

    public HomeFrame() {
        // Set application icon
        setAppIcon("logo.png");
        setTitle("Home");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Close application when window is closed
        setLocationRelativeTo(null);   // Center frame on screen

        // =========================
        // Background Image
        // =========================

        // Load background image
        ImageIcon bgIcon = new ImageIcon("background3.jpg");
        Image bgImage = bgIcon.getImage().getScaledInstance(950, 600, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        // =========================
        // Logo Section
        // =========================

        // Load logo image
        ImageIcon logoIcon = new ImageIcon("logo.png");
        Image logoImg = logoIcon.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImg));   // Display logo
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(50, 380, 10, 380));

        // =========================
        // Subtitle
        // =========================
        JLabel subtitle = new JLabel("What would you like to do?", JLabel.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        subtitle.setBorder(BorderFactory.createEmptyBorder(10, 340, 5, 30));

        // =========================
        // Top Panel
        // =========================
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(logoLabel);
        topPanel.add(subtitle);
        add(topPanel, BorderLayout.NORTH);

        // =========================
        // Center Buttons Panel
        // =========================
        JPanel centerPanel = new JPanel(new GridLayout(2, 3, 25, 25));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 180, 40, 180));    // Add padding around buttons
        Border btnBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
        Font btnsFont = new Font("SansSerif", Font.BOLD, 16);

        // =========================
        // Create Buttons
        // =========================
        JButton btnProducts = createButton("Products", "MngProduct.png", btnsFont, btnBorder);
        JButton btnStock = createButton("Stock", "QrCode.png", btnsFont, btnBorder);
        JButton btnLowStock = createButton("View Low Stock", "Warning.png", btnsFont, btnBorder);
        JButton btnReports = createButton("Reports", "ReportIcon.png", btnsFont, btnBorder);
        JButton btnReorder = createButton("Reorder Items", "Supplier.png", btnsFont, btnBorder);

       
        // =========================
        // Button Functionalities
        // =========================
        btnProducts.addActionListener(e -> {     // Open products management screen
            dispose();
            new ManageProductsFrame().setVisible(true);
        });

        btnStock.addActionListener(e -> {        // Open stock management screen
            dispose();
            new ManageStockFrame().setVisible(true);
        });

        btnLowStock.addActionListener(e -> {     // Open low stock products screen
            dispose();
            new LowStockFrame().setVisible(true);
        });

        btnReports.addActionListener(e -> {      // Open reports screen
            dispose();
            new ReportsFrame().setVisible(true);
        });
        
        btnReorder.addActionListener(e -> {      // Open reorder items screen
           dispose();
           new SupplierReorderFrame().setVisible(true);
        });
        
        // Add buttons to center panel
        centerPanel.add(btnProducts);
        centerPanel.add(btnStock);
        centerPanel.add(btnLowStock);
        centerPanel.add(btnReports);
        centerPanel.add(btnReorder);

        add(centerPanel, BorderLayout.CENTER);

        
        // =========================
        // Logout Section
        // =========================
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
       
        // Create logout button
        JButton btnLogout = createButton("Log Out", "logout.png",
                new Font("SansSerif", Font.PLAIN, 14), null);

        btnLogout.setOpaque(false);
        btnLogout.setContentAreaFilled(false);
        btnLogout.setBorderPainted(false);

        // Logout button functionality
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );
            
            // If user confirms logout
            if (confirm == JOptionPane.YES_OPTION) {   
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
        
        // Add logout button
        bottomPanel.add(btnLogout);
        
        add(bottomPanel, BorderLayout.SOUTH);


        // =========================
        // Transparency Settings
        // =========================
        topPanel.setOpaque(false);
        centerPanel.setOpaque(false);
        bottomPanel.setOpaque(false);
        
        // Display frame
        setVisible(true);
        
        // =========================
        // Low Stock Monitoring Thread
        // =========================

        // Start only one monitoring thread
        if (!alertShown) {
            // Create monitoring thread
            LowStockMonitorThread monitor = new LowStockMonitorThread(products ->
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(    // Display warning message
                        this,
                        products.size() + " product(s) are low on stock!\nGo to 'View Low Stock' for details.",
                        "Low Stock Warning",
                        JOptionPane.WARNING_MESSAGE
                    );
                })
            );
            // Start thread
            monitor.start();
           
            alertShown = true; // Mark as started so we don't launch multiple threads
        }
    }
    
    // =========================
    // Method to Create Buttons
    // =========================
    private JButton createButton(String text, String iconPath, Font font, Border border) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image img = icon.getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH);
        JButton button = new JButton(text, new ImageIcon(img));

        button.setFont(font);   // Set font
        button.setBackground(Color.WHITE);   // Set background color
      
        // Apply border if exists
        if (border != null)
            button.setBorder(border);
        return button;
    }
    
    // =========================
    // Method to Set App Icon
    // =========================
    private void setAppIcon(String path) {
        try {
            ImageIcon icon = new ImageIcon(path);
            setIconImage(icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        } catch (Exception ignored) {  
            // Ignore icon loading errors
        }
    }
}