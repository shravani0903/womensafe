package com.womensafe.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.womensafe.dao.EmergencyContactDAO;
import com.womensafe.models.EmergencyContact;
import com.womensafe.models.User;
import com.womensafe.utils.Theme;

public class EmergencyContactFrame extends JFrame {
    private User user;
    private EmergencyContactDAO contactDAO;
    private JTable contactTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, phoneField, emailField;
    private JComboBox<Integer> priorityCombo;
    
    public EmergencyContactFrame(User user) {
        this.user = user;
        this.contactDAO = new EmergencyContactDAO();
        
        setTitle("WomenSafe - Emergency Contacts");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Main panel with gradient background
        JPanel mainPanel = Theme.createGradientPanel();
        mainPanel.setLayout(new BorderLayout());
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        
        JLabel titleLabel = new JLabel("📞 Emergency Contacts");
        titleLabel.setFont(Theme.TITLE_FONT);
        titleLabel.setForeground(Theme.PRIMARY);
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // ========== ADD CONTACT FORM PANEL ==========
        JPanel formPanel = Theme.createCardPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Theme.INFO), 
            "➕ Add New Contact", 
            0, 0, 
            Theme.SUBHEADING_FONT, 
            Theme.INFO
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Name field
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(Theme.NORMAL_FONT);
        formPanel.add(nameLabel, gbc);
        
        gbc.gridx = 1;
        nameField = new JTextField(15);
        nameField.setFont(Theme.NORMAL_FONT);
        nameField.setPreferredSize(new Dimension(200, 35));
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 210)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(nameField, gbc);
        
        // Phone field
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(Theme.NORMAL_FONT);
        formPanel.add(phoneLabel, gbc);
        
        gbc.gridx = 1;
        phoneField = new JTextField(15);
        phoneField.setFont(Theme.NORMAL_FONT);
        phoneField.setPreferredSize(new Dimension(200, 35));
        phoneField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 210)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(phoneField, gbc);
        
        // Email field
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(Theme.NORMAL_FONT);
        formPanel.add(emailLabel, gbc);
        
        gbc.gridx = 1;
        emailField = new JTextField(15);
        emailField.setFont(Theme.NORMAL_FONT);
        emailField.setPreferredSize(new Dimension(200, 35));
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 210)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(emailField, gbc);
        
        // Priority field
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel priorityLabel = new JLabel("Priority (1=Highest):");
        priorityLabel.setFont(Theme.NORMAL_FONT);
        formPanel.add(priorityLabel, gbc);
        
        gbc.gridx = 1;
        priorityCombo = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        priorityCombo.setFont(Theme.NORMAL_FONT);
        priorityCombo.setPreferredSize(new Dimension(200, 35));
        formPanel.add(priorityCombo, gbc);
        
        // ADD BUTTON - TEXT ONLY (NO BOX)
        gbc.gridx = 1; gbc.gridy = 4;
        JLabel addLabel = new JLabel("ADD CONTACT");
        addLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addLabel.setForeground(Theme.SUCCESS);
        addLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addLabel.setForeground(new Color(30, 180, 90));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addLabel.setForeground(Theme.SUCCESS);
            }
        });
        addLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addContact();
            }
        });
        formPanel.add(addLabel, gbc);
        
        mainPanel.add(formPanel, BorderLayout.NORTH);
        
        // ========== CONTACTS TABLE ==========
        String[] columns = {"ID", "Name", "Phone", "Email", "Priority"};
        tableModel = new DefaultTableModel(columns, 0);
        contactTable = new JTable(tableModel);
        contactTable.setRowHeight(32);
        contactTable.setFont(Theme.NORMAL_FONT);
        contactTable.getTableHeader().setFont(Theme.SUBHEADING_FONT);
        contactTable.getTableHeader().setBackground(Theme.PRIMARY_LIGHT);
        contactTable.getTableHeader().setForeground(Theme.PRIMARY);
        
        JScrollPane scrollPane = new JScrollPane(contactTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Theme.INFO),
            "📋 Your Emergency Contact List",
            0, 0,
            Theme.SUBHEADING_FONT,
            Theme.INFO
        ));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // ========== BOTTOM PANEL - TEXT ONLY BUTTONS ==========
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        // DELETE SELECTED - TEXT ONLY
        JLabel deleteLabel = new JLabel("DELETE SELECTED");
        deleteLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteLabel.setForeground(Theme.ERROR);
        deleteLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deleteLabel.setForeground(new Color(200, 30, 40));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deleteLabel.setForeground(Theme.ERROR);
            }
        });
        deleteLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteContact();
            }
        });
        bottomPanel.add(deleteLabel);
        
        // BACK TO DASHBOARD - TEXT ONLY
        JLabel backLabel = new JLabel("← BACK TO DASHBOARD");
        backLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        backLabel.setForeground(Theme.TEXT_LIGHT);
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
                new UserDashboardFrame(user);
                dispose();
            }
        });
        bottomPanel.add(backLabel);
        
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        loadContacts();
        
        setVisible(true);
    }
    
    private void loadContacts() {
        tableModel.setRowCount(0);
        List<EmergencyContact> contacts = contactDAO.getContactsByUserId(user.getUserId());
        for (EmergencyContact c : contacts) {
            tableModel.addRow(new Object[]{
                c.getContactId(), 
                c.getContactName(), 
                c.getContactPhone(), 
                c.getEmail(),
                c.getPriorityLevel()
            });
        }
    }
    
    private void addContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        
        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Please enter name and phone number!");
            return;
        }
        
        EmergencyContact contact = new EmergencyContact(
            user.getUserId(), 
            name, 
            phone, 
            email,
            (int) priorityCombo.getSelectedItem()
        );
        
        if (contactDAO.addContact(contact)) {
            JOptionPane.showMessageDialog(this, "✅ Contact added successfully!");
            nameField.setText("");
            phoneField.setText("");
            emailField.setText("");
            loadContacts();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Failed to add contact!");
        }
    }
    
    private void deleteContact() {
        int row = contactTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Please select a contact to delete!");
            return;
        }
        
        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Are you sure you want to delete this contact?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            contactDAO.deleteContact(id);
            loadContacts();
            JOptionPane.showMessageDialog(this, "✅ Contact deleted successfully!");
        }
    }
}