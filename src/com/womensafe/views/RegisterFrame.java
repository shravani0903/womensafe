package com.womensafe.views;

import javax.swing.*;
import java.awt.*;
import com.womensafe.dao.UserDAO;
import com.womensafe.models.User;

public class RegisterFrame extends JFrame {
    private JTextField nameField, emailField, phoneField;
    private JPasswordField passwordField, confirmPasswordField;
    private UserDAO userDAO;
    
    public RegisterFrame() {
        userDAO = new UserDAO();
        
        setTitle("WomenSafe - Register");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 230, 240), 
                                                      getWidth(), getHeight(), new Color(243, 229, 245));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(231, 76, 60));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, gbc);
        
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 20, 10);
        JLabel subtitleLabel = new JLabel("Join WomenSafe for your safety");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(149, 165, 166));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(subtitleLabel, gbc);
        
        gbc.gridy = 2;
        gbc.insets = new Insets(8, 20, 2, 20);
        gbc.gridwidth = 1;
        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        nameLabel.setForeground(new Color(52, 73, 94));
        mainPanel.add(nameLabel, gbc);
        
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 20, 8, 20);
        nameField = new JTextField(20);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameField.setBackground(Color.WHITE);
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 190)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        nameField.setPreferredSize(new Dimension(320, 40));
        mainPanel.add(nameField, gbc);
        
        gbc.gridy = 4;
        gbc.insets = new Insets(8, 20, 2, 20);
        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        emailLabel.setForeground(new Color(52, 73, 94));
        mainPanel.add(emailLabel, gbc);
        
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 20, 8, 20);
        emailField = new JTextField(20);
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setBackground(Color.WHITE);
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 190)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        emailField.setPreferredSize(new Dimension(320, 40));
        mainPanel.add(emailField, gbc);
        
        gbc.gridy = 6;
        gbc.insets = new Insets(8, 20, 2, 20);
        JLabel phoneLabel = new JLabel("Phone Number");
        phoneLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        phoneLabel.setForeground(new Color(52, 73, 94));
        mainPanel.add(phoneLabel, gbc);
        
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 20, 8, 20);
        phoneField = new JTextField(20);
        phoneField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        phoneField.setBackground(Color.WHITE);
        phoneField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 190)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        phoneField.setPreferredSize(new Dimension(320, 40));
        mainPanel.add(phoneField, gbc);
        
        gbc.gridy = 8;
        gbc.insets = new Insets(8, 20, 2, 20);
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        passLabel.setForeground(new Color(52, 73, 94));
        mainPanel.add(passLabel, gbc);
        
        gbc.gridy = 9;
        gbc.insets = new Insets(0, 20, 8, 20);
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBackground(Color.WHITE);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 190)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        passwordField.setPreferredSize(new Dimension(320, 40));
        mainPanel.add(passwordField, gbc);
        
        gbc.gridy = 10;
        gbc.insets = new Insets(8, 20, 2, 20);
        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        confirmLabel.setForeground(new Color(52, 73, 94));
        mainPanel.add(confirmLabel, gbc);
        
        gbc.gridy = 11;
        gbc.insets = new Insets(0, 20, 15, 20);
        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        confirmPasswordField.setBackground(Color.WHITE);
        confirmPasswordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 190)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        confirmPasswordField.setPreferredSize(new Dimension(320, 40));
        mainPanel.add(confirmPasswordField, gbc);
        
        gbc.gridy = 12;
        gbc.insets = new Insets(5, 20, 5, 20);
        JButton registerButton = new JButton("REGISTER");
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        registerButton.setForeground(new Color(231, 76, 60));
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(e -> registerUser());
        mainPanel.add(registerButton, gbc);
        
        gbc.gridy = 13;
        gbc.insets = new Insets(5, 20, 20, 20);
        JButton backButton = new JButton("Back to Home");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        backButton.setForeground(new Color(149, 165, 166));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            new LandingPageFrame();
            dispose();
        });
        mainPanel.add(backButton, gbc);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private void registerUser() {
        String username = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
        
        if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }
        
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters!");
            return;
        }
        
        User user = new User(username, email, phone, password);
        if (userDAO.registerUser(user)) {
            JOptionPane.showMessageDialog(this, "Registration Successful!\n\nPlease login with your credentials.");
            new LoginFrame();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed!\nEmail might already exist.");
        }
    }
}