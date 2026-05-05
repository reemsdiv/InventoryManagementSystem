package gui;

import database.UserDAO;
import javax.swing.*;
import java.awt.*;

public class SignUpFrame extends JFrame {

    private final String BG_IMAGE = "background3.jpg";
    private final String LOGO_IMAGE = "logo.png";

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private JButton signUpButton;
    private JButton backButton;
    private JLabel errorLabel;

    public SignUpFrame() {
        setTitle("Sign Up");
        setSize(750, 650);        
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

        //Background
        JLabel background = new JLabel(
                new ImageIcon(new ImageIcon(BG_IMAGE)
                        .getImage().getScaledInstance(750, 650, Image.SCALE_SMOOTH))
        );
        background.setLayout(new GridBagLayout());
        setContentPane(background);

        //Outer column (logo + card)
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
        mainBox.add(Box.createVerticalStrut(20));

        //Card
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                int shadowSize = 12, arc = 25;
                g2.setColor(new Color(0, 0, 0, 60));
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
        card.setMaximumSize(new Dimension(430, 440));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.insets  = new Insets(6, 0, 10, 0);
        gbc.weightx = 1;

        //Title
        JLabel title = new JLabel("Create Account", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridy = 0;
        card.add(title, gbc);

        //Username 
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridy = 1;
        card.add(userLabel, gbc);

        usernameField = new JTextField();
        gbc.gridy = 2;
        card.add(usernameField, gbc);

        //Password 
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridy = 3;
        card.add(passLabel, gbc);

        passwordField = new JPasswordField();
        gbc.gridy = 4;
        card.add(passwordField, gbc);

        //Confirm Password
        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridy = 5;
        card.add(confirmLabel, gbc);

        confirmField = new JPasswordField();
        gbc.gridy = 6;
        card.add(confirmField, gbc);

        //Error label
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(new Color(176, 0, 32));
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridy = 7;
        card.add(errorLabel, gbc);

        //Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setOpaque(false);

        signUpButton = new JButton("Sign Up");
        backButton   = new JButton("Back");

        styleButton(signUpButton);
        styleButton(backButton);

        btnPanel.add(signUpButton);
        btnPanel.add(backButton);

        gbc.gridy = 8;
        card.add(btnPanel, gbc);

        mainBox.add(card);
        background.add(mainBox);

        //Action listeners
        signUpButton.addActionListener(e -> handleSignUp());

        backButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        //Allow Enter key to submit
        confirmField.addActionListener(e -> handleSignUp());
    }

    // Sign up logic
    private void handleSignUp() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirm  = new String(confirmField.getPassword());

        //Basic field validation
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Username and password cannot be empty.");
            return;
        }

        //Passwords must match
        if (!password.equals(confirm)) {
            errorLabel.setText("Passwords do not match.");
            return;
        }

        //Check uniqueness + register
        try {
            UserDAO dao = new UserDAO();

            if (dao.usernameExists(username)) {
                errorLabel.setText("Username is already taken.");
                return;
            }

            dao.register(username, password);

            JOptionPane.showMessageDialog(
                    this,
                    "Account created successfully!",
                    "Sign Up Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            new HomeFrame().setVisible(true);
            dispose();

        } catch (Exception ex) {
            errorLabel.setText("Error: " + ex.getMessage());
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