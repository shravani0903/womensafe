package com.womensafe.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.womensafe.config.DatabaseConnection;
import com.womensafe.models.EmergencyContact;

public class EmergencyContactDAO {
    
    public boolean addContact(EmergencyContact contact) {
        String sql = "INSERT INTO `Emergency Contacts` (user_id, contact_name, contact_phone,email, priority_level) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, contact.getUserId());
            pstmt.setString(2, contact.getContactName());
            pstmt.setString(3, contact.getContactPhone());
            pstmt.setString(4, contact.getEmail());  
            pstmt.setInt(5, contact.getPriorityLevel());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<EmergencyContact> getContactsByUserId(int userId) {
        List<EmergencyContact> contacts = new ArrayList<>();
        String sql = "SELECT contact_id, user_id, contact_name, contact_phone, email, priority_level FROM `Emergency Contacts` WHERE user_id = ? ORDER BY priority_level ASC";        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                EmergencyContact contact = new EmergencyContact();
                contact.setContactId(rs.getInt("contact_id"));
                contact.setUserId(rs.getInt("user_id"));
                contact.setContactName(rs.getString("contact_name"));
                contact.setContactPhone(rs.getString("contact_phone"));
                contact.setEmail(rs.getString("email"));
                contact.setPriorityLevel(rs.getInt("priority_level"));
                
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }
    
    public boolean deleteContact(int contactId) {
        String sql = "DELETE FROM `Emergency Contacts` WHERE contact_id = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, contactId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}