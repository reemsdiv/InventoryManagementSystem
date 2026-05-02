package gui;


import javax.swing.*;
import java.awt.*;
import model.Product;
import model.ProductManager;

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
    
    private ProductManager manager = new ProductManager();

    public ManageStockFrame() {

        setTitle("Stock");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAppIcon("logo.png");

        setContentPane(createBackgroundPanel("Background3.jpg"));
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        mainPanel.setOpaque(false);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Product"));
        searchPanel.setOpaque(false);

        searchPanel.add(new JLabel("Product ID: "));
        txtProductID = new JTextField(10);
        searchPanel.add(txtProductID);

        btnSearch = new JButton("Search");
        searchPanel.add(btnSearch);

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
       
        btnSearch.addActionListener(e -> {
            try {
                String id = txtProductID.getText().trim();

                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter a Product ID to search.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Product p = manager.searchProduct(id);

                txtProductName.setText(p.getName());
                txtCurrentQuantity.setText(String.valueOf(p.getQuantity()));

            } catch (Exception ex) {
                txtProductName.setText("");
                txtCurrentQuantity.setText("");
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        
        btnUpdate.addActionListener(e -> {
            try {
                String id = txtProductID.getText().trim();
   
                if (id.isEmpty() || txtProductName.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Please search for a product first.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!rbAdd.isSelected() && !rbReduce.isSelected()) {
                    JOptionPane.showMessageDialog(this,
                            "Please select Add Stock or Reduce Stock.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (txtQuantity.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter a quantity.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int quantity = Integer.parseInt(txtQuantity.getText().trim());
                boolean isAdding = rbAdd.isSelected();

                manager.updateStock(id, quantity, isAdding);
                JOptionPane.showMessageDialog(this, "Stock updated successfully!");

                
                Product updated = manager.searchProduct(id);
                txtCurrentQuantity.setText(String.valueOf(updated.getQuantity()));

               
                txtQuantity.setText("");
                rbAdd.setSelected(false);
                rbReduce.setSelected(false);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Quantity must be a whole number.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnBack.addActionListener(e -> {
        dispose();
        new HomeFrame().setVisible(true);
        });

        setVisible(true);
    }

    private JPanel createBackgroundPanel(String path) {

        Image bgImage = new ImageIcon(path).getImage();  
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