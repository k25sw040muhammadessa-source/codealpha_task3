package com.hotel.ui;

import com.hotel.manager.HotelManager;
import com.hotel.util.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * BaseFrame provides a common structure for all screens in the application.
 * Features modern Material Design styling with professional appearance.
 */
public abstract class BaseFrame extends JFrame {
    protected HotelManager hotelManager;
    protected JPanel contentPanel;
    protected JPanel navigationPanel;
    protected JPanel headerPanel;
    
    /**
     * Constructor for BaseFrame with modern styling
     */
    public BaseFrame(String title, HotelManager hotelManager) {
        this.hotelManager = hotelManager;
        
        // Frame setup
        setTitle("Hotel Reservation System - " + title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(UIConstants.WINDOW_WIDTH, UIConstants.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Maximize window
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Set window icon
        setIconImage(UIConstants.createHotelIcon(64).getImage());
        
        // Set modern look and feel
        setBackground(UIConstants.BACKGROUND_COLOR);
        
        // Main container with modern styling
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        // Modern navigation panel with better spacing
        navigationPanel = new JPanel();
        navigationPanel.setLayout(new BorderLayout());
        navigationPanel.setBackground(UIConstants.PRIMARY_DARK);
        navigationPanel.setPreferredSize(new Dimension(0, 70));
        
        // Header panel for title
        headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, UIConstants.SPACING_LG, UIConstants.SPACING_MD));
        headerPanel.setBackground(UIConstants.PRIMARY_DARK);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(UIConstants.SPACING_LG, UIConstants.SPACING_LG, UIConstants.SPACING_LG, 0));
        
        navigationPanel.add(headerPanel, BorderLayout.WEST);
        
        // Content panel with padding
        contentPanel = new JPanel();
        contentPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        // Add components to main panel
        mainPanel.add(navigationPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Set main panel as content pane
        setContentPane(mainPanel);
        
        // Initialize components
        initializeComponents();
    }
    
    /**
     * Abstract method for subclasses to initialize their specific components
     */
    protected abstract void initializeComponents();
    
    /**
     * Create a modern navigation button with hover effects
     */
    protected JButton createNavButton(String text, ActionListener listener) {
        JButton button = UIConstants.createStyledButton(text, UIConstants.PRIMARY_COLOR, listener);
        button.setFont(UIConstants.HEADING_FONT);
        return button;
    }
    
    /**
     * Show an information dialog
     */
    protected void showInfoDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Show an error dialog
     */
    protected void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Show a warning dialog
     */
    protected void showWarningDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Show a confirmation dialog
     */
    protected boolean showConfirmDialog(String title, String message) {
        int result = JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
    
    /**
     * Go back to home frame (close this frame, open home)
     */
    protected void goBack() {
        this.dispose();
        new HomeFrame(hotelManager);
    }
}
