package com.womensafe;

import com.womensafe.views.LandingPageFrame;
import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf; // Make sure this is here

public class Main {
    public static void main(String[] args) {
        // Set the modern FlatLaf Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf()); 
            
            // This rounds the corners of buttons and text fields
            UIManager.put("Button.arc", 15); 
            UIManager.put("Component.arc", 15); 
            UIManager.put("TextComponent.arc", 15);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Launch the application with Landing Page
        new LandingPageFrame();
    }
}