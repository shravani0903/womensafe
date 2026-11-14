package com.womensafe.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.womensafe.dao.UserDAO;
import com.womensafe.models.User;

public class LoginFrame extends JFrame {
    
    private JTextField emailField;
    private JPasswordField passwordField;
    
    public LoginFrame() {
        setTitle("WomenSafe - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // --- Background Gradient Panel ---
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 182, 193), 
                        getWidth(), getHeight(), new Color(218, 112, 214));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        // --- Main Card Panel ---
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true),
                BorderFactory.createEmptyBorder(40, 60, 40, 60)
        ));

        // --- Title & Tagline ---
        JLabel titleLabel = new JLabel("WOMENSAFE");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(220, 20, 60)); 
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // FORCING THE EMOJI FONT HERE
        JLabel taglineLabel = new JLabel("🛡️ Your Safety, Our Priority 🛡️");
        taglineLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14)); 
        taglineLabel.setForeground(Color.GRAY);
        taglineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Welcome Back!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeLabel.setForeground(new Color(220, 20, 60));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel("Login to continue your safety journey");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subLabel.setForeground(Color.GRAY);
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Email Field ---
        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));

        emailField = new JTextField(20);
        emailField.setMaximumSize(new Dimension(320, 40));
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.putClientProperty("JComponent.roundRect", true);
        emailField.putClientProperty("JTextField.placeholderText", "Enter your email");

        // --- Password Field ---
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));

        JPanel passwordContainer = new JPanel(new BorderLayout());
        passwordContainer.setBackground(Color.WHITE);
        passwordContainer.setMaximumSize(new Dimension(320, 40));
        passwordContainer.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setEchoChar('•');
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // FORCING THE EMOJI FONT FOR THE EYE BUTTON
        JButton eyeButton = new JButton("👁");
        eyeButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14)); 
        eyeButton.setFocusPainted(false);
        eyeButton.setContentAreaFilled(false);
        eyeButton.setBorderPainted(false);
        eyeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        eyeButton.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));

        eyeButton.addActionListener(e -> {
            if (passwordField.getEchoChar() == '•') {
                passwordField.setEchoChar((char) 0); 
            } else {
                passwordField.setEchoChar('•'); 
            }
        });

        passwordContainer.add(passwordField, BorderLayout.CENTER);
        passwordContainer.add(eyeButton, BorderLayout.EAST);

        // --- Login Button ---
        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setBackground(new Color(220, 20, 60));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setMaximumSize(new Dimension(150, 40));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.putClientProperty("JButton.buttonType", "roundRect"); 
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginButton.addActionListener(e -> performLogin());

        // --- Links ---
        JLabel registerLabel = new JLabel("Create New Account");
        registerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        registerLabel.setForeground(new Color(220, 20, 60));
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new RegisterFrame();
                dispose();
            }
        });

        JLabel adminLabel = new JLabel("Admin Login");
        adminLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        adminLabel.setForeground(Color.GRAY);
        adminLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        adminLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new AdminLoginFrame();
                dispose();
            }
        });

        // --- Add components ---
        cardPanel.add(titleLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        cardPanel.add(taglineLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        cardPanel.add(welcomeLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        cardPanel.add(subLabel);
        cardPanel.add(emailLabel);
        cardPanel.add(emailField);
        cardPanel.add(passLabel);
        cardPanel.add(passwordContainer); 
        cardPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        cardPanel.add(loginButton);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        cardPanel.add(registerLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        cardPanel.add(adminLabel);

        backgroundPanel.add(cardPanel);
        add(backgroundPanel, BorderLayout.CENTER);
        
        setVisible(true);
    }

    private void performLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter email and password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.loginUser(email, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login Successful! Welcome " + user.getUsername());
            new UserDashboardFrame(user);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Email or Password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}