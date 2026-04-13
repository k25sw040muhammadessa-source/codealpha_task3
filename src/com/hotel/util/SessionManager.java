package com.hotel.util;

import java.util.HashMap;
import java.util.Map;

/**
 * SessionManager handles user session and context throughout the application.
 */
public class SessionManager {
    private static SessionManager instance;
    private Map<String, Object> sessionData;
    private int currentCustomerId;
    
    private SessionManager() {
        sessionData = new HashMap<>();
    }
    
    /**
     * Get singleton instance of SessionManager
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    /**
     * Store data in session
     */
    public void putData(String key, Object value) {
        sessionData.put(key, value);
    }
    
    /**
     * Retrieve data from session
     */
    public Object getData(String key) {
        return sessionData.get(key);
    }
    
    /**
     * Remove data from session
     */
    public void removeData(String key) {
        sessionData.remove(key);
    }
    
    /**
     * Clear all session data
     */
    public void clearSession() {
        sessionData.clear();
    }
    
    /**
     * Set current customer ID
     */
    public void setCurrentCustomerId(int customerId) {
        this.currentCustomerId = customerId;
    }
    
    /**
     * Get current customer ID
     */
    public int getCurrentCustomerId() {
        return currentCustomerId;
    }
}
