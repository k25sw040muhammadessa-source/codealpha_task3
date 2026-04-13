package com.hotel.model;

/**
 * StandardRoom class extending Room.
 * Represents a basic hotel room with essential amenities.
 * Demonstrates inheritance and polymorphism.
 */
public class StandardRoom extends Room {
    private static final long serialVersionUID = 1L;
    
    public StandardRoom(int roomId) {
        super(roomId, "Standard Room", 80.0, 2, 
              "Basic room with bed, bathroom, and essential amenities");
    }
    
    @Override
    public String getRoomDetails() {
        return "Standard Room (ID: " + roomId + ")\n" +
               "Price: $" + pricePerNight + " per night\n" +
               "Capacity: " + capacity + " guests\n" +
               "Amenities: Single bed, bathroom, TV, WiFi, Air Conditioning";
    }
    
    @Override
    public double getDiscount() {
        return 0.05; // 5% discount
    }
}
