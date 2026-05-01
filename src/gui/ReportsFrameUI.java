package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import model.ProductManager;
import model.ReportGenerator;

public class ReportsFrameUI extends JFrame {

    private JList<String> reportList;
    private JTextArea reportDetailsArea;
    private JLabel statusLabel;

    private JButton generateButton;
    private JButton saveButton;
    private JButton backButton;

    private String lastReport = "";

    public ReportsFrameUI() {

        setTitle("Reports");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        
        setAppIcon("logo.png");

        // ================= Background =================
        ImageIcon bgIcon = new ImageIcon("background3.jpg");
        Image bgImage = bgIcon.getImage().getScaledInstance(950, 600, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        // ================= Root Panel =================
        JPanel root = new JPanel(new BorderLayout(20, 20));
        root.setBorder(new EmptyBorder(25, 30, 25, 30));
        root.setOpaque(false);

        // ================= Header =================
        JLabel title = new JLabel("Reports");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(title, BorderLayout.WEST);

        // ================= Report List =================
        reportList = new JList<>(new String[]{
                "Daily Sales Report",
                "Stock Report",
                "Low Stock Report"
        });

        reportList.setBackground(new Color(252, 244, 247));
        reportList.setSelectionBackground(new Color(245, 225, 232));

        JScrollPane leftScroll = new JScrollPane(reportList);
        leftScroll.getViewport().setBackground(new Color(252, 244, 247));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(252, 244, 247));
        leftPanel.add(leftScroll, BorderLayout.CENTER);

        // ================= Report Area =================
        reportDetailsArea = new JTextArea();
        reportDetailsArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        reportDetailsArea.setEditable(false);
        reportDetailsArea.setBackground(new Color(252, 244, 247));

        JScrollPane rightScroll = new JScrollPane(reportDetailsArea);
        rightScroll.getViewport().setBackground(new Color(252, 244, 247));

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(252, 244, 247));
        rightPanel.add(rightScroll, BorderLayout.CENTER);

        // ================= Split Pane =================
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                leftPanel,
                rightPanel
        );

        splitPane.setResizeWeight(0.3);
        splitPane.setDividerSize(8);
        splitPane.setBorder(null);

        // ================= Buttons =================
        generateButton = new JButton("Generate");
        saveButton = new JButton("Export");
        backButton = new JButton("Back");

        generateButton.setFocusPainted(false);
        saveButton.setFocusPainted(false);
        backButton.setFocusPainted(false);

        generateButton.setPreferredSize(new Dimension(130, 32));
        saveButton.setPreferredSize(new Dimension(130, 32));
        backButton.setPreferredSize(new Dimension(100, 32));

        generateButton.addActionListener(e -> generateReport());
        saveButton.addActionListener(e -> saveReport());

        backButton.addActionListener(e -> {
            dispose();
            new HomeFrame().setVisible(true);
        });

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false); // ✅ FIX
        buttons.add(generateButton);
        buttons.add(saveButton);
        buttons.add(backButton);

        // ================= Status =================
        statusLabel = new JLabel("Status: Ready");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        // ================= Bottom Panel =================
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false); // ✅ FIX (removes grey bar)
        bottom.add(statusLabel, BorderLayout.WEST);
        bottom.add(buttons, BorderLayout.EAST);

        // ================= Add Everything =================
        root.add(headerPanel, BorderLayout.NORTH);
        root.add(splitPane, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        add(root);

        setVisible(true);
    }
    
    // ================= ICON =================
    private void setAppIcon(String path) {
        try {
            ImageIcon icon = new ImageIcon(path);
            setIconImage(icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        } catch (Exception ignored) {}
    }

    // ================= GENERATE =================
    private void generateReport() {
        try {
            ProductManager pm = new ProductManager();
            ReportGenerator generator = new ReportGenerator(pm);

            String selected = reportList.getSelectedValue();

            if (selected.equals("Stock Report")) {
                lastReport = generator.getStockReport();
            } else if (selected.equals("Low Stock Report")) {
                lastReport = generator.getLowStockReport();
            } else {
                lastReport = generator.getDailySalesReport();
            }

            reportDetailsArea.setText(lastReport);
            statusLabel.setText("Status: Generated");

        } catch (Exception e) {
            reportDetailsArea.setText("Error: " + e.getMessage());
            statusLabel.setText("Status: Failed");
        }
    }

    // ================= SAVE =================
    private void saveReport() {
        try {
            if (lastReport.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Generate report first!");
                return;
            }

            ProductManager pm = new ProductManager();
            ReportGenerator generator = new ReportGenerator(pm);

            String fileName = "Report_" + System.currentTimeMillis() + ".txt";

            generator.saveToFile(lastReport, fileName);

            statusLabel.setText("Saved: " + fileName);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving file");
        }
    }
}