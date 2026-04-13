package com.hotel.ui;

import com.hotel.manager.HotelManager;
import com.hotel.util.UIConstants;

import javax.swing.*;
import java.awt.*;

/**
 * HomeFrame is the main entry point and dashboard of the application.
 * Displays hotel statistics and provides navigation to all features.
 */
public class HomeFrame extends BaseFrame {
    private JLabel statisticsLabel;
    private JButton searchButton;
    private JButton myBookingsButton;
    private JButton cancelButton;
    private JButton exitButton;
    
    /**
     * Constructor for HomeFrame
     */
    public HomeFrame(HotelManager hotelManager) {
        super("Home", hotelManager);
        setVisible(true);
    }
    
    @Override
    protected void initializeComponents() {
        // Setup navigation panel
        setupNavigationPanel();
        
        // Setup content panel
        contentPanel.setLayout(new BorderLayout());
        
        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        
        // Create main content panel
        JPanel mainContentPanel = createMainContentPanel();
        
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(mainContentPanel, BorderLayout.CENTER);
    }
    
    /**
     * Setup the navigation panel
     */
    private void setupNavigationPanel() {
        navigationPanel.removeAll();
        
        JLabel titleLabel = new JLabel("HOTEL RESERVATION SYSTEM");
        titleLabel.setFont(UIConstants.HEADING_FONT);
        titleLabel.setForeground(Color.WHITE);
        
        navigationPanel.add(titleLabel);
    }
    
    /**
     * Create header panel with welcome message
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UIConstants.PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel welcomeLabel = new JLabel("Welcome to Grand Hotel");
        welcomeLabel.setFont(UIConstants.TITLE_FONT);
        welcomeLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Book your perfect stay today!");
        subtitleLabel.setFont(UIConstants.LABEL_FONT);
        subtitleLabel.setForeground(new Color(200, 200, 200));
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        textPanel.setOpaque(false);
        textPanel.add(welcomeLabel);
        textPanel.add(subtitleLabel);
        
        headerPanel.add(textPanel, BorderLayout.WEST);
        
        return headerPanel;
    }
    
    /**
     * Create main content panel with options and statistics
     */
    private JPanel createMainContentPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        mainPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Left panel - Quick actions
        JPanel actionsPanel = createActionsPanel();
        
        // Right panel - Statistics
        JPanel statsPanel = createStatisticsPanel();
        
        mainPanel.add(actionsPanel);
        mainPanel.add(statsPanel);
        
        return mainPanel;
    }
    
    /**
     * Create actions panel with buttons
     */
    private JPanel createActionsPanel() {
        JPanel actionsPanel = new JPanel(new GridLayout(4, 1, 0, 15));
        actionsPanel.setBackground(Color.WHITE);
        actionsPanel.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1));
        actionsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        
        // Title
        JLabel titleLabel = UIConstants.createStyledLabel("Quick Actions", UIConstants.HEADING_FONT);
        
        // Search Room Button with icon
        searchButton = UIConstants.createStyledButton("Search & Book Rooms", 
                                                        UIConstants.PRIMARY_COLOR, e -> openSearchFrame());
        searchButton.setIcon(UIConstants.createSearchIcon(24));
        searchButton.setHorizontalAlignment(SwingConstants.LEFT);
        
        // My Bookings Button with icon
        myBookingsButton = UIConstants.createStyledButton("View My Bookings", 
                                                            UIConstants.SECONDARY_COLOR, e -> openBookingsFrame());
        myBookingsButton.setIcon(UIConstants.createBookingsIcon(24));
        myBookingsButton.setHorizontalAlignment(SwingConstants.LEFT);
        
        // Cancel Reservation Button with icon
        cancelButton = UIConstants.createStyledButton("Cancel Reservation", 
                                                        UIConstants.ACCENT_COLOR, e -> openCancelFrame());
        cancelButton.setIcon(UIConstants.createCancelIcon(24));
        cancelButton.setHorizontalAlignment(SwingConstants.LEFT);
        
        // Exit Button
        exitButton = UIConstants.createStyledButton("Exit Application", 
                                                    UIConstants.DANGER_COLOR, e -> System.exit(0));
        exitButton.setIcon(UIConstants.createExitIcon(24));
        exitButton.setHorizontalAlignment(SwingConstants.LEFT);
        
        actionsPanel.add(titleLabel);
        actionsPanel.add(searchButton);
        actionsPanel.add(myBookingsButton);
        actionsPanel.add(cancelButton);
        actionsPanel.add(exitButton);
        
        return actionsPanel;
    }
    
    /**
     * Create statistics panel
     */
    private JPanel createStatisticsPanel() {
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1));
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        
        // Title
        JLabel titleLabel = UIConstants.createStyledLabel("Hotel Statistics", UIConstants.HEADING_FONT);
        
        // Statistics display
        statisticsLabel = UIConstants.createStyledLabel("", UIConstants.LABEL_FONT);
        statisticsLabel.setVerticalAlignment(JLabel.TOP);
        updateStatistics();
        
        // Refresh button
        JButton refreshButton = UIConstants.createStyledButton("Refresh", UIConstants.PRIMARY_COLOR,
                                                                e -> updateStatistics());
        refreshButton.setIcon(UIConstants.createRefreshIcon(24));
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topPanel.setOpaque(false);
        topPanel.add(titleLabel);
        
        statsPanel.add(topPanel, BorderLayout.NORTH);
        statsPanel.add(new JScrollPane(statisticsLabel), BorderLayout.CENTER);
        statsPanel.add(refreshButton, BorderLayout.SOUTH);
        
        return statsPanel;
    }
    
    /**
     * Update statistics display
     */
    private void updateStatistics() {
        String stats = "<html>" + hotelManager.getStatistics().replace("\n", "<br>") + "</html>";
        statisticsLabel.setText(stats);
    }
    
    /**
     * Open search/booking frame
     */
    private void openSearchFrame() {
        new SearchBookingFrame(hotelManager);
        dispose();
    }
    
    /**
     * Open bookings view frame
     */
    private void openBookingsFrame() {
        new BookingsFrame(hotelManager);
        dispose();
    }
    
    /**
     * Open cancel reservation frame
     */
    private void openCancelFrame() {
        new CancelReservationFrame(hotelManager);
        dispose();
    }
}
