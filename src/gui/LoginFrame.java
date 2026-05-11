package gui;

import database.UserDAO;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    // Background image path
    private final String BG_IMAGE   = "background3.jpg";
    
    // Logo image path
    private final String LOGO_IMAGE = "logo.png";

    private JTextField     usernameField;
    private JPasswordField passwordField;
    private JButton        loginButton;
    private JButton        exitButton;
    private JLabel         errorLabel;

    public LoginFrame() {
        setTitle("Login");  // Set frame title
        setSize(750, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Close application when frame closes
        setLocationRelativeTo(null);    // Center frame on screen

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}
        
        // Initialize UI components
        initUI();
    }
    
    // =========================
    // Initialize User Interface
    // =========================
    private void initUI() {
        //Background
        JLabel background = new JLabel(
                new ImageIcon(new ImageIcon(BG_IMAGE)
                        .getImage().getScaledInstance(750, 600, Image.SCALE_SMOOTH))
        );
        // Use GridBagLayout for centered content
        background.setLayout(new GridBagLayout());
        setContentPane(background);

        // =========================
        // Main Vertical Container
        // =========================
        JPanel mainBox = new JPanel();
        mainBox.setOpaque(false);
        mainBox.setLayout(new BoxLayout(mainBox, BoxLayout.Y_AXIS));

        // =========================
        // Application Logo
        // =========================
        
        setAppIcon(LOGO_IMAGE);
        JLabel logoLabel = new JLabel(
                new ImageIcon(new ImageIcon(LOGO_IMAGE)
                        .getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH))
        );
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainBox.add(logoLabel);
        mainBox.add(Box.createVerticalStrut(25));

        // =========================
        // Login Card Panel
        // =========================
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                int shadowSize = 12, arc = 25;
                g2.setColor(new Color(0, 0, 0, 60));   // Draw shadow
                g2.fillRoundRect(shadowSize, shadowSize,
                        getWidth() - shadowSize, getHeight() - shadowSize, arc, arc);
                g2.setColor(new Color(252, 244, 247));
                g2.fillRoundRect(0, 0,
                        getWidth() - shadowSize, getHeight() - shadowSize, arc, arc);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        card.setMaximumSize(new Dimension(430, 380));
       
        // =========================
        // GridBag Layout Settings
        // =========================
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.insets  = new Insets(6, 0, 12, 0);
        gbc.weightx = 1;

        // =========================
        // Login Title
        // =========================
        JLabel title = new JLabel("User Login", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridy = 0;
        card.add(title, gbc);


        // =========================
        // Username Section
        // =========================
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 1;
        card.add(userLabel, gbc);

        usernameField = new JTextField();
        gbc.gridy = 2;
        card.add(usernameField, gbc);

        // =========================
        // Password Section
        // =========================
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 3;
        card.add(passLabel, gbc);

        passwordField = new JPasswordField();   // Password input field
        gbc.gridy = 4;
        card.add(passwordField, gbc);

        // =========================
        // Error Label
        // =========================
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(new Color(176, 0, 32));
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridy = 5;
        card.add(errorLabel, gbc);

        // =========================
        // Login and Exit Buttons
        // =========================
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setOpaque(false);

        // Create buttons
        loginButton = new JButton("Login");
        exitButton  = new JButton("Exit");

        styleButton(loginButton);
        styleButton(exitButton);
      
        // Add buttons
        btnPanel.add(loginButton);
        btnPanel.add(exitButton);

        gbc.gridy = 6;
        card.add(btnPanel, gbc);

        // =========================
        // Sign Up Section
        // =========================
        JPanel signUpRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        signUpRow.setOpaque(false);

        JLabel noAccountLabel = new JLabel("Don't have an account?");
        noAccountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JButton signUpLink = new JButton("Sign Up");
        signUpLink.setFont(new Font("Segoe UI", Font.BOLD, 12));
        signUpLink.setBorderPainted(false);
        signUpLink.setContentAreaFilled(false);
        signUpLink.setForeground(new Color(100, 60, 120));
        signUpLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signUpLink.setFocusPainted(false);
        signUpLink.setMargin(new Insets(0, 0, 0, 0));

        // Add labels/buttons
        signUpRow.add(noAccountLabel);
        signUpRow.add(signUpLink);

        gbc.gridy  = 7;
        gbc.insets = new Insets(2, 0, 0, 0);
        card.add(signUpRow, gbc);

        mainBox.add(card);
        background.add(mainBox);

        // =========================
        // Action Listeners
        // =========================
        loginButton.addActionListener(e -> handleLogin());     // Login button action
        passwordField.addActionListener(e -> handleLogin());   // Enter key

        exitButton.addActionListener(e -> System.exit(0));     // Exit
        
        // Open Sign Up screen
        signUpLink.addActionListener(e -> {
            new SignUpFrame().setVisible(true);
            dispose();
        });
    }


    // =========================
    // Login Logic
    // =========================
    private void handleLogin() {
        String username = usernameField.getText().trim();   // Get username
        String password = new String(passwordField.getPassword());  // Get password

        // Validate empty fields
        if (username.isBlank() || password.isBlank()) {
            errorLabel.setText("Please enter username and password.");
            return;
        }

        try {
            // Create DAO object
            UserDAO dao = new UserDAO();
            // Validate login
            if (dao.login(username, password)) {
                new HomeFrame().setVisible(true);
                dispose();
            } else {   // Invalid login
                errorLabel.setText("Invalid username or password.");
            }
            
        } catch (Exception ex) {
            // Database connection error
            errorLabel.setText("Connection error. Please try again.");
            
            ex.printStackTrace();
        }
    }

    //Helpers
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