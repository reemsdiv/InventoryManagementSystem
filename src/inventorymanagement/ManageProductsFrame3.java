
package inventorymanagement;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ManageProductsFrame3 extends JFrame {

    private JTextField txtId, txtName, txtCategory, txtPrice, txtQuantity, txtMinStock;
    private JTable productTable;
    private DefaultTableModel tableModel;

    public ManageProductsFrame3() {

        setTitle("Manage Products");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setAppIcon("logo.png");

        setContentPane(new BackgroundPanel("Background3.jpg"));
        setLayout(new BorderLayout());

        // Nimbus look
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        initUI();
    }

    private void initUI() {

        JPanel root = (JPanel) getContentPane();
        root.setLayout(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        JPanel form = createFormPanel();
        JPanel tablePanel = createTablePanel();

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, form, tablePanel);
        split.setResizeWeight(0.40);
        split.setDividerSize(8);
        split.setDividerLocation(375);
        split.setEnabled(false);

        root.add(split, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        footer.setOpaque(false);

        JButton btnBack = new JButton("Back");
        styleButton(btnBack);
        btnBack.addActionListener(e -> {
            new HomeFrame().setVisible(true);
            dispose();
        });

        footer.add(btnBack);
        root.add(footer, BorderLayout.SOUTH);
    }

    private JPanel createFormPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(makeTitled("Product Details"));
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        txtId = new JTextField();
        txtName = new JTextField();
        txtCategory = new JTextField();
        txtPrice = new JTextField();
        txtQuantity = new JTextField();
        txtMinStock = new JTextField();

        JTextField[] fields = {txtId, txtName, txtCategory, txtPrice, txtQuantity, txtMinStock};
        for (JTextField f : fields) {
            f.setFont(fieldFont);
            f.setPreferredSize(new Dimension(240, 30));
        }

        txtId.setEditable(false);
        txtId.setBackground(new Color(245, 245, 245));

        addRow(panel, gbc, 0, "Product ID:", txtId, labelFont);
        addRow(panel, gbc, 1, "Product Name:", txtName, labelFont);
        addRow(panel, gbc, 2, "Category:", txtCategory, labelFont);
        addRow(panel, gbc, 3, "Price:", txtPrice, labelFont);
        addRow(panel, gbc, 4, "Quantity:", txtQuantity, labelFont);
        addRow(panel, gbc, 5, "Minimum Stock Level:", txtMinStock, labelFont);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        btnPanel.setOpaque(false);

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");

        styleButton(btnAdd);
        styleButton(btnUpdate);
        styleButton(btnDelete);
        styleButton(btnClear);

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(btnPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(Box.createVerticalGlue(), gbc);

        return panel;
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row,
                        String labelText, JComponent field, Font labelFont) {

        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0.35;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0.65;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
    }

    private JPanel createTablePanel() {

        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(makeTitled("All Products List"));
        panel.setOpaque(false);

        String[] columns = {"ID", "Name", "Category", "Price", "Quantity", "Min Stock"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        productTable = new JTable(tableModel);
        productTable.setRowHeight(26);
        productTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        productTable.setFillsViewportHeight(true);

        JTableHeader header = productTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));

        panel.add(new JScrollPane(productTable), BorderLayout.CENTER);
        return panel;
    }

    private TitledBorder makeTitled(String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14)
        );
    }

    private void styleButton(JButton b) {
        b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        b.setPreferredSize(new Dimension(85, 28));
        b.setFocusPainted(false);
    }

    static class BackgroundPanel extends JPanel {
        private Image image;

        public BackgroundPanel(String path) {
            image = new ImageIcon(path).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
    private void setAppIcon(String path) {
        try {
            ImageIcon icon = new ImageIcon(path);
            setIconImage(icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        } catch (Exception ignored) {}
    }
}