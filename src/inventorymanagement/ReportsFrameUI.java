

package inventorymanagement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ReportsFrameUI extends JFrame {

    private JList<String> reportList;
    private JTextArea reportDetailsArea;
    private JLabel statusLabel;
    private JButton generateButton, backButton;

    public ReportsFrameUI() {

        setTitle("Reports");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

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

        // ================= Left Side (Report List) =================
        reportList = new JList<>(new String[]{
                "Daily Sales Report",
                "Stock Report",
                "Low Stock Report"
        });

        reportList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        reportList.setSelectedIndex(0);
        reportList.setBackground(new Color(252, 244, 247));
        reportList.setSelectionBackground(new Color(245, 225, 232));

        JScrollPane leftScroll = new JScrollPane(reportList);
        leftScroll.getViewport().setBackground(new Color(252, 244, 247));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(252, 244, 247));
        leftPanel.add(leftScroll, BorderLayout.CENTER);

        // ================= Right Side (Report Details) =================
        reportDetailsArea = new JTextArea();
        reportDetailsArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
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
        splitPane.setResizeWeight(0.30);
        splitPane.setDividerSize(8);
        splitPane.setBorder(null);

        // ================= Bottom Bar =================
        statusLabel = new JLabel("Status: Ready");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        generateButton = new JButton("Generate");
        backButton = new JButton("Back");

        generateButton.setFont(new Font("SansSerif", Font.PLAIN, 13));
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 13));
        generateButton.setFocusPainted(false);
        backButton.setFocusPainted(false);
        generateButton.setPreferredSize(new Dimension(110, 32));
        backButton.setPreferredSize(new Dimension(110, 32));

        // Back button action
        backButton.addActionListener(e -> {
            dispose();
            new HomeFrame().setVisible(true);
        });

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(generateButton);
        buttonRow.add(backButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(statusLabel, BorderLayout.WEST);
        bottomPanel.add(buttonRow, BorderLayout.EAST);

        // ================= Add Everything =================
        root.add(headerPanel, BorderLayout.NORTH);
        root.add(splitPane, BorderLayout.CENTER);
        root.add(bottomPanel, BorderLayout.SOUTH);

        add(root, BorderLayout.CENTER);

        setVisible(true);
    }
}