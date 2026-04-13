package com.hotel.ui;

import com.hotel.manager.HotelManager;
import com.hotel.model.*;
import com.hotel.util.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * SearchBookingFrame allows users to search for available rooms and make bookings.
 */
public class SearchBookingFrame extends BaseFrame {
    private JComboBox<String> roomTypeCombo;
    private JTable availableRoomsTable;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JSpinner checkInSpinner;
    private JSpinner checkOutSpinner;
    private JComboBox<String> paymentMethodCombo;
    private JLabel selectedRoomLabel;
    private Room selectedRoom;
    
    /**
     * Constructor for SearchBookingFrame
     */
    public SearchBookingFrame(HotelManager hotelManager) {
        super("Search & Book", hotelManager);
        setVisible(true);
    }
    
    @Override
    protected void initializeComponents() {
        // Setup navigation panel
        setupNavigationPanel();
        
        // Setup content panel
        contentPanel.setLayout(new BorderLayout());
        
        // Create main panel with tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(UIConstants.LABEL_FONT);
        tabbedPane.setBackground(UIConstants.BACKGROUND_COLOR);
        
        // Search tab
        tabbedPane.addTab("Search Rooms", createSearchPanel());
        
        // Customer info tab
        tabbedPane.addTab("Customer Info", createCustomerPanel());
        
        // Booking tab
        tabbedPane.addTab("Complete Booking", createBookingPanel());
        
        contentPanel.add(tabbedPane, BorderLayout.CENTER);
    }
    
    /**
     * Setup navigation panel
     */
    private void setupNavigationPanel() {
        navigationPanel.removeAll();
        
        JLabel titleLabel = new JLabel("Search & Book Rooms");
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
     * Create search panel
     */
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UIConstants.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Filter panel
        JPanel filterPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        
        // Room type filter
        filterPanel.add(UIConstants.createStyledLabel("Room Type:", UIConstants.LABEL_FONT));
        String[] roomTypes = hotelManager.getRoomTypes();
        roomTypeCombo = UIConstants.createStyledComboBox(roomTypes);
        roomTypeCombo.addActionListener(e -> searchRooms());
        filterPanel.add(roomTypeCombo);
        
        // Check-in date
        filterPanel.add(UIConstants.createStyledLabel("Check-in:", UIConstants.LABEL_FONT));
        checkInSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dEditor1 = new JSpinner.DateEditor(checkInSpinner, "yyyy-MM-dd");
        checkInSpinner.setEditor(dEditor1);
        checkInSpinner.setValue(java.sql.Date.valueOf(LocalDate.now().plusDays(1)));
        checkInSpinner.addChangeListener(e -> searchRooms());
        filterPanel.add(checkInSpinner);
        
        // Check-out date
        filterPanel.add(UIConstants.createStyledLabel("Check-out:", UIConstants.LABEL_FONT));
        checkOutSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dEditor2 = new JSpinner.DateEditor(checkOutSpinner, "yyyy-MM-dd");
        checkOutSpinner.setEditor(dEditor2);
        checkOutSpinner.setValue(java.sql.Date.valueOf(LocalDate.now().plusDays(2)));
        checkOutSpinner.addChangeListener(e -> searchRooms());
        filterPanel.add(checkOutSpinner);
        
        JButton searchButton = UIConstants.createStyledButton("Search", UIConstants.PRIMARY_COLOR, 
                                                                e -> searchRooms());
        searchButton.setIcon(UIConstants.createSearchIcon(24));
        filterPanel.add(searchButton);
        
        // Table for available rooms
        availableRoomsTable = new JTable();
        availableRoomsTable.setFont(UIConstants.LABEL_FONT);
        availableRoomsTable.setRowHeight(30);
        availableRoomsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        availableRoomsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && availableRoomsTable.getSelectedRow() != -1) {
                updateSelectedRoom();
            }
        });
        
        JScrollPane tableScroll = new JScrollPane(availableRoomsTable);
        tableScroll.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1));
        
        // Selected room panel
        JPanel selectedPanel = new JPanel(new BorderLayout());
        selectedPanel.setBackground(Color.WHITE);
        selectedPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.PRIMARY_COLOR, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        selectedRoomLabel = UIConstants.createStyledLabel("No room selected", UIConstants.LABEL_FONT);
        selectedRoomLabel.setVerticalAlignment(JLabel.TOP);
        selectedPanel.add(new JScrollPane(selectedRoomLabel), BorderLayout.CENTER);
        
        // Layout
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 0, 15));
        mainPanel.setOpaque(false);
        mainPanel.add(filterPanel);
        mainPanel.add(tableScroll);
        mainPanel.add(selectedPanel);
        
        panel.add(mainPanel, BorderLayout.CENTER);
        
        // Initialize search
        searchRooms();
        
        return panel;
    }
    
    /**
     * Search rooms based on filters
     */
    private void searchRooms() {
        String roomType = (String) roomTypeCombo.getSelectedItem();
        java.util.List<Room> availableRooms = hotelManager.searchAvailableRoomsByType(roomType);
        
        // Create table model
        String[] columns = {"Room ID", "Type", "Price/Night", "Capacity", "Status"};
        Object[][] data = new Object[availableRooms.size()][5];
        
        for (int i = 0; i < availableRooms.size(); i++) {
            Room room = availableRooms.get(i);
            data[i][0] = room.getRoomId();
            data[i][1] = room.getRoomType();
            data[i][2] = "$" + room.getPricePerNight();
            data[i][3] = room.getCapacity() + " guests";
            data[i][4] = room.isAvailable() ? "Available" : "Occupied";
        }
        
        availableRoomsTable.setModel(new javax.swing.table.DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }
    
    /**
     * Update selected room display
     */
    private void updateSelectedRoom() {
        int selectedRow = availableRoomsTable.getSelectedRow();
        if (selectedRow != -1) {
            String roomType = (String) roomTypeCombo.getSelectedItem();
            java.util.List<Room> rooms = hotelManager.searchAvailableRoomsByType(roomType);
            if (selectedRow < rooms.size()) {
                selectedRoom = rooms.get(selectedRow);
                String details = "<html>" + selectedRoom.getRoomDetails().replace("\n", "<br>") + "</html>";
                selectedRoomLabel.setText(details);
            }
        }
    }
    
    /**
     * Create customer information panel
     */
    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UIConstants.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 15));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        
        // First name
        formPanel.add(UIConstants.createStyledLabel("First Name:", UIConstants.LABEL_FONT));
        firstNameField = UIConstants.createStyledTextField(20);
        formPanel.add(firstNameField);
        
        // Last name
        formPanel.add(UIConstants.createStyledLabel("Last Name:", UIConstants.LABEL_FONT));
        lastNameField = UIConstants.createStyledTextField(20);
        formPanel.add(lastNameField);
        
        // Email
        formPanel.add(UIConstants.createStyledLabel("Email:", UIConstants.LABEL_FONT));
        emailField = UIConstants.createStyledTextField(20);
        formPanel.add(emailField);
        
        // Phone
        formPanel.add(UIConstants.createStyledLabel("Phone:", UIConstants.LABEL_FONT));
        phoneField = UIConstants.createStyledTextField(20);
        formPanel.add(phoneField);
        
        // Address
        formPanel.add(UIConstants.createStyledLabel("Address:", UIConstants.LABEL_FONT));
        addressField = UIConstants.createStyledTextField(20);
        formPanel.add(addressField);
        
        panel.add(formPanel, BorderLayout.NORTH);
        
        return panel;
    }
    
    /**
     * Create booking completion panel
     */
    private JPanel createBookingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UIConstants.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel bookingPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        bookingPanel.setBackground(Color.WHITE);
        bookingPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        
        // Payment method
        bookingPanel.add(UIConstants.createStyledLabel("Payment Method:", UIConstants.LABEL_FONT));
        String[] paymentMethods = {"Credit Card", "Debit Card", "Cash"};
        paymentMethodCombo = UIConstants.createStyledComboBox(paymentMethods);
        bookingPanel.add(paymentMethodCombo);
        
        // Summary
        JLabel summaryLabel = UIConstants.createStyledLabel("Booking Summary", UIConstants.HEADING_FONT);
        bookingPanel.add(summaryLabel);
        
        JLabel emptyLabel = new JLabel();
        bookingPanel.add(emptyLabel);
        
        // Book button
        JButton bookButton = UIConstants.createStyledButton("Confirm Booking", 
                                                            UIConstants.SECONDARY_COLOR, e -> confirmBooking());
        bookButton.setIcon(UIConstants.createBookingsIcon(24));
        bookingPanel.add(bookButton);
        
        // Cancel button
        JButton cancelButton = UIConstants.createStyledButton("Cancel", 
                                                                UIConstants.DANGER_COLOR, e -> goBack());
        cancelButton.setIcon(UIConstants.createCancelIcon(24));
        bookingPanel.add(cancelButton);
        
        panel.add(bookingPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Confirm and process booking
     */
    private void confirmBooking() {
        try {
            // Validate inputs
            if (firstNameField.getText().trim().isEmpty() || 
                lastNameField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty() ||
                phoneField.getText().trim().isEmpty()) {
                showErrorDialog("Validation Error", "Please fill in all customer information");
                return;
            }
            
            if (selectedRoom == null) {
                showErrorDialog("Selection Error", "Please select a room");
                return;
            }
            
            // Create customer
            Customer customer = hotelManager.registerCustomer(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    addressField.getText());
            
            // Get dates
            LocalDate checkIn = new java.sql.Date(((java.util.Date) checkInSpinner.getValue()).getTime()).toLocalDate();
            LocalDate checkOut = new java.sql.Date(((java.util.Date) checkOutSpinner.getValue()).getTime()).toLocalDate();
            
            // Book room
            Reservation reservation = hotelManager.bookRoom(customer, selectedRoom, checkIn, checkOut);
            
            // Process payment
            String paymentMethod = (String) paymentMethodCombo.getSelectedItem();
            Payment payment = hotelManager.processPayment(reservation.getReservationId(), paymentMethod);
            
            // Show confirmation
            String message = "Booking Confirmed!\n\n" +
                            reservation.getReservationDetails() + "\n\n" +
                            payment.getPaymentReceipt();
            showInfoDialog("Booking Confirmation", message);
            
            goBack();
        } catch (Exception e) {
            showErrorDialog("Booking Error", "Error: " + e.getMessage());
        }
    }
}
