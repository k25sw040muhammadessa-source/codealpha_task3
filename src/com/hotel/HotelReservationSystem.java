package com.hotel;

import com.hotel.manager.HotelManager;
import com.hotel.ui.HomeFrame;

import javax.swing.*;

/**
 * HotelReservationSystem - Main Application Entry Point
 * 
 * This is a comprehensive Hotel Reservation System desktop application built with Swing/AWT.
 * 
 * FEATURES:
 * - Search and book hotel rooms by category
 * - Multiple room types: Standard, Deluxe, Suite
 * - Customer management and reservation tracking
 * - Payment processing simulation
 * - Cancel existing reservations
 * - Room availability management
 * - Data persistence (File I/O with serialization)
 * 
 * OOP PRINCIPLES DEMONSTRATED:
 * - Encapsulation: Data hiding and controlled access
 * - Inheritance: Room hierarchy (StandardRoom, DeluxeRoom, Suite extending Room)
 * - Polymorphism: Abstract method getRoomDetails() overridden in each subclass
 * - Abstraction: Abstract Room class, Manager pattern
 * 
 * ARCHITECTURE:
 * - Model: Room, Reservation, Customer, Payment classes
 * - Manager: HotelManager orchestrates business logic
 * - UI: BaseFrame, HomeFrame, SearchBookingFrame, BookingsFrame, CancelReservationFrame
 * - Utilities: UIConstants for styling, SessionManager for session handling
 * 
 * @author Java Developer
 * @version 1.0
 */
public class HotelReservationSystem {
    
    /**
     * Main method - Application entry point
     */
    public static void main(String[] args) {
        // Run on Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> {
            try {
                // Set look and feel to system default
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Initialize hotel manager
            HotelManager hotelManager = new HotelManager();
            
            // Display home frame
            new HomeFrame(hotelManager);
        });
    }
}
