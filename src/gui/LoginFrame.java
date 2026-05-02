
package gui;


import javax.swing.*;
import java.awt.*;



// This class represents the login interface, allowing users to enter username and password
// and access the main application (HomeFrame)

public class LoginFrame extends JFrame {

    private String BG_IMAGE = "background3.jpg";
    private String LOGO_IMAGE = "logo.png";

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, exitButton;
    private JLabel errorLabel;

    public LoginFrame() {

        setTitle("Login");
        setSize(750, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

  
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
        
        JLabel background = new JLabel(
                new ImageIcon(new ImageIcon(BG_IMAGE)
                        .getImage().getScaledInstance(750, 600, Image.SCALE_SMOOTH))
        );
        background.setLayout(new GridBagLayout());
        setContentPane(background);

     
        JPanel mainBox = new JPanel();
        mainBox.setOpaque(false);
        mainBox.setLayout(new BoxLayout(mainBox, BoxLayout.Y_AXIS));

         
        setAppIcon(LOGO_IMAGE);
        JLabel logoLabel = new JLabel(
                new ImageIcon(new ImageIcon(LOGO_IMAGE)
                        .getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH))
        );
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainBox.add(logoLabel);
        mainBox.add(Box.createVerticalStrut(25));

   
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                int shadowSize = 12;
                int arc = 25;

             
                g2.setColor(new Color(0, 0, 0, 60));
                g2.fillRoundRect(shadowSize, shadowSize,
                        getWidth() - shadowSize,
                        getHeight() - shadowSize,
                        arc, arc);

     
                g2.setColor(new Color(252, 244, 247));
                g2.fillRoundRect(0, 0,
                        getWidth() - shadowSize,
                        getHeight() - shadowSize,
                        arc, arc);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        card.setMaximumSize(new Dimension(430, 360));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 12, 0);
        gbc.weightx = 1;

    
        JLabel title = new JLabel("User Login", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridy = 0;
        card.add(title, gbc);

        
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 1;
        card.add(userLabel, gbc);

        usernameField = new JTextField();
        gbc.gridy = 2;
        card.add(usernameField, gbc);

  
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 3;
        card.add(passLabel, gbc);

        passwordField = new JPasswordField();
        gbc.gridy = 4;
        card.add(passwordField, gbc);


        errorLabel = new JLabel(" ");
        errorLabel.setForeground(new Color(176, 0, 32));
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridy = 5;
        card.add(errorLabel, gbc);

        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setOpaque(false);

        loginButton = new JButton("Login");
        exitButton = new JButton("Exit");
        
        styleButton(loginButton);
        styleButton(exitButton);

        btnPanel.add(loginButton);
        btnPanel.add(exitButton);

        gbc.gridy = 6;
        card.add(btnPanel, gbc);

        mainBox.add(card);
        background.add(mainBox);

   
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals("admin") && password.equals("1234")) {
                new HomeFrame().setVisible(true);
                dispose();
            } else {
                errorLabel.setText("Invalid username or password.");
            }
        });

        exitButton.addActionListener(e -> System.exit(0));
    }

    private void styleButton(JButton b) {
        b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        b.setPreferredSize(new Dimension(110, 28));
        b.setFocusPainted(false);
    }
    
    private void setAppIcon(String path) {
        try {
            ImageIcon icon = new ImageIcon(path);
            setIconImage(icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        } catch (Exception ignored) {}
    }

}