package inventorymanagement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;

public class LowStockFrame extends JFrame {

    private JTable productTable;
    private JButton refreshButton, backButton;
    private JLabel statusLabel;

    public LowStockFrame() {

        setTitle("Low Stock Alert");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        // Background Image (same style as your first frame)
        ImageIcon bgIcon = new ImageIcon("background3.jpg");
        Image bgImage = bgIcon.getImage().getScaledInstance(950, 600, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        // Root Panel
        JPanel root = new JPanel(new BorderLayout(20, 20));
        root.setBorder(new EmptyBorder(25, 30, 25, 30));
        root.setOpaque(false);

        // ================= HEADER =================
        JLabel title = new JLabel("Low Stock Products");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(title, BorderLayout.WEST);

        // ================= TABLE =================
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Product ID", "Name", "Category", "Quantity", "Min Level"}, 0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        productTable = new JTable(model);
        productTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        productTable.setRowHeight(28);
        productTable.setFillsViewportHeight(true);
        productTable.setBackground(new Color(252, 244, 247));
        productTable.setShowGrid(true);
        productTable.setGridColor(new Color(220, 210, 215));

        JTableHeader tableHeader = productTable.getTableHeader();
        tableHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
        tableHeader.setBackground(new Color(245, 220, 230));
        tableHeader.setReorderingAllowed(false);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        productTable.getColumnModel().getColumn(3).setCellRenderer(center);
        productTable.getColumnModel().getColumn(4).setCellRenderer(center);

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.getViewport().setBackground(new Color(252, 244, 247));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 210, 215)));

        // ================= BOTTOM BAR =================
        statusLabel = new JLabel("Status: Ready");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        refreshButton = new JButton("Refresh");
        backButton = new JButton("Back");

        refreshButton.setFont(new Font("SansSerif", Font.PLAIN, 13));
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 13));
        refreshButton.setFocusPainted(false);
        backButton.setFocusPainted(false);
        refreshButton.setPreferredSize(new Dimension(110, 32));
        backButton.setPreferredSize(new Dimension(110, 32));

        backButton.addActionListener(e -> {
            dispose();
            new HomeFrame().setVisible(true);
        });

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(refreshButton);
        buttonRow.add(backButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(statusLabel, BorderLayout.WEST);
        bottomPanel.add(buttonRow, BorderLayout.EAST);

        // ================= ADD EVERYTHING =================
        root.add(headerPanel, BorderLayout.NORTH);
        root.add(scrollPane, BorderLayout.CENTER);
        root.add(bottomPanel, BorderLayout.SOUTH);

        add(root, BorderLayout.CENTER);

        setVisible(true);
    }
}