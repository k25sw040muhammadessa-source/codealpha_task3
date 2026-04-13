package com.hotel.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Payment class handling payment simulation and record keeping.
 * Demonstrates abstraction of payment processing.
 */
public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int paymentId;
    private int reservationId;
    private double amount;
    private String paymentMethod; // CREDIT_CARD, DEBIT_CARD, CASH
    private String status; // PENDING, COMPLETED, FAILED
    private LocalDateTime paymentDateTime;
    private String transactionId;
    
    /**
     * Constructor for Payment
     */
    public Payment(int paymentId, int reservationId, double amount, String paymentMethod) {
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = "PENDING";
        this.paymentDateTime = LocalDateTime.now();
        this.transactionId = generateTransactionId();
    }
    
    /**
     * Simulate payment processing
     */
    public boolean processPayment() {
        try {
            // Simulate payment delay
            Thread.sleep(500);
            
            // Simulate 99% success rate
            if (Math.random() > 0.01) {
                this.status = "COMPLETED";
                this.paymentDateTime = LocalDateTime.now();
                return true;
            } else {
                this.status = "FAILED";
                return false;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            this.status = "FAILED";
            return false;
        }
    }
    
    /**
     * Generate a unique transaction ID
     */
    private String generateTransactionId() {
        return "TRX" + System.currentTimeMillis() + "_" + (int)(Math.random() * 10000);
    }
    
    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }
    
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
    
    public int getReservationId() {
        return reservationId;
    }
    
    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getPaymentDateTime() {
        return paymentDateTime;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public String getPaymentReceipt() {
        return "======== PAYMENT RECEIPT ========\n" +
               "Transaction ID: " + transactionId + "\n" +
               "Payment ID: " + paymentId + "\n" +
               "Reservation ID: " + reservationId + "\n" +
               "Amount: $" + String.format("%.2f", amount) + "\n" +
               "Method: " + paymentMethod + "\n" +
               "Status: " + status + "\n" +
               "Date & Time: " + paymentDateTime + "\n" +
               "==================================";
    }
    
    @Override
    public String toString() {
        return "Payment{" +
                "ID=" + paymentId +
                ", Amount=$" + amount +
                ", Method='" + paymentMethod + '\'' +
                ", Status='" + status + '\'' +
                ", TransactionID='" + transactionId + '\'' +
                '}';
    }
}
