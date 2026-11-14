package com.womensafe.utils;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil {
    
    // ⚠️ MAKE SURE THIS IS YOUR REAL GMAIL AND 16-LETTER APP PASSWORD (No spaces)
    private static final String FROM_EMAIL = "YOUR_GMAIL_HERE";
private static final String APP_PASSWORD = "YOUR_APP_PASSWORD_HERE";    
    public static void sendSOSAlert(String toEmail, String userName, String userPhone) {
        String subject = "🚨 EMERGENCY SOS ALERT 🚨";
        String body = "User: " + userName + "\nPhone: " + userPhone + "\n\nPlease contact them immediately!";
        sendEmail(toEmail, subject, body);
    }

       public static boolean sendSOSAlertWithLocation(String toEmail, String userName, String userPhone, String location) {
        String subject = "🚨 EMERGENCY SOS ALERT with LIVE LOCATION 🚨";
        String body = "User: " + userName + "\nPhone: " + userPhone + "\n📍 LIVE LOCATION: " + location + "\n\nPlease contact them immediately!";
        return sendEmail(toEmail, subject, body); // Added 'return'
    }

    public static void sendLocationShare(String toEmail, String userName, String userPhone, String location) {
        String subject = "📍 Location Shared by " + userName;
        String body = userName + " shared their location.\n📍 Location: " + location;
        sendEmail(toEmail, subject, body);
    }

    // THIS METHOD NOW RETURNS TRUE OR FALSE
    public static boolean sendEmail(String toEmail, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        // Add this to increase timeout so the app waits for Gmail
        props.put("mail.smtp.timeout", "10000"); 

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);
            
            // This line actually sends the email and waits for Gmail's response
            Transport.send(message);
            System.out.println("✅ SUCCESS: Email successfully sent to " + toEmail);
            return true; 
            
        } catch (MessagingException e) {
            System.out.println("❌ FAILED: Could not send email to " + toEmail);
            e.printStackTrace(); // This will print the EXACT error in the VS Code terminal
            return false;
        }
    }
}