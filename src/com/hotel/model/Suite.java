package com.hotel.model;

/**
 * Suite class extending Room.
 * Represents a luxury suite with premium facilities.
 * Demonstrates inheritance and polymorphism.
 */
public class Suite extends Room {
    private static final long serialVersionUID = 1L;
    
    public Suite(int roomId) {
        super(roomId, "Suite", 300.0, 4, 
              "Luxury suite with separate living area and premium amenities");
    }
    
    @Override
    public String getRoomDetails() {
        return "Suite (ID: " + roomId + ")\n" +
               "Price: $" + pricePerNight + " per night\n" +
               "Capacity: " + capacity + " guests\n" +
               "Amenities: Multiple beds, living area, luxury bathroom, Jacuzzi, " +
               "AC, 55\" TV, WiFi, Premium bar, Panoramic balcony";
    }
    
    @Override
    public double getDiscount() {
        return 0.10; // 10% discount
    }
}
