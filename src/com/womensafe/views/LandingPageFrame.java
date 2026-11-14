package com.womensafe.views;

import javax.swing.*;
import java.awt.*;

public class LandingPageFrame extends JFrame {
    
    public LandingPageFrame() {
        setTitle("WomenSafe - Monitoring Safety, Enabling Protection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Main Panel
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
        mainPanel.setLayout(new BorderLayout());
        
        // ========== HEADER ==========
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(231, 76, 60));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        headerPanel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("WomenSafe");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        navPanel.setOpaque(false);
        
        JButton loginBtn = createNavButton("Login");
        JButton registerBtn = createNavButton("Register");
        JButton adminBtn = createNavButton("Admin");
        
        navPanel.add(loginBtn);
        navPanel.add(registerBtn);
        navPanel.add(adminBtn);
        headerPanel.add(navPanel, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // ========== CENTER PANEL - ALL CONTENT CENTERED ==========
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(255, 240, 245));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Main Heading - CENTERED
        JLabel headingLabel = new JLabel("WomenSafe");
        headingLabel.setFont(new Font("Segoe UI", Font.BOLD, 52));
        headingLabel.setForeground(new Color(231, 76, 60));
        centerPanel.add(headingLabel, gbc);
        
        // Tagline - CENTERED
        gbc.gridy = 1;
        JLabel taglineLabel = new JLabel("Monitoring Safety, Enabling Protection");
        taglineLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        taglineLabel.setForeground(new Color(52, 73, 94));
        centerPanel.add(taglineLabel, gbc);
        
        // Description - CENTERED using HTML
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 30, 20, 30);
        
        String description = "<html><div style='text-align:center; font-family:Segoe UI; font-size:17px; color:#34495e; line-height:1.8;'>"
            + "WomenSafe is a comprehensive women safety system that provides:<br><br>"
            + "&nbsp;&nbsp;&nbsp; One-click SOS alerts with live location sharing<br>"
            + "&nbsp;&nbsp;&nbsp; Emergency contact management<br>"
            + "&nbsp;&nbsp;&nbsp; Nearby police station and hospital finder<br>"
            + "&nbsp;&nbsp;&nbsp; Real-time email notifications to emergency contacts and admin"
            + "</div></html>";
        
        JLabel descLabel = new JLabel(description);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(descLabel, gbc);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // ========== FOOTER ==========
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(44, 62, 80));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        
        JLabel footerLabel = new JLabel("(c) 2026 WomenSafe Project | MCA - New Horizon College of Engineering");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        footerLabel.setForeground(new Color(189, 195, 199));
        footerPanel.add(footerLabel, BorderLayout.WEST);
        
        JPanel helplinePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        helplinePanel.setOpaque(false);
        JLabel helplineLabel = new JLabel("EMERGENCY: Police: 100 | Women Helpline: 1091 | Ambulance: 108");
        helplineLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        helplineLabel.setForeground(new Color(189, 195, 199));
        helplinePanel.add(helplineLabel);
        footerPanel.add(helplinePanel, BorderLayout.EAST);
        
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        loginBtn.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });
        
        registerBtn.addActionListener(e -> {
            new RegisterFrame();
            dispose();
        });
        
        adminBtn.addActionListener(e -> {
            new AdminLoginFrame();
            dispose();
        });
        
        setVisible(true);
    }
    
    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(231, 76, 60));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 25, 8, 25));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        return btn;
    }
}