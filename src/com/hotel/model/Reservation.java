package com.hotel.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Reservation class representing a hotel booking.
 * Demonstrates encapsulation and business logic.
 */
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int reservationId;
    private Customer customer;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalPrice;
    private String status; // CONFIRMED, CANCELLED
    private LocalDate reservationDate;
    
    /**
     * Constructor for Reservation
     */
    public Reservation(int reservationId, Customer customer, Room room,
                      LocalDate checkInDate, LocalDate checkOutDate) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = "CONFIRMED";
        this.reservationDate = LocalDate.now();
        calculateTotalPrice();
    }
    
    /**
     * Calculate total price based on number of nights and room price
     */
    private void calculateTotalPrice() {
        long nights = java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        if (nights <= 0) nights = 1;
        
        double basePrice = room.getPricePerNight() * nights;
        double discount = basePrice * room.getDiscount();
        this.totalPrice = basePrice - discount;
    }
    
    // Getters and Setters
    public int getReservationId() {
        return reservationId;
    }
    
    public void setReservationId(int reservationId) {
        if (reservationId > 0) {
            this.reservationId = reservationId;
        }
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Room getRoom() {
        return room;
    }
    
    public void setRoom(Room room) {
        this.room = room;
        calculateTotalPrice();
    }
    
    public LocalDate getCheckInDate() {
        return checkInDate;
    }
    
    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
        calculateTotalPrice();
    }
    
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
    
    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
        calculateTotalPrice();
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        if (status.equals("CONFIRMED") || status.equals("CANCELLED")) {
            this.status = status;
        }
    }
    
    public LocalDate getReservationDate() {
        return reservationDate;
    }
    
    public long getNumberOfNights() {
        return java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
    
    public String getReservationDetails() {
        return "Reservation ID: " + reservationId + "\n" +
               "Customer: " + customer.getFullName() + "\n" +
               "Room: " + room.getRoomType() + " (ID: " + room.getRoomId() + ")\n" +
               "Check-in: " + checkInDate + "\n" +
               "Check-out: " + checkOutDate + "\n" +
               "Nights: " + getNumberOfNights() + "\n" +
               "Total Price: $" + String.format("%.2f", totalPrice) + "\n" +
               "Status: " + status;
    }
    
    @Override
    public String toString() {
        return "Reservation{" +
                "ID=" + reservationId +
                ", Customer='" + customer.getFullName() + '\'' +
                ", Room=" + room.getRoomType() +
                ", Check-in=" + checkInDate +
                ", Check-out=" + checkOutDate +
                ", Price=$" + totalPrice +
                ", Status='" + status + '\'' +
                '}';
    }
}
