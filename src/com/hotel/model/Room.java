package com.hotel.model;

import java.io.Serializable;

/**
 * Abstract base class representing a hotel room.
 * Demonstrates abstraction and encapsulation principles.
 */
public abstract class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected int roomId;
    protected String roomType;
    protected double pricePerNight;
    protected boolean available;
    protected int capacity;
    protected String description;
    
    /**
     * Constructor for Room
     * @param roomId Unique identifier for the room
     * @param roomType Type of the room
     * @param pricePerNight Price per night
     * @param capacity Number of guests the room can accommodate
     * @param description Room description
     */
    public Room(int roomId, String roomType, double pricePerNight, int capacity, String description) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.available = true;
        this.capacity = capacity;
        this.description = description;
    }
    
    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }
    
    public String getRoomType() {
        return roomType;
    }
    
    public double getPricePerNight() {
        return pricePerNight;
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * Abstract method to get room details - Polymorphism
     */
    public abstract String getRoomDetails();
    
    /**
     * Get discount rate for this room type
     */
    public abstract double getDiscount();
    
    @Override
    public String toString() {
        return roomType + " (ID: " + roomId + ") - $" + pricePerNight + "/night - " +
               (available ? "Available" : "Booked");
    }
}
