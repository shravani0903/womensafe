package com.womensafe.views;

import javax.swing.*;
import java.awt.*;
import com.womensafe.models.User;
import com.womensafe.utils.Theme;

public class UserDashboardFrame extends JFrame {
    
    public UserDashboardFrame(User user) {
        setTitle("WomenSafe - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Main panel with gradient
        JPanel mainPanel = Theme.createGradientPanel();
        mainPanel.setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 20, 40));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername() + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        welcomeLabel.setForeground(Theme.PRIMARY);
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        
        JLabel dateLabel = new JLabel(new java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a").format(new java.util.Date()));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateLabel.setForeground(Theme.TEXT_LIGHT);
        headerPanel.add(dateLabel, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Cards Panel
        JPanel cardsPanel = new JPanel(new GridLayout(2, 3, 40, 40));
        cardsPanel.setOpaque(false);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 60, 100));
        
        JButton contactsBtn = createRoundedCard("Emergency Contacts", "Manage your trusted contacts", new Color(52, 152, 219));
        JButton sosBtn = createRoundedCard("SOS Alert", "Send emergency alert instantly", new Color(231, 76, 60));
        JButton locationBtn = createRoundedCard("Share Location", "Share your live location", new Color(46, 204, 113));
        JButton safeBtn = createRoundedCard("Safe Places", "Find police & hospitals nearby", new Color(155, 89, 182));
        JButton logoutBtn = createRoundedCard("Logout", "Exit your account", new Color(149, 165, 166));
        JButton backBtn = createRoundedCard("Back to Login", "Return to login screen", new Color(241, 196, 15));
        
        cardsPanel.add(contactsBtn);
        cardsPanel.add(sosBtn);
        cardsPanel.add(locationBtn);
        cardsPanel.add(safeBtn);
        cardsPanel.add(logoutBtn);
        cardsPanel.add(backBtn);
        
        mainPanel.add(cardsPanel, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setOpaque(false);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
        JLabel footerLabel = new JLabel("Your Safety is Our Priority | WomenSafe v2.0");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        footerLabel.setForeground(Theme.TEXT_LIGHT);
        footerPanel.add(footerLabel);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        contactsBtn.addActionListener(e -> { new EmergencyContactFrame(user); dispose(); });
        sosBtn.addActionListener(e -> { new SOSFrame(user); dispose(); });
        locationBtn.addActionListener(e -> { new LiveLocationFrame(user); dispose(); });
        safeBtn.addActionListener(e -> { new SafeLocationFrame(user); dispose(); });
        logoutBtn.addActionListener(e -> { new LoginFrame(); dispose(); });
        backBtn.addActionListener(e -> { new LoginFrame(); dispose(); });
        
        setVisible(true);
    }
    
    private JButton createRoundedCard(String title, String desc, Color color) {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2d.dispose();
                super.paintComponent(g);
            }
            
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(color);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 40, 40);
                g2d.dispose();
            }
        };
        btn.setLayout(new GridBagLayout());
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));  // ✅ FIXED: 4 parameters
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title - Bigger font
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(color);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        btn.add(titleLabel, gbc);
        
        // Description
        JLabel descLabel = new JLabel(desc);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descLabel.setForeground(Color.GRAY);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        btn.add(descLabel, gbc);
        
        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(250, 250, 255));
                btn.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
                btn.repaint();
            }
        });
        
        return btn;
    }
}