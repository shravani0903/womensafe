package com.womensafe.views;

import javax.swing.*;
import java.awt.*;
import com.womensafe.utils.Theme;

public class AdminDashboardFrame extends JFrame {
    
    public AdminDashboardFrame() {
        setTitle("WomenSafe - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Main panel with gradient
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
                g2d.setColor(new Color(255, 255, 255, 15));
                g2d.fillOval(-50, -50, 200, 200);
                g2d.fillOval(getWidth() - 150, getHeight() - 150, 250, 250);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 20, 40));
        
        JLabel titleLabel = new JLabel("ADMIN DASHBOARD");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JLabel adminLabel = new JLabel("Authorized Access Only");
        adminLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        adminLabel.setForeground(new Color(180, 180, 220));
        headerPanel.add(adminLabel, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Cards Panel
        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 30, 30));
        cardsPanel.setOpaque(false);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 80, 100));
        
        JButton usersBtn = createAdminCard("View All Users", "See all registered users", new Color(52, 152, 219));
        JButton panicBtn = createAdminCard("View Panic Alerts", "See all SOS emergency alerts", new Color(231, 76, 60));
        JButton safeBtn = createAdminCard("Manage Safe Locations", "Add/Edit/Delete safe places", new Color(46, 204, 113));
        JButton logoutBtn = createAdminCard("Logout", "Exit admin panel", new Color(149, 165, 166));
        
        cardsPanel.add(usersBtn);
        cardsPanel.add(panicBtn);
        cardsPanel.add(safeBtn);
        cardsPanel.add(logoutBtn);
        
        mainPanel.add(cardsPanel, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setOpaque(false);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
        JLabel footerLabel = new JLabel("Manage your WomenSafe application from here");
        footerLabel.setFont(Theme.SMALL_FONT);
        footerLabel.setForeground(new Color(180, 180, 220));
        footerPanel.add(footerLabel);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        usersBtn.addActionListener(e -> { new ViewUsersFrame(); dispose(); });
        panicBtn.addActionListener(e -> { new ViewPanicAlertsFrame(); dispose(); });
        safeBtn.addActionListener(e -> { new ManageSafeLocationsFrame(); dispose(); });
        logoutBtn.addActionListener(e -> { new LoginFrame(); dispose(); });
        
        setVisible(true);
    }
    
    private JButton createAdminCard(String title, String desc, Color color) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout());
        btn.setBackground(new Color(255, 255, 255, 230));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(25, 20, 25, 20)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(color);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel descLabel = new JLabel(desc);
        descLabel.setFont(Theme.SMALL_FONT);
        descLabel.setForeground(Theme.TEXT_LIGHT);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel);
        textPanel.add(descLabel);
        
        btn.add(textPanel, BorderLayout.CENTER);
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
                btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(color.brighter(), 3),
                    BorderFactory.createEmptyBorder(25, 20, 25, 20)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(255, 255, 255, 230));
                btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(color, 2),
                    BorderFactory.createEmptyBorder(25, 20, 25, 20)
                ));
            }
        });
        
        return btn;
    }
}