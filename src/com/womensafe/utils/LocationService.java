package com.womensafe.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class LocationService {
    
    public static String getCurrentLocation() {
        try {
            // Free IP-based location API
            URL url = new URL("http://ip-api.com/json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            JSONObject json = new JSONObject(response.toString());
            if (json.getString("status").equals("success")) {
                String city = json.getString("city");
                String region = json.getString("regionName");
                double lat = json.getDouble("lat");
                double lon = json.getDouble("lon");
                
                // This creates a clickable Google Maps link
                String mapLink = "https://www.google.com/maps?q=" + lat + "," + lon;
                
                return city + ", " + region + "\n🗺️ Map Link: " + mapLink;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unable to detect location. Map Link: https://www.google.com/maps";
    }
}