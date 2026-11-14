package com.womensafe.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.womensafe.dao.AdminDAO;
import com.womensafe.models.Admin;
import com.womensafe.utils.Theme;

public class AdminLoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private AdminDAO adminDAO;
    
    public AdminLoginFrame() {
        adminDAO = new AdminDAO();
        
        setTitle("WomenSafe - Admin Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(30, 30, 60), 
                                                      getWidth(), getHeight(), new Color(60, 30, 80));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.setColor(new Color(255, 255, 255, 20));
                g2d.fillOval(-100, -100, 350, 350);
                g2d.fillOval(getWidth() - 200, getHeight() - 200, 300, 300);
                g2d.fillOval(getWidth() / 2 - 150, getHeight() / 2 - 100, 180, 180);
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        
        // Card Panel
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255, 255, 255, 240));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(35, 40, 40, 40));
        cardPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        JLabel titleLabel = new JLabel("ADMIN ACCESS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 34));
        titleLabel.setForeground(new Color(50, 50, 80));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        cardPanel.add(titleLabel, gbc);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Authorized Personnel Only");
        subtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        subtitleLabel.setForeground(Theme.TEXT_LIGHT);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        cardPanel.add(subtitleLabel, gbc);
        
        // Divider
        JLabel dividerLabel = new JLabel("━━━━━━━━━━━━━━━━━━━━━━");
        dividerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dividerLabel.setForeground(new Color(200, 200, 220));
        dividerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 2;
        cardPanel.add(dividerLabel, gbc);
        
        // Username
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(18, 20, 5, 20);
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(Theme.SMALL_FONT);
        userLabel.setForeground(Theme.TEXT_DARK);
        cardPanel.add(userLabel, gbc);
        
        gbc.gridy = 4;
        usernameField = new JTextField(20);
        usernameField.setFont(Theme.NORMAL_FONT);
        usernameField.setBackground(new Color(248, 248, 252));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 210)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        cardPanel.add(usernameField, gbc);
        
        // Password
        gbc.gridy = 5;
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(Theme.SMALL_FONT);
        passLabel.setForeground(Theme.TEXT_DARK);
        cardPanel.add(passLabel, gbc);
        
        gbc.gridy = 6;
        passwordField = new JPasswordField(20);
        passwordField.setFont(Theme.NORMAL_FONT);
        passwordField.setBackground(new Color(248, 248, 252));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 210)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        cardPanel.add(passwordField, gbc);
        
        // ========== LOGIN - TEXT ONLY (CLICKABLE) ==========
        gbc.gridy = 7;
        gbc.insets = new Insets(25, 20, 10, 20);
        
        JLabel loginLabel = new JLabel("LOGIN");
        loginLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        loginLabel.setForeground(new Color(50, 50, 80));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginLabel.setForeground(new Color(200, 30, 60));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginLabel.setForeground(new Color(50, 50, 80));
            }
        });
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                performLogin();
            }
        });
        cardPanel.add(loginLabel, gbc);
        
        // ========== BACK BUTTON - TEXT ONLY ==========
        gbc.gridy = 8;
        gbc.insets = new Insets(5, 20, 5, 20);
        
        JLabel backLabel = new JLabel("← Back to User Login");
        backLabel.setFont(Theme.SMALL_FONT);
        backLabel.setForeground(Theme.TEXT_LIGHT);
        backLabel.setHorizontalAlignment(SwingConstants.CENTER);
        backLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backLabel.setForeground(Theme.PRIMARY);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backLabel.setForeground(Theme.TEXT_LIGHT);
            }
        });
        backLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new LoginFrame();
                dispose();
            }
        });
        cardPanel.add(backLabel, gbc);
        
        mainPanel.add(cardPanel);
        add(mainPanel);
        setVisible(true);
    }
    
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password!");
            return;
        }
        
        Admin admin = adminDAO.loginAdmin(username, password);
        if (admin != null) {
            JOptionPane.showMessageDialog(this, "Welcome Admin: " + admin.getUsername() + "!");
            new AdminDashboardFrame();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!");
        }
    }
}