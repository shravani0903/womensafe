package com.womensafe.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import com.womensafe.dao.EmergencyContactDAO;
import com.womensafe.dao.PanicAlertDAO;
import com.womensafe.models.EmergencyContact;
import com.womensafe.models.User;
import com.womensafe.models.PanicAlert;
import com.womensafe.utils.EmailUtil;
import com.womensafe.utils.LocationService;

public class SOSFrame extends JFrame {
    private User user;
    private EmergencyContactDAO contactDAO;
    private PanicAlertDAO panicAlertDAO;
    private JLabel statusLabel;
    private JButton sosButton;
    
    public SOSFrame(User user) {
        this.user = user;
        this.contactDAO = new EmergencyContactDAO();
        this.panicAlertDAO = new PanicAlertDAO();
        
        setTitle("WomenSafe - EMERGENCY");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Very dark, serious background
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(30, 30, 30)); 
        
        // --- Main Card ---
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(new Color(45, 45, 45));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80), 2, true),
            BorderFactory.createEmptyBorder(50, 80, 50, 80)
        ));
        
        // Header
        JLabel warningLabel = new JLabel("⚠️ EMERGENCY SOS ⚠️");
        warningLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 36));
        warningLabel.setForeground(new Color(231, 76, 60));
        warningLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel infoLabel = new JLabel("Press the button below ONLY in an emergency.");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoLabel.setForeground(Color.LIGHT_GRAY);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Status Label (Shows "Sending..." or "Sent!")
        statusLabel = new JLabel("System Ready.");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        statusLabel.setForeground(new Color(46, 204, 113)); // Green
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // --- GIANT ROUND SOS BUTTON ---
        sosButton = new JButton("SOS") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(new Color(150, 30, 30)); // Darker when clicked
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(255, 80, 80)); // Lighter when hovered
                } else {
                    g2.setColor(new Color(220, 20, 60)); // Standard Crimson Red
                }
                
                g2.fill(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        
        sosButton.setFont(new Font("Segoe UI", Font.BOLD, 48));
        sosButton.setForeground(Color.WHITE);
        sosButton.setContentAreaFilled(false); // Let custom paint show
        sosButton.setFocusPainted(false);
        sosButton.setBorderPainted(false);
        sosButton.setPreferredSize(new Dimension(200, 200));
        sosButton.setMaximumSize(new Dimension(200, 200));
        sosButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sosButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        sosButton.addActionListener(e -> triggerSOS());
        
        // Back Button
        JButton backButton = new JButton("← Cancel & Go Back");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setForeground(Color.GRAY);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> {
            new UserDashboardFrame(user);
            dispose();
        });
        
        // Add everything to card
        cardPanel.add(warningLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        cardPanel.add(infoLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        cardPanel.add(sosButton);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        cardPanel.add(statusLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        cardPanel.add(backButton);
        
        mainPanel.add(cardPanel);
        add(mainPanel);
        setVisible(true);
    }
    
    private void triggerSOS() {
        // Disable button and show loading status
        sosButton.setEnabled(false);
        statusLabel.setText("📍 Fetching location and sending alerts... Please wait.");
        statusLabel.setForeground(Color.ORANGE);
        
        // Run in a separate thread so UI doesn't freeze
        new Thread(() -> {
            String liveLocation = LocationService.getCurrentLocation();
            
            PanicAlert alert = new PanicAlert();
            alert.setUserId(user.getUserId());
            alert.setLocationId(1); 
            alert.setStatus("EMERGENCY SOS! Location: " + liveLocation);
            panicAlertDAO.savePanicAlert(alert);
            
            java.util.List<EmergencyContact> contacts = contactDAO.getContactsByUserId(user.getUserId());
            
            if (contacts.isEmpty()) {
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("❌ No emergency contacts found!");
                    statusLabel.setForeground(Color.RED);
                    sosButton.setEnabled(true);
                });
                return;
            }

            int successCount = 0;
            for (EmergencyContact c : contacts) {
                if (c.getEmail() != null && !c.getEmail().isEmpty()) {
                    boolean sent = EmailUtil.sendSOSAlertWithLocation(c.getEmail(), user.getUsername(), user.getPhone(), liveLocation);
                    if (sent) successCount++;
                }
            }
            
            final int finalSuccess = successCount;
            
            // Update UI when done
            SwingUtilities.invokeLater(() -> {
                if (finalSuccess > 0) {
                    statusLabel.setText("✅ SOS SENT! Emails dispatched to " + finalSuccess + " contacts.");
                    statusLabel.setForeground(new Color(46, 204, 113));
                } else {
                    statusLabel.setText("⚠️ Failed to send emails. Check network.");
                    statusLabel.setForeground(Color.RED);
                }
                sosButton.setEnabled(true);
            });
        }).start();
    }
}