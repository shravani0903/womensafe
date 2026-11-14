package com.womensafe.models;

import java.util.Date;

public class PanicAlert {
    private int alertId;
    private int userId;
    private int locationId;
    private Date alertTime;
    private String status;
    
    // Constructors
    public PanicAlert() {}
    
    public PanicAlert(int userId, int locationId, String status) {
        this.userId = userId;
        this.locationId = locationId;
        this.status = status;
    }
    
    // Getters and Setters
    public int getAlertId() { return alertId; }
    public void setAlertId(int alertId) { this.alertId = alertId; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public int getLocationId() { return locationId; }
    public void setLocationId(int locationId) { this.locationId = locationId; }
    
    public Date getAlertTime() { return alertTime; }
    public void setAlertTime(Date alertTime) { this.alertTime = alertTime; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}