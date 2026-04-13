package com.hotel.model;

import java.io.Serializable;

/**
 * Customer class representing a hotel customer/guest.
 * Demonstrates encapsulation with data validation.
 */
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    
    /**
     * Constructor for Customer
     */
    public Customer(int customerId, String firstName, String lastName, 
                   String email, String phone, String address) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
    
    // Getters and Setters with validation
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        if (customerId > 0) {
            this.customerId = customerId;
        }
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        if (firstName != null && !firstName.trim().isEmpty()) {
            this.firstName = firstName;
        }
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        if (lastName != null && !lastName.trim().isEmpty()) {
            this.lastName = lastName;
        }
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        }
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        if (phone != null && !phone.trim().isEmpty()) {
            this.phone = phone;
        }
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        if (address != null && !address.trim().isEmpty()) {
            this.address = address;
        }
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "ID=" + customerId +
                ", Name='" + getFullName() + '\'' +
                ", Email='" + email + '\'' +
                ", Phone='" + phone + '\'' +
                '}';
    }
}
