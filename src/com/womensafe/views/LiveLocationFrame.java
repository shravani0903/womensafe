package com.womensafe.views;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.womensafe.dao.EmergencyContactDAO;
import com.womensafe.models.EmergencyContact;
import com.womensafe.models.User;
import com.womensafe.utils.EmailUtil;
import com.womensafe.utils.LocationService; // Importing the new service
import java.sql.*;
import com.womensafe.config.DatabaseConnection;

public class LiveLocationFrame extends JFrame {
    private User user;
    private EmergencyContactDAO contactDAO;
    private JTextArea locationField;
    private JTextArea messageArea;
    
    public LiveLocationFrame(User user) {
        this.user = user;
        this.contactDAO = new EmergencyContactDAO();
        
        setTitle("WomenSafe - Share Location");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(255, 240, 245));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(46, 204, 113));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("📍 Share My Live Location");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Center Panel
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        centerPanel.setBackground(new Color(255, 240, 245));
        
        // LEFT PANEL - Location Form
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(46, 204, 113), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        formPanel.setLayout(new BorderLayout());
        
        JLabel formTitle = new JLabel("📍 LOCATION DETAILS");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        formTitle.setForeground(new Color(46, 204, 113));
        formTitle.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(formTitle, BorderLayout.NORTH);
        
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        inputPanel.setBackground(Color.WHITE);
        
        locationField = new JTextArea(4, 30);
        locationField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        locationField.setLineWrap(true);
        locationField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        locationField.setBackground(new Color(248, 248, 252));
        JScrollPane scrollPane = new JScrollPane(locationField);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(400, 80));
        inputPanel.add(scrollPane, BorderLayout.CENTER);
        
        // ==========================================
        // NEW: AUTO-DETECT BUTTON
        // ==========================================
        JPanel autoDetectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        autoDetectPanel.setBackground(Color.WHITE);
        
        JLabel autoDetectLabel = new JLabel("📍 Auto-Detect My Location");
        autoDetectLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        autoDetectLabel.setForeground(new Color(52, 152, 219));
        autoDetectLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        autoDetectLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                autoDetectLabel.setText("⌛ Detecting... Please wait...");
                autoDetectLabel.setForeground(Color.GRAY);
                
                // Run in a separate thread so UI doesn't freeze
                new Thread(() -> {
                    String autoLocation = LocationService.getCurrentLocation();
                    SwingUtilities.invokeLater(() -> {
                        locationField.setText(autoLocation);
                        autoDetectLabel.setText("🔄 Detect Again");
                        autoDetectLabel.setForeground(new Color(52, 152, 219));
                    });
                }).start();
            }
        });
        autoDetectPanel.add(autoDetectLabel);
        
        JPanel bottomInputPanel = new JPanel(new BorderLayout());
        bottomInputPanel.setBackground(Color.WHITE);
        bottomInputPanel.add(autoDetectPanel, BorderLayout.NORTH);
        
        JLabel tipLabel = new JLabel("💡 Or type manually: Near City Mall, Main Road");
        tipLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tipLabel.setForeground(Color.GRAY);
        bottomInputPanel.add(tipLabel, BorderLayout.SOUTH);
        
        inputPanel.add(bottomInputPanel, BorderLayout.SOUTH);
        // ==========================================
        
        formPanel.add(inputPanel, BorderLayout.CENTER);
        
        // ========== SHARE BUTTON - TEXT ONLY (NO BOX) ==========
        JLabel shareLabel = new JLabel("SHARE MY LOCATION");
        shareLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        shareLabel.setForeground(new Color(46, 204, 113));
        shareLabel.setHorizontalAlignment(SwingConstants.CENTER);
        shareLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        shareLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                shareLabel.setForeground(new Color(30, 180, 90));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                shareLabel.setForeground(new Color(46, 204, 113));
            }
        });
        shareLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                shareLocation();
            }
        });
        formPanel.add(shareLabel, BorderLayout.SOUTH);
        
        centerPanel.add(formPanel);
        
        // RIGHT PANEL - Status
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(Color.WHITE);
        statusPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        statusPanel.setLayout(new BorderLayout());
        
        JLabel statusTitle = new JLabel("📢 LOCATION STATUS");
        statusTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        statusTitle.setForeground(new Color(52, 152, 219));
        statusTitle.setHorizontalAlignment(SwingConstants.CENTER);
        statusPanel.add(statusTitle, BorderLayout.NORTH);
        
        messageArea = new JTextArea(14, 40);
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        messageArea.setBackground(new Color(250, 255, 250));
        messageArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane msgScroll = new JScrollPane(messageArea);
        statusPanel.add(msgScroll, BorderLayout.CENTER);
        
        centerPanel.add(statusPanel);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // ========== BACK BUTTON - TEXT ONLY (NO BOX) ==========
        JPanel backPanel = new JPanel();
        backPanel.setBackground(new Color(255, 240, 245));
        backPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        
        JLabel backLabel = new JLabel("← BACK TO DASHBOARD");
        backLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        backLabel.setForeground(new Color(100, 100, 120));
        backLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backLabel.setForeground(new Color(46, 204, 113));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backLabel.setForeground(new Color(100, 100, 120));
            }
        });
        backLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new UserDashboardFrame(user);
                dispose();
            }
        });
        backPanel.add(backLabel);
        mainPanel.add(backPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        setVisible(true);
    }
    
    private void shareLocation() {
        String location = locationField.getText().trim();
        
        if (location.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Please enter location description or click Auto-Detect!");
            return;
        }
        
        List<EmergencyContact> contacts = contactDAO.getContactsByUserId(user.getUserId());
        
        if (contacts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ No emergency contacts found!\nPlease add contacts first.");
            return;
        }
        
        // Save to database
        String sql = "INSERT INTO Location (user_id, location_text) VALUES (?, ?)";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, user.getUserId());
            pstmt.setString(2, location);
            pstmt.executeUpdate();
            System.out.println("✅ Location saved to database!");
        } catch (SQLException ex) { 
            ex.printStackTrace(); 
        }
        
        // Send email to each contact
        System.out.println("📧 Sending location emails to contacts...");
        for (EmergencyContact c : contacts) {
            String email = c.getEmail();
            if (email != null && !email.isEmpty()) {
                System.out.println("📧 Sending location email to: " + email);
                EmailUtil.sendLocationShare(
                    email,
                    user.getUsername(),
                    user.getPhone(),
                    location
                );
            } else {
                System.out.println("⚠️ No email found for contact: " + c.getContactName());
            }
        }
        
        // Build message
        StringBuilder msg = new StringBuilder();
        msg.append("=============================================================\n");
        msg.append("              📍📍📍 LOCATION SHARED 📍📍📍\n");
        msg.append("=============================================================\n\n");
        msg.append("  USER DETAILS:\n");
        msg.append("  ---------------------------------------------------------\n");
        msg.append("     👤 Name      : ").append(user.getUsername()).append("\n");
        msg.append("     📞 Phone     : ").append(user.getPhone()).append("\n");
        msg.append("     📍 Location  : ").append(location).append("\n");
        msg.append("  ---------------------------------------------------------\n\n");
        msg.append("  📞 NOTIFIED CONTACTS:\n");
        msg.append("  ---------------------------------------------------------\n");
        
        for (EmergencyContact c : contacts) {
            msg.append("     • ").append(c.getContactName()).append(" → ").append(c.getContactPhone());
            if (c.getEmail() != null && !c.getEmail().isEmpty()) {
                msg.append(" (📧 ").append(c.getEmail()).append(")");
            }
            msg.append("\n");
        }
        
        msg.append("  ---------------------------------------------------------\n\n");
        msg.append("  ✅ This is a NORMAL location update. No emergency.\n");
        msg.append("=============================================================");
        
        messageArea.setText(msg.toString());
        messageArea.setForeground(new Color(46, 204, 113));
        messageArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        
        JOptionPane.showMessageDialog(this, 
            "✅ Location shared with " + contacts.size() + " contacts!\n\n" +
            "This is a normal location update, not an emergency.", 
            "📍 LOCATION SHARED", 
            JOptionPane.INFORMATION_MESSAGE);
        
        locationField.setText("");
    }
}