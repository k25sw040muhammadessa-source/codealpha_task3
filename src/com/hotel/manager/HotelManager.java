package com.hotel.manager;

import com.hotel.model.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * HotelManager class orchestrating all hotel operations.
 * Demonstrates the Manager/Controller pattern for business logic.
 */
public class HotelManager {
    private List<Room> rooms;
    private List<Reservation> reservations;
    private List<Payment> payments;
    private List<Customer> customers;
    
    private int nextRoomId = 100;
    private int nextCustomerId = 1000;
    private int nextReservationId = 5000;
    private int nextPaymentId = 10000;
    
    private static final String ROOMS_FILE = "data/rooms.dat";
    private static final String RESERVATIONS_FILE = "data/reservations.dat";
    private static final String PAYMENTS_FILE = "data/payments.dat";
    private static final String CUSTOMERS_FILE = "data/customers.dat";
    
    /**
     * Constructor initializing the hotel manager
     */
    public HotelManager() {
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.customers = new ArrayList<>();
        
        loadData();
        
        // Initialize with default rooms if none exist
        if (rooms.isEmpty()) {
            initializeDefaultRooms();
        }
    }
    
    /**
     * Initialize default rooms for the hotel
     */
    private void initializeDefaultRooms() {
        // Standard Rooms
        for (int i = 0; i < 5; i++) {
            rooms.add(new StandardRoom(nextRoomId++));
        }
        // Deluxe Rooms
        for (int i = 0; i < 4; i++) {
            rooms.add(new DeluxeRoom(nextRoomId++));
        }
        // Suites
        for (int i = 0; i < 3; i++) {
            rooms.add(new Suite(nextRoomId++));
        }
        saveData();
    }
    
    /**
     * Search available rooms by type
     */
    public List<Room> searchAvailableRoomsByType(String roomType) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable() && room.getRoomType().equalsIgnoreCase(roomType)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }
    
    /**
     * Get all available rooms (any type)
     */
    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }
    
    /**
     * Get all rooms
     */
    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms);
    }
    
    /**
     * Get unique room types
     */
    public String[] getRoomTypes() {
        Set<String> types = new HashSet<>();
        for (Room room : rooms) {
            types.add(room.getRoomType());
        }
        return types.toArray(new String[0]);
    }
    
    /**
     * Book a room for a customer
     */
    public synchronized Reservation bookRoom(Customer customer, Room room, 
                                             LocalDate checkIn, LocalDate checkOut) 
            throws Exception {
        
        if (!room.isAvailable()) {
            throw new Exception("Room is not available");
        }
        
        if (checkOut.isBefore(checkIn) || checkOut.equals(checkIn)) {
            throw new Exception("Invalid dates: Check-out must be after check-in");
        }
        
        // Create reservation
        Reservation reservation = new Reservation(nextReservationId++, customer, room, 
                                                  checkIn, checkOut);
        reservations.add(reservation);
        
        // Mark room as unavailable
        room.setAvailable(false);
        
        // Add customer if new
        if (!customers.contains(customer)) {
            customers.add(customer);
        }
        
        saveData();
        return reservation;
    }
    
    /**
     * Cancel a reservation
     */
    public synchronized boolean cancelReservation(int reservationId) {
        for (Reservation res : reservations) {
            if (res.getReservationId() == reservationId) {
                if (res.getStatus().equals("CANCELLED")) {
                    return false; // Already cancelled
                }
                
                res.setStatus("CANCELLED");
                res.getRoom().setAvailable(true);
                saveData();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get reservation by ID
     */
    public Reservation getReservation(int reservationId) {
        for (Reservation res : reservations) {
            if (res.getReservationId() == reservationId) {
                return res;
            }
        }
        return null;
    }
    
    /**
     * Get all reservations
     */
    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }
    
    /**
     * Get active reservations (not cancelled)
     */
    public List<Reservation> getActiveReservations() {
        List<Reservation> active = new ArrayList<>();
        for (Reservation res : reservations) {
            if (res.getStatus().equals("CONFIRMED")) {
                active.add(res);
            }
        }
        return active;
    }
    
    /**
     * Process payment for a reservation
     */
    public synchronized Payment processPayment(int reservationId, String paymentMethod) 
            throws Exception {
        
        Reservation reservation = getReservation(reservationId);
        if (reservation == null) {
            throw new Exception("Reservation not found");
        }
        
        Payment payment = new Payment(nextPaymentId++, reservationId, 
                                     reservation.getTotalPrice(), paymentMethod);
        
        if (payment.processPayment()) {
            payments.add(payment);
            saveData();
            return payment;
        } else {
            throw new Exception("Payment processing failed");
        }
    }
    
    /**
     * Get payment for reservation
     */
    public Payment getPayment(int reservationId) {
        for (Payment pay : payments) {
            if (pay.getReservationId() == reservationId) {
                return pay;
            }
        }
        return null;
    }
    
    /**
     * Get all payments
     */
    public List<Payment> getAllPayments() {
        return new ArrayList<>(payments);
    }
    
    /**
     * Register a new customer
     */
    public Customer registerCustomer(String firstName, String lastName, 
                                     String email, String phone, String address) {
        Customer customer = new Customer(nextCustomerId++, firstName, lastName, 
                                        email, phone, address);
        customers.add(customer);
        saveData();
        return customer;
    }
    
    /**
     * Get customer by ID
     */
    public Customer getCustomer(int customerId) {
        for (Customer cust : customers) {
            if (cust.getCustomerId() == customerId) {
                return cust;
            }
        }
        return null;
    }
    
    /**
     * Get all customers
     */
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }
    
    /**
     * Save all data to files
     */
    private synchronized void saveData() {
        try {
            // Save rooms
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(ROOMS_FILE))) {
                oos.writeObject(rooms);
            }
            
            // Save reservations
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(RESERVATIONS_FILE))) {
                oos.writeObject(reservations);
            }
            
            // Save payments
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(PAYMENTS_FILE))) {
                oos.writeObject(payments);
            }
            
            // Save customers
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(CUSTOMERS_FILE))) {
                oos.writeObject(customers);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Load all data from files
     */
    @SuppressWarnings("unchecked")
    private synchronized void loadData() {
        try {
            File roomsFile = new File(ROOMS_FILE);
            if (roomsFile.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream(ROOMS_FILE))) {
                    rooms = (List<Room>) ois.readObject();
                }
            }
            
            File reservationsFile = new File(RESERVATIONS_FILE);
            if (reservationsFile.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream(RESERVATIONS_FILE))) {
                    reservations = (List<Reservation>) ois.readObject();
                }
            }
            
            File paymentsFile = new File(PAYMENTS_FILE);
            if (paymentsFile.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream(PAYMENTS_FILE))) {
                    payments = (List<Payment>) ois.readObject();
                }
            }
            
            File customersFile = new File(CUSTOMERS_FILE);
            if (customersFile.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream(CUSTOMERS_FILE))) {
                    customers = (List<Customer>) ois.readObject();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Get hotel statistics
     */
    public String getStatistics() {
        int totalRooms = rooms.size();
        int availableRooms = getAvailableRooms().size();
        int totalReservations = reservations.size();
        int activeReservations = getActiveReservations().size();
        
        return "Total Rooms: " + totalRooms + "\n" +
               "Available: " + availableRooms + "\n" +
               "Occupied: " + (totalRooms - availableRooms) + "\n" +
               "Total Reservations: " + totalReservations + "\n" +
               "Active Reservations: " + activeReservations + "\n" +
               "Total Revenue: $" + String.format("%.2f", calculateTotalRevenue());
    }
    
    /**
     * Calculate total revenue from completed payments
     */
    private double calculateTotalRevenue() {
        double total = 0;
        for (Payment payment : payments) {
            if (payment.getStatus().equals("COMPLETED")) {
                total += payment.getAmount();
            }
        }
        return total;
    }
}
