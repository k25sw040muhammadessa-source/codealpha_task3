package com.hotel.ui;

import com.hotel.manager.HotelManager;
import com.hotel.model.Reservation;
import com.hotel.util.UIConstants;

import javax.swing.*;
import java.awt.*;

/**
 * CancelReservationFrame allows users to cancel existing reservations.
 */
public class CancelReservationFrame extends BaseFrame {
    private JTextField reservationIdField;
    private JTextArea reservationDetailsArea;
    private JButton cancelButton;
    private JButton searchButton;
    
    /**
     * Constructor for CancelReservationFrame
     */
    public CancelReservationFrame(HotelManager hotelManager) {
        super("Cancel Reservation", hotelManager);
        setVisible(true);
    }
    
    @Override
    protected void initializeComponents() {
        // Setup navigation panel
        setupNavigationPanel();
        
        // Setup content panel
        contentPanel.setLayout(new BorderLayout());
        
        // Create main panel
        JPanel mainPanel = createMainPanel();
        
        contentPanel.add(mainPanel, BorderLayout.CENTER);
    }
    
    /**
     * Setup navigation panel
     */
    private void setupNavigationPanel() {
        navigationPanel.removeAll();
        
        JLabel titleLabel = new JLabel("Cancel Reservation");
        titleLabel.setFont(UIConstants.HEADING_FONT);
        titleLabel.setForeground(Color.WHITE);
        
        JButton backButton = createNavButton("Back to Home", e -> goBack());
        backButton.setBackground(UIConstants.ACCENT_COLOR);
        backButton.setIcon(UIConstants.createBackIcon(24));
        
        navigationPanel.add(titleLabel);
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(backButton);
    }
    
    /**
     * Create main panel
     */
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 0, 15));
        mainPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Search panel
        JPanel searchPanel = createSearchPanel();
        
        // Details panel
        JPanel detailsPanel = createDetailsPanel();
        
        mainPanel.add(searchPanel);
        mainPanel.add(detailsPanel);
        
        return mainPanel;
    }
    
    /**
     * Create search panel
     */
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1));
        
        JLabel titleLabel = UIConstants.createStyledLabel("Find Reservation", UIConstants.HEADING_FONT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        inputPanel.setOpaque(false);
        
        inputPanel.add(UIConstants.createStyledLabel("Reservation ID:", UIConstants.LABEL_FONT));
        
        reservationIdField = UIConstants.createStyledTextField(10);
        inputPanel.add(reservationIdField);
        
        searchButton = UIConstants.createStyledButton("Search", UIConstants.PRIMARY_COLOR,
                                                        e -> searchReservation());
        searchButton.setIcon(UIConstants.createSearchIcon(24));
        inputPanel.add(searchButton);
        inputPanel.add(searchButton);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(inputPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create details panel
     */
    private JPanel createDetailsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1));
        
        JLabel titleLabel = UIConstants.createStyledLabel("Reservation Details", UIConstants.HEADING_FONT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        reservationDetailsArea = new JTextArea();
        reservationDetailsArea.setFont(UIConstants.LABEL_FONT);
        reservationDetailsArea.setEditable(false);
        reservationDetailsArea.setMargin(new Insets(10, 10, 10, 10));
        reservationDetailsArea.setLineWrap(true);
        reservationDetailsArea.setWrapStyleWord(true);
        reservationDetailsArea.setText("Enter a Reservation ID and click Search to view details");
        
        JScrollPane scrollPane = new JScrollPane(reservationDetailsArea);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setOpaque(false);
        
        cancelButton = UIConstants.createStyledButton("Confirm Cancellation", UIConstants.DANGER_COLOR,
                                                        e -> confirmCancellation());
        cancelButton.setIcon(UIConstants.createCancelIcon(24));
        cancelButton.setEnabled(false);
        
        JButton backButton = UIConstants.createStyledButton("Back", UIConstants.SECONDARY_COLOR,
                                                            e -> goBack());
        backButton.setIcon(UIConstants.createBackIcon(24));
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Search for reservation
     */
    private void searchReservation() {
        try {
            String idStr = reservationIdField.getText().trim();
            if (idStr.isEmpty()) {
                showErrorDialog("Input Error", "Please enter a Reservation ID");
                return;
            }
            
            int reservationId = Integer.parseInt(idStr);
            Reservation reservation = hotelManager.getReservation(reservationId);
            
            if (reservation == null) {
                showErrorDialog("Not Found", "Reservation with ID " + reservationId + " not found");
                reservationDetailsArea.setText("");
                cancelButton.setEnabled(false);
                return;
            }
            
            if (reservation.getStatus().equals("CANCELLED")) {
                showWarningDialog("Already Cancelled", "This reservation has already been cancelled");
                reservationDetailsArea.setText(reservation.getReservationDetails() + "\n\nStatus: CANCELLED");
                cancelButton.setEnabled(false);
                return;
            }
            
            // Display reservation details
            String details = reservation.getReservationDetails() + 
                            "\n\nCancelling this reservation will make the room available again.";
            reservationDetailsArea.setText(details);
            cancelButton.setEnabled(true);
            
        } catch (NumberFormatException e) {
            showErrorDialog("Invalid Input", "Reservation ID must be a number");
            cancelButton.setEnabled(false);
        }
    }
    
    /**
     * Confirm and process cancellation
     */
    private void confirmCancellation() {
        try {
            String idStr = reservationIdField.getText().trim();
            int reservationId = Integer.parseInt(idStr);
            
            if (!showConfirmDialog("Confirm Cancellation", 
                                "Are you sure you want to cancel this reservation?" +
                                "\nThis action cannot be undone.")) {
                return;
            }
            
            boolean success = hotelManager.cancelReservation(reservationId);
            
            if (success) {
                showInfoDialog("Cancellation Successful", 
                            "Reservation " + reservationId + " has been cancelled successfully.\n" +
                            "The room is now available for booking.");
                
                reservationDetailsArea.setText("");
                reservationIdField.setText("");
                cancelButton.setEnabled(false);
            } else {
                showErrorDialog("Cancellation Failed", "Could not cancel the reservation");
            }
        } catch (Exception e) {
            showErrorDialog("Error", "Error during cancellation: " + e.getMessage());
        }
    }
}
