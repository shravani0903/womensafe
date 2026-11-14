package com.womensafe.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import com.womensafe.config.DatabaseConnection;

public class ManageSafeLocationsFrame extends JFrame {
    private JTable locationTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, typeField, latField, lngField, addressField;
    
    public ManageSafeLocationsFrame() {
        setTitle("Admin - Manage Safe Locations");
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
        
        JLabel titleLabel = new JLabel("Manage Safe Locations");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(46, 204, 113));
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(46, 204, 113), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        nameLabel.setForeground(new Color(52, 73, 94));
        formPanel.add(nameLabel, gbc);
        
        gbc.gridx = 1;
        nameField = new JTextField(15);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameField.setBackground(Color.WHITE);
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 190)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        formPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        typeLabel.setForeground(new Color(52, 73, 94));
        formPanel.add(typeLabel, gbc);
        
        gbc.gridx = 1;
        typeField = new JTextField(15);
        typeField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        typeField.setBackground(Color.WHITE);
        typeField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 190)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        formPanel.add(typeField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel latLabel = new JLabel("Latitude:");
        latLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        latLabel.setForeground(new Color(52, 73, 94));
        formPanel.add(latLabel, gbc);
        
        gbc.gridx = 1;
        latField = new JTextField(15);
        latField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        latField.setBackground(Color.WHITE);
        latField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 190)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        formPanel.add(latField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lngLabel = new JLabel("Longitude:");
        lngLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lngLabel.setForeground(new Color(52, 73, 94));
        formPanel.add(lngLabel, gbc);
        
        gbc.gridx = 1;
        lngField = new JTextField(15);
        lngField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lngField.setBackground(Color.WHITE);
        lngField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 190)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        formPanel.add(lngField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        addressLabel.setForeground(new Color(52, 73, 94));
        formPanel.add(addressLabel, gbc);
        
        gbc.gridx = 1;
        addressField = new JTextField(15);
        addressField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addressField.setBackground(Color.WHITE);
        addressField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 190)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        formPanel.add(addressField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        
        JButton addButton = new JButton("Add Location");
        addButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addButton.setForeground(new Color(46, 204, 113));
        addButton.setBorderPainted(false);
        addButton.setContentAreaFilled(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(e -> addLocation());
        
        JLabel separator = new JLabel("|");
        separator.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        separator.setForeground(new Color(200, 200, 210));
        
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        clearButton.setForeground(new Color(149, 165, 166));
        clearButton.setBorderPainted(false);
        clearButton.setContentAreaFilled(false);
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener(e -> clearForm());
        
        buttonPanel.add(addButton);
        buttonPanel.add(separator);
        buttonPanel.add(clearButton);
        formPanel.add(buttonPanel, gbc);
        
        mainPanel.add(formPanel, BorderLayout.NORTH);
        
        String[] columns = {"ID", "Name", "Type", "Latitude", "Longitude", "Address"};
        tableModel = new DefaultTableModel(columns, 0);
        locationTable = new JTable(tableModel);
        locationTable.setRowHeight(32);
        locationTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        locationTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        locationTable.getTableHeader().setBackground(new Color(46, 204, 113, 50));
        
        JScrollPane scrollPane = new JScrollPane(locationTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(46, 204, 113), 1),
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
            new AdminDashboardFrame();
            dispose();
        });
        bottomPanel.add(backButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        loadLocations();
        locationTable.getSelectionModel().addListSelectionListener(e -> {
            int row = locationTable.getSelectedRow();
            if (row != -1) {
                nameField.setText(tableModel.getValueAt(row, 1).toString());
                typeField.setText(tableModel.getValueAt(row, 2).toString());
                latField.setText(tableModel.getValueAt(row, 3).toString());
                lngField.setText(tableModel.getValueAt(row, 4).toString());
                addressField.setText(tableModel.getValueAt(row, 5).toString());
            }
        });
        
        setVisible(true);
    }
    
    private void loadLocations() {
        tableModel.setRowCount(0);
        String sql = "SELECT * FROM `Safe Locations` ORDER BY type, name";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("safe_id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude"),
                    rs.getString("address")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void addLocation() {
        String name = nameField.getText().trim();
        String type = typeField.getText().trim();
        String latText = latField.getText().trim();
        String lngText = lngField.getText().trim();
        String address = addressField.getText().trim();
        
        if (name.isEmpty() || type.isEmpty() || latText.isEmpty() || lngText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }
        
        String sql = "INSERT INTO `Safe Locations` (name, type, latitude, longitude, address) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setDouble(3, Double.parseDouble(latText));
            pstmt.setDouble(4, Double.parseDouble(lngText));
            pstmt.setString(5, address);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Location added successfully!");
            clearForm();
            loadLocations();
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private void clearForm() {
        nameField.setText("");
        typeField.setText("");
        latField.setText("");
        lngField.setText("");
        addressField.setText("");
        locationTable.clearSelection();
    }
}