package gui;


import javax.swing.*;
import java.awt.*;

public class ManageStockFrame extends JFrame {

    private JTextField txtProductID;
    private JTextField txtProductName;
    private JTextField txtCurrentQuantity;
    private JTextField txtQuantity;

    private JButton btnSearch;
    private JButton btnUpdate;
    private JButton btnBack;

    private JRadioButton rbAdd;
    private JRadioButton rbReduce;

    public ManageStockFrame() {

        setTitle("Stock");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAppIcon("logo.png");

        // 🔥 Background Image
        setContentPane(createBackgroundPanel("Background3.jpg"));
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        mainPanel.setOpaque(false);

        // ================= Search Panel =================
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Product"));
        searchPanel.setOpaque(false);

        searchPanel.add(new JLabel("Product ID: "));
        txtProductID = new JTextField(10);
        searchPanel.add(txtProductID);

        btnSearch = new JButton("Search");
        searchPanel.add(btnSearch);

        // ================= Info Panel =================
        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Current Stock Information"));
        infoPanel.setOpaque(false);

        infoPanel.add(new JLabel("Product Name: "));
        txtProductName = new JTextField();
        txtProductName.setEditable(false);
        infoPanel.add(txtProductName);

        infoPanel.add(new JLabel("Current Quantity: "));
        txtCurrentQuantity = new JTextField();
        txtCurrentQuantity.setEditable(false);
        infoPanel.add(txtCurrentQuantity);

        // ================= Adjust Panel =================
        JPanel adjustPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        adjustPanel.setBorder(BorderFactory.createTitledBorder("Adjust Stock"));
        adjustPanel.setOpaque(false);

        adjustPanel.add(new JLabel("Quantity: "));
        txtQuantity = new JTextField();
        adjustPanel.add(txtQuantity);

        rbAdd = new JRadioButton("Add Stock");
        rbReduce = new JRadioButton("Reduce Stock");

        rbAdd.setOpaque(false);
        rbReduce.setOpaque(false);

        ButtonGroup group = new ButtonGroup();
        group.add(rbAdd);
        group.add(rbReduce);

        adjustPanel.add(rbAdd);
        adjustPanel.add(rbReduce);

        // ================= Bottom Buttons =================
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        btnUpdate = new JButton("Update Stock");
        btnBack = new JButton("Back");

        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnBack);

        mainPanel.add(searchPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(infoPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(adjustPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(buttonPanel);

        add(mainPanel, BorderLayout.CENTER);
        
        btnBack.addActionListener(e -> {
        dispose();
        new HomeFrame().setVisible(true);
        });

        setVisible(true);
    }

    
    private JPanel createBackgroundPanel(String path) {

        Image bgImage = new ImageIcon(path).getImage();  // safer for local image

        return new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
    }
    
    private void setAppIcon(String path) {
        try {
            ImageIcon icon = new ImageIcon(path);
            setIconImage(icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        } catch (Exception ignored) {}
    }
}