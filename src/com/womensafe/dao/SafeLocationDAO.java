package com.womensafe.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.womensafe.config.DatabaseConnection;
import com.womensafe.models.SafeLocation;

public class SafeLocationDAO {
    
    // Get all safe locations
    public List<SafeLocation> getAllSafeLocations() {
        List<SafeLocation> locations = new ArrayList<>();
        String sql = "SELECT * FROM `Safe Locations` ORDER BY type, name";
        
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                SafeLocation location = new SafeLocation();
                location.setSafeId(rs.getInt("safe_id"));
                location.setName(rs.getString("name"));
                location.setType(rs.getString("type"));
                location.setLatitude(rs.getDouble("latitude"));
                location.setLongitude(rs.getDouble("longitude"));
                location.setAddress(rs.getString("address"));
                locations.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }
    
    // Get locations by type (Police Station, Hospital, NGO)
    public List<SafeLocation> getLocationsByType(String type) {
        List<SafeLocation> locations = new ArrayList<>();
        String sql = "SELECT * FROM `Safe Locations` WHERE type = ? ORDER BY name";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, type);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                SafeLocation location = new SafeLocation();
                location.setSafeId(rs.getInt("safe_id"));
                location.setName(rs.getString("name"));
                location.setType(rs.getString("type"));
                location.setLatitude(rs.getDouble("latitude"));
                location.setLongitude(rs.getDouble("longitude"));
                location.setAddress(rs.getString("address"));
                locations.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }
}