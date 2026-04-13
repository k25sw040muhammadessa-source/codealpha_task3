package com.hotel.model;

/**
 * DeluxeRoom class extending Room.
 * Represents a premium room with enhanced facilities.
 * Demonstrates inheritance and polymorphism.
 */
public class DeluxeRoom extends Room {
    private static final long serialVersionUID = 1L;
    
    public DeluxeRoom(int roomId) {
        super(roomId, "Deluxe Room", 150.0, 3, 
              "Premium room with upgraded amenities and better views");
    }
    
    @Override
    public String getRoomDetails() {
        return "Deluxe Room (ID: " + roomId + ")\n" +
               "Price: $" + pricePerNight + " per night\n" +
               "Capacity: " + capacity + " guests\n" +
               "Amenities: Queen bed, marble bathroom, AC, TV, WiFi, Mini bar, City view";
    }
    
    @Override
    public double getDiscount() {
        return 0.08; // 8% discount
    }
}
