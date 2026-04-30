/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

/**
 *
 * @author User
 */

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import network.TipClient;

public class TipsFrame extends JFrame {   private JTextArea tipsArea;
    private JButton fetchButton;
    private JButton backButton;

    public TipsFrame() {
        setTitle("Inventory Tips");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setAppIcon("logo.png");

        setContentPane(new BackgroundPanel("Background3.jpg"));
        setLayout(new BorderLayout());

        initUI();
    }

    private void initUI() {
        JPanel root = (JPanel) getContentPane();
        root.setLayout(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        backButton = new JButton("←");
        styleButton(backButton);
        backButton.setPreferredSize(new Dimension(40, 10));
        backButton.addActionListener(e -> {
            new HomeFrame().setVisible(true);
            dispose();
        });

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(backButton, BorderLayout.WEST);

        JLabel title = new JLabel("Inventory Tips", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        top.add(title, BorderLayout.CENTER);
        

        root.add(top, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(makeTitled("Recommendations"));

        tipsArea = new JTextArea();
        tipsArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tipsArea.setLineWrap(true);
        tipsArea.setWrapStyleWord(true);
        tipsArea.setEditable(false);
        tipsArea.setText("No tips loaded yet.\nClick 'Fetch Tips' to retrieve inventory management recommendations from the server.");

        JScrollPane scrollPane = new JScrollPane(tipsArea);
        scrollPane.setPreferredSize(new Dimension(700, 330));

        contentPanel.add(scrollPane, BorderLayout.CENTER);
        root.add(contentPanel, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setOpaque(false);

        fetchButton = new JButton("Fetch Tips");
        styleButton(fetchButton);
        fetchButton.setPreferredSize(new Dimension(140, 35));

        fetchButton.addActionListener(e -> {
            String tip = TipClient.getTip();
           tipsArea.setText(tip);
        });

        bottom.add(fetchButton);
        root.add(bottom, BorderLayout.SOUTH);
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
