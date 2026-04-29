package inventorymanagement;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

public class HomeFrame extends JFrame {

    public HomeFrame() {

        setAppIcon("logo.png");
        setTitle("Home");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //background image
        ImageIcon bgIcon = new ImageIcon("background3.jpg");
        Image bgImage = bgIcon.getImage().getScaledInstance(950, 600, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        //logo
        ImageIcon logoIcon = new ImageIcon("logo.png");
        Image logoImg = logoIcon.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImg));
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(50, 380, 10, 380));

        //subtitle
        JLabel subtitle = new JLabel("What would you like to do?", JLabel.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        subtitle.setBorder(BorderFactory.createEmptyBorder(10, 340, 5, 30));

        //combine everything
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(logoLabel);
        topPanel.add(subtitle);
        add(topPanel, BorderLayout.NORTH);

        //center buttons (SAME VALUES)
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 25, 25));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 180, 40, 180));

        Border btnBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
        Font btnsFont = new Font("SansSerif", Font.BOLD, 16);

        JButton btnProducts = createButton("Products", "MngProduct.png", btnsFont, btnBorder);
        JButton btnStock = createButton("Stock", "QrCode.png", btnsFont, btnBorder);
        JButton btnLowStock = createButton("View Low Stock", "Warning.png", btnsFont, btnBorder);
        JButton btnReports = createButton("Reports", "ReportIcon.png", btnsFont, btnBorder);

       
        btnProducts.addActionListener(e -> {
            dispose();
            new ManageProductsFrame3().setVisible(true);
        });

        btnStock.addActionListener(e -> {
            dispose();
            new ManageStockFrame().setVisible(true);
        });

        btnLowStock.addActionListener(e -> {
            dispose();
            new LowStockFrame().setVisible(true);
        });

        btnReports.addActionListener(e -> {
            dispose();
            new ReportsFrameUI().setVisible(true);
        });

        centerPanel.add(btnProducts);
        centerPanel.add(btnStock);
        centerPanel.add(btnLowStock);
        centerPanel.add(btnReports);

        add(centerPanel, BorderLayout.CENTER);

        //logout button (same style)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));

        JButton btnLogout = createButton("Log Out", "logout.png",
                new Font("SansSerif", Font.PLAIN, 14), null);

        btnLogout.setOpaque(false);
        btnLogout.setContentAreaFilled(false);
        btnLogout.setBorderPainted(false);

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });

        bottomPanel.add(btnLogout);
        add(bottomPanel, BorderLayout.SOUTH);

        //transparency
        topPanel.setOpaque(false);
        centerPanel.setOpaque(false);
        bottomPanel.setOpaque(false);

        setVisible(true);
    }

    private JButton createButton(String text, String iconPath, Font font, Border border) {

        ImageIcon icon = new ImageIcon(iconPath);
        Image img = icon.getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH);
        JButton button = new JButton(text, new ImageIcon(img));

        button.setFont(font);
        button.setBackground(Color.WHITE);

        if (border != null)
            button.setBorder(border);

        return button;
    }
    
    private void setAppIcon(String path) {
        try {
            ImageIcon icon = new ImageIcon(path);
            setIconImage(icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        } catch (Exception ignored) {}
    }
}