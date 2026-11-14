package com.womensafe.models;

public class EmergencyContact {
    private int contactId;
    private int userId;
    private String contactName;
    private String contactPhone;
    private String email;          // ← ADD THIS
    private int priorityLevel;
    
    // Constructors
    public EmergencyContact() {}
    
    public EmergencyContact(int userId, String contactName, String contactPhone, String email, int priorityLevel) {
        this.userId = userId;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.email = email;        // ← ADD THIS
        this.priorityLevel = priorityLevel;
    }
    
    // Getters and Setters
    public int getContactId() { return contactId; }
    public void setContactId(int contactId) { this.contactId = contactId; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    
    public String getEmail() { return email; }           // ← ADD THIS
    public void setEmail(String email) { this.email = email; }  // ← ADD THIS
    
    public int getPriorityLevel() { return priorityLevel; }
    public void setPriorityLevel(int priorityLevel) { this.priorityLevel = priorityLevel; }
}