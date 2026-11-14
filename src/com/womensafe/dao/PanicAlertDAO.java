package com.womensafe.dao;

import java.sql.*;
import com.womensafe.config.DatabaseConnection;
import com.womensafe.models.PanicAlert;

public class PanicAlertDAO {
    
    // Save panic alert
    public boolean savePanicAlert(PanicAlert alert) {
        String sql = "INSERT INTO `Panic Alerts` (user_id, location_id, status) VALUES (?, ?, ?)";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, alert.getUserId());
            pstmt.setInt(2, alert.getLocationId());
            pstmt.setString(3, alert.getStatus());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}