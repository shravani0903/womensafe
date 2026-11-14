package com.womensafe.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.womensafe.dao.SafeLocationDAO;
import com.womensafe.models.SafeLocation;
import com.womensafe.models.User;

public class SafeLocationFrame extends JFrame {
    private User user;
    private SafeLocationDAO locationDAO;
    private JTable locationTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> typeCombo;
    
    public SafeLocationFrame(User user) {
        this.user = user;
        this.locationDAO = new SafeLocationDAO();
        
        setTitle("WomenSafe - Safe Locations");
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
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JLabel titleLabel = new JLabel("Safe Locations Near You");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(155, 89, 182));
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        filterPanel.setOpaque(false);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(155, 89, 182), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        JLabel filterLabel = new JLabel("Filter by type:");
        filterLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        filterLabel.setForeground(new Color(52, 73, 94));
        filterPanel.add(filterLabel);
        
        typeCombo = new JComboBox<>(new String[]{"All", "Police Station", "Hospital", "NGO"});
        typeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        typeCombo.setBackground(Color.WHITE);
        typeCombo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 190)),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        typeCombo.setPreferredSize(new Dimension(150, 35));
        filterPanel.add(typeCombo);
        
        JButton filterButton = new JButton("Search");
        filterButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        filterButton.setForeground(new Color(155, 89, 182));
        filterButton.setBorderPainted(false);
        filterButton.setContentAreaFilled(false);
        filterButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        filterButton.addActionListener(e -> loadLocations((String)typeCombo.getSelectedItem()));
        filterPanel.add(filterButton);
        
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        
        String[] columns = {"ID", "Name", "Type", "Address"};
        tableModel = new DefaultTableModel(columns, 0);
        locationTable = new JTable(tableModel);
        locationTable.setRowHeight(32);
        locationTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        locationTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        locationTable.getTableHeader().setBackground(new Color(155, 89, 182, 50));
        
        JScrollPane scrollPane = new JScrollPane(locationTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(155, 89, 182), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setForeground(new Color(149, 165, 166));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            new UserDashboardFrame(user);
            dispose();
        });
        bottomPanel.add(backButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        loadLocations("All");
        setVisible(true);
    }
    
    private void loadLocations(String type) {
        tableModel.setRowCount(0);
        List<SafeLocation> locations = type.equals("All") ? 
            locationDAO.getAllSafeLocations() : locationDAO.getLocationsByType(type);
        
        for (SafeLocation loc : locations) {
            tableModel.addRow(new Object[]{
                loc.getSafeId(), 
                loc.getName(), 
                loc.getType(), 
                loc.getAddress()
            });
        }
        
        if (locations.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No locations found for: " + type);
        }
    }
}