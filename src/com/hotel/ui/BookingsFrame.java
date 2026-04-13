package com.hotel.ui;

import com.hotel.manager.HotelManager;
import com.hotel.model.Reservation;
import com.hotel.util.UIConstants;

import javax.swing.*;
import java.awt.*;

/**
 * BookingsFrame displays all existing reservations and their details.
 */
public class BookingsFrame extends BaseFrame {
    private JTable bookingsTable;
    private JTextArea detailsArea;
    
    /**
     * Constructor for BookingsFrame
     */
    public BookingsFrame(HotelManager hotelManager) {
        super("My Bookings", hotelManager);
        setVisible(true);
    }
    
    @Override
    protected void initializeComponents() {
        // Setup navigation panel
        setupNavigationPanel();
        
        // Setup content panel
        contentPanel.setLayout(new BorderLayout());
        
        // Create main panel
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        mainPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Bookings table panel
        JPanel tablePanel = createTablePanel();
        
        // Details panel
        JPanel detailsPanel = createDetailsPanel();
        
        mainPanel.add(tablePanel);
        mainPanel.add(detailsPanel);
        
        contentPanel.add(mainPanel, BorderLayout.CENTER);
        
        // Load bookings
        loadBookings();
    }
    
    /**
     * Setup navigation panel
     */
    private void setupNavigationPanel() {
        navigationPanel.removeAll();
        
        JLabel titleLabel = new JLabel("View Bookings");
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
     * Create table panel
     */
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1));
        
        JLabel titleLabel = UIConstants.createStyledLabel("All Reservations", UIConstants.HEADING_FONT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        bookingsTable = new JTable();
        bookingsTable.setFont(UIConstants.LABEL_FONT);
        bookingsTable.setRowHeight(25);
        bookingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookingsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && bookingsTable.getSelectedRow() != -1) {
                updateDetails();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create details panel
     */
    private JPanel createDetailsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1));
        
        JLabel titleLabel = UIConstants.createStyledLabel("Booking Details", UIConstants.HEADING_FONT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        detailsArea = new JTextArea();
        detailsArea.setFont(UIConstants.LABEL_FONT);
        detailsArea.setEditable(false);
        detailsArea.setMargin(new Insets(10, 10, 10, 10));
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        
        JButton refreshButton = UIConstants.createStyledButton("Refresh", UIConstants.PRIMARY_COLOR,
                                                                e -> loadBookings());
        refreshButton.setIcon(UIConstants.createRefreshIcon(24));
        refreshButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Load all bookings into table
     */
    private void loadBookings() {
        java.util.List<Reservation> reservations = hotelManager.getAllReservations();
        
        String[] columns = {"Reservation ID", "Customer", "Room Type", "Check-in", "Status"};
        Object[][] data = new Object[reservations.size()][5];
        
        for (int i = 0; i < reservations.size(); i++) {
            Reservation res = reservations.get(i);
            data[i][0] = res.getReservationId();
            data[i][1] = res.getCustomer().getFullName();
            data[i][2] = res.getRoom().getRoomType();
            data[i][3] = res.getCheckInDate();
            data[i][4] = res.getStatus();
        }
        
        bookingsTable.setModel(new javax.swing.table.DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }
    
    /**
     * Update details display
     */
    private void updateDetails() {
        int selectedRow = bookingsTable.getSelectedRow();
        if (selectedRow != -1) {
            java.util.List<Reservation> reservations = hotelManager.getAllReservations();
            if (selectedRow < reservations.size()) {
                Reservation reservation = reservations.get(selectedRow);
                
                StringBuilder details = new StringBuilder();
                details.append(reservation.getReservationDetails());
                details.append("\n\n--- Payment Information ---\n");
                
                com.hotel.model.Payment payment = hotelManager.getPayment(reservation.getReservationId());
                if (payment != null) {
                    details.append("Payment ID: ").append(payment.getPaymentId()).append("\n");
                    details.append("Amount: $").append(String.format("%.2f", payment.getAmount())).append("\n");
                    details.append("Method: ").append(payment.getPaymentMethod()).append("\n");
                    details.append("Status: ").append(payment.getStatus()).append("\n");
                    details.append("Transaction ID: ").append(payment.getTransactionId()).append("\n");
                } else {
                    details.append("No payment recorded");
                }
                
                detailsArea.setText(details.toString());
            }
        }
    }
}
