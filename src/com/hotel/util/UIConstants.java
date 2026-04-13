package com.hotel.util;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * UIConstants with Modern Material Design Styling
 * Provides comprehensive styling utilities for a professional, modern look
 */
public class UIConstants {
    // ===== MODERN COLOR PALETTE =====
    // Primary Colors
    public static final Color PRIMARY_COLOR = new Color(25, 118, 210);        // Deep Blue
    public static final Color PRIMARY_LIGHT = new Color(66, 165, 245);        // Light Blue
    public static final Color PRIMARY_DARK = new Color(13, 71, 161);          // Dark Blue
    
    // Secondary Colors
    public static final Color SECONDARY_COLOR = new Color(56, 142, 60);       // Forest Green
    public static final Color SECONDARY_LIGHT = new Color(129, 199, 132);     // Light Green
    public static final Color SECONDARY_DARK = new Color(27, 94, 32);         // Dark Green
    
    // Accent & Action Colors
    public static final Color ACCENT_COLOR = new Color(255, 111, 0);          // Deep Orange
    public static final Color ACCENT_LIGHT = new Color(255, 167, 38);         // Light Orange
    public static final Color DANGER_COLOR = new Color(211, 47, 47);          // Deep Red
    public static final Color WARNING_COLOR = new Color(245, 127, 23);        // Deep Orange Red
    public static final Color SUCCESS_COLOR = new Color(46, 125, 50);         // Success Green
    
    // Neutral Colors
    public static final Color BACKGROUND_COLOR = new Color(250, 250, 250);    // Soft White
    public static final Color SURFACE_COLOR = new Color(255, 255, 255);       // Pure White
    public static final Color CARD_COLOR = new Color(255, 255, 255);          // Card White
    
    // Text Colors
    public static final Color TEXT_PRIMARY = new Color(33, 33, 33);           // Primary Text
    public static final Color TEXT_SECONDARY = new Color(117, 117, 117);      // Secondary Text
    public static final Color TEXT_HINT = new Color(189, 189, 189);           // Hint Text
    
    // Border & Shadow
    public static final Color BORDER_COLOR = new Color(224, 224, 224);        // Light Border
    public static final Color DIVIDER_COLOR = new Color(189, 189, 189);       // Divider
    public static final Color SHADOW_COLOR = new Color(0, 0, 0);              // Shadow
    
    // Disabled Colors
    public static final Color DISABLED_COLOR = new Color(230, 230, 230);      // Disabled BG
    public static final Color DISABLED_TEXT = new Color(158, 158, 158);       // Disabled Text
    
    // ===== MODERN TYPOGRAPHY =====
    // Display Fonts
    public static final Font DISPLAY_FONT = new Font("Segoe UI", Font.BOLD, 32);
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font HEADING_1_FONT = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font HEADING_2_FONT = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font HEADING_FONT = new Font("Segoe UI", Font.BOLD, 18);
    
    // Body Fonts
    public static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font TINY_FONT = new Font("Segoe UI", Font.PLAIN, 11);
    
    // ===== SPACING SYSTEM (8px base) =====
    public static final int SPACING_XS = 4;
    public static final int SPACING_SM = 8;
    public static final int SPACING_MD = 12;
    public static final int SPACING_LG = 16;
    public static final int SPACING_XL = 24;
    public static final int SPACING_XXL = 32;
    
    // Legacy padding names (for compatibility)
    public static final int PADDING_SMALL = SPACING_SM;
    public static final int PADDING_MEDIUM = SPACING_LG;
    public static final int PADDING_LARGE = SPACING_XL;
    
    // ===== DIMENSION SYSTEM =====
    public static final int BORDER_RADIUS = 8;
    public static final int BORDER_RADIUS_SMALL = 4;
    public static final int BORDER_RADIUS_LARGE = 12;
    public static final int ELEVATION_SMALL = 2;
    public static final int ELEVATION_MEDIUM = 4;
    public static final int ELEVATION_LARGE = 8;
    
    // Window Size
    public static final int WINDOW_WIDTH = 1100;
    public static final int WINDOW_HEIGHT = 750;
    
    public static final int BUTTON_HEIGHT = 40;
    public static final int BUTTON_WIDTH_SMALL = 100;
    public static final int BUTTON_WIDTH_MEDIUM = 150;
    
    // ===== MODERN BUTTON STYLING =====
    /**
     * Create a modern styled button with hover effects
     */
    public static JButton createStyledButton(String text, Color bgColor, ActionListener listener) {
        ModernButton button = new ModernButton(text, bgColor);
        if (listener != null) {
            button.addActionListener(listener);
        }
        return button;
    }
    
    /**
     * Create a large action button (primary actions)
     */
    public static JButton createPrimaryButton(String text, ActionListener listener) {
        return createStyledButton(text, PRIMARY_COLOR, listener);
    }
    
    /**
     * Create a secondary action button
     */
    public static JButton createSecondaryButton(String text, ActionListener listener) {
        return createStyledButton(text, SECONDARY_COLOR, listener);
    }
    
    /**
     * Create a danger action button (destructive actions)
     */
    public static JButton createDangerButton(String text, ActionListener listener) {
        return createStyledButton(text, DANGER_COLOR, listener);
    }
    
    // ===== MODERN LABEL STYLING =====
    /**
     * Create a styled label
     */
    public static JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(TEXT_PRIMARY);
        return label;
    }
    
    /**
     * Create a title label
     */
    public static JLabel createTitleLabel(String text) {
        return createStyledLabel(text, TITLE_FONT);
    }
    
    /**
     * Create a heading label
     */
    public static JLabel createHeadingLabel(String text) {
        return createStyledLabel(text, HEADING_FONT);
    }
    
    // ===== MODERN TEXT FIELD STYLING =====
    /**
     * Create a modern styled text field
     */
    public static JTextField createStyledTextField(int columns) {
        ModernTextField textField = new ModernTextField(columns);
        textField.setFont(LABEL_FONT);
        return textField;
    }
    
    /**
     * Create a modern combo box
     */
    public static <T> JComboBox<T> createStyledComboBox(T[] items) {
        ModernComboBox<T> comboBox = new ModernComboBox<>(items);
        comboBox.setFont(LABEL_FONT);
        return comboBox;
    }
    
    // ===== MODERN PANEL STYLING =====
    /**
     * Create a modern card panel with shadow
     */
    public static JPanel createCardPanel(Color bgColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Shadow effect
                g2.setColor(new Color(0, 0, 0, 15));
                g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, BORDER_RADIUS, BORDER_RADIUS);
                
                // Card background
                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, BORDER_RADIUS, BORDER_RADIUS);
                
                // Border
                g2.setColor(BORDER_COLOR);
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawRoundRect(0, 0, getWidth() - 2, getHeight() - 2, BORDER_RADIUS, BORDER_RADIUS);
                
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setBackground(bgColor);
        return card;
    }
    
    /**
     * Create a modern panel with elevation
     */
    public static JPanel createElevatedPanel(Color bgColor) {
        return createCardPanel(bgColor);
    }
    
    /**
     * Create a separator/divider
     */
    public static JSeparator createModernSeparator() {
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(DIVIDER_COLOR);
        separator.setBackground(DIVIDER_COLOR);
        return separator;
    }
    
    // ===== MODERN COMPONENT FACTORY =====
    /**
     * Create modern header panel with gradient-like effect
     */
    public static JPanel createHeaderPanel(String title, Color bgColor) {
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(bgColor);
                g2.fillRect(0, 0, getWidth(), getHeight());
                
                super.paintComponent(g);
            }
        };
        header.setLayout(new BorderLayout(SPACING_LG, 0));
        header.setBackground(bgColor);
        header.setBorder(BorderFactory.createEmptyBorder(SPACING_LG, SPACING_LG, SPACING_LG, SPACING_LG));
        header.setPreferredSize(new Dimension(0, 70));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(HEADING_FONT);
        titleLabel.setForeground(Color.WHITE);
        header.add(titleLabel, BorderLayout.WEST);
        
        return header;
    }
    
    /**
     * Create a modern info panel
     */
    public static JPanel createInfoPanel(String title, String value) {
        JPanel infoPanel = createCardPanel(SURFACE_COLOR);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(SPACING_LG, SPACING_LG, SPACING_LG, SPACING_LG));
        
        JLabel titleLabel = createStyledLabel(title, SMALL_FONT);
        titleLabel.setForeground(TEXT_SECONDARY);
        
        JLabel valueLabel = createStyledLabel(value, HEADING_2_FONT);
        valueLabel.setForeground(PRIMARY_COLOR);
        
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(SPACING_SM));
        infoPanel.add(valueLabel);
        
        return infoPanel;
    }
    
    // ===== ICON GENERATION =====
    /**
     * Create a home icon
     */
    public static ImageIcon createHomeIcon(int size) {
        return createGeometricIcon(size, (g2, s) -> {
            // House shape
            g2.setStroke(new BasicStroke(2.0f));
            g2.drawLine(s/4, s/2, s/2, s/4);
            g2.drawLine(s/2, s/4, 3*s/4, s/2);
            g2.drawRect(s/4, s/2, s/2, s/4);
            g2.drawRect(s/2-s/8, 2*s/3, s/4, s/6);
        }, Color.WHITE);
    }
    
    /**
     * Create a search icon
     */
    public static ImageIcon createSearchIcon(int size) {
        return createGeometricIcon(size, (g2, s) -> {
            g2.setStroke(new BasicStroke(2.0f));
            g2.drawOval(s/4, s/4, s/2, s/2);
            g2.drawLine(2*s/3, 2*s/3, 3*s/4, 3*s/4);
        }, Color.WHITE);
    }
    
    /**
     * Create a bookings/list icon
     */
    public static ImageIcon createBookingsIcon(int size) {
        return createGeometricIcon(size, (g2, s) -> {
            g2.setStroke(new BasicStroke(1.5f));
            for (int i = 0; i < 3; i++) {
                int y = s/4 + i * s/4;
                g2.drawLine(s/4, y, s/2, y);
                g2.drawLine(s/4, y+3, s/2, y+3);
            }
            g2.drawRect(s/4, s/4, s/2, 3*s/8);
        }, Color.WHITE);
    }
    
    /**
     * Create a cancel/ban icon
     */
    public static ImageIcon createCancelIcon(int size) {
        return createGeometricIcon(size, (g2, s) -> {
            g2.setStroke(new BasicStroke(2.0f));
            g2.drawOval(s/4, s/4, s/2, s/2);
            g2.drawLine(s/3, s/3, 2*s/3, 2*s/3);
        }, Color.WHITE);
    }
    
    /**
     * Create a back/arrow icon
     */
    public static ImageIcon createBackIcon(int size) {
        return createGeometricIcon(size, (g2, s) -> {
            g2.setStroke(new BasicStroke(2.0f));
            g2.drawLine(3*s/4, s/4, s/4, s/2);
            g2.drawLine(3*s/4, 3*s/4, s/4, s/2);
        }, Color.WHITE);
    }
    
    /**
     * Create a hotel/building icon
     */
    public static ImageIcon createHotelIcon(int size) {
        return createGeometricIcon(size, (g2, s) -> {
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRect(s/4, s/4, s/2, s/2);
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    g2.drawRect(s/4 + i*s/4, s/4 + j*s/5, s/6, s/6);
                }
            }
        }, Color.WHITE);
    }
    
    /**
     * Create a refresh/reload icon
     */
    public static ImageIcon createRefreshIcon(int size) {
        return createGeometricIcon(size, (g2, s) -> {
            g2.setStroke(new BasicStroke(2.0f));
            // Curved arrow
            g2.drawArc(s/3, s/3, s/3, s/3, 0, 270);
            g2.drawLine(2*s/3, s/3, 3*s/4, s/4);
            g2.drawLine(3*s/4, s/4, 2*s/3+3, s/5);
        }, Color.WHITE);
    }
    
    /**
     * Create an exit/logout icon
     */
    public static ImageIcon createExitIcon(int size) {
        return createGeometricIcon(size, (g2, s) -> {
            g2.setStroke(new BasicStroke(2.0f));
            // Door
            g2.drawRect(s/4, s/4, s/2, s/2);
            // Door handle
            g2.fillOval(3*s/5, s/2-2, 4, 4);
            // Arrow pointing out
            g2.drawLine(s/4-5, s/2, s/4-15, s/2);
            g2.drawLine(s/4-15, s/2, s/4-12, s/2-4);
            g2.drawLine(s/4-15, s/2, s/4-12, s/2+4);
        }, Color.WHITE);
    }
    
    /**
     * Generic geometric icon creator
     */
    private static ImageIcon createGeometricIcon(int size, IconRenderer renderer, Color color) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        renderer.render(g2, size);
        g2.dispose();
        return new ImageIcon(image);
    }
    
    /**
     * Functional interface for icon rendering
     */
    @FunctionalInterface
    private interface IconRenderer {
        void render(Graphics2D g2, int size);
    }
}

// ===== CUSTOM COMPONENT: Modern Button =====
class ModernButton extends JButton {
    private Color baseColor;
    private Color hoverColor;
    private Color pressColor;
    private boolean isHovered = false;
    private boolean isPressed = false;
    
    public ModernButton(String text, Color baseColor) {
        super(text);
        this.baseColor = baseColor;
        this.hoverColor = brighten(baseColor, 20);
        this.pressColor = darken(baseColor, 20);
        
        setupStyle();
        setupListeners();
    }
    
    private void setupStyle() {
        setFont(UIConstants.BUTTON_FONT);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(UIConstants.BUTTON_WIDTH_MEDIUM, UIConstants.BUTTON_HEIGHT));
    }
    
    private void setupListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                isPressed = false;
                repaint();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Choose color based on state
        Color drawColor = baseColor;
        if (isPressed) {
            drawColor = pressColor;
        } else if (isHovered) {
            drawColor = hoverColor;
        }
        
        // Draw shadow
        g2.setColor(new Color(0, 0, 0, 20));
        g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, UIConstants.BORDER_RADIUS, UIConstants.BORDER_RADIUS);
        
        // Draw button
        g2.setColor(drawColor);
        g2.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, UIConstants.BORDER_RADIUS, UIConstants.BORDER_RADIUS);
        
        // Draw icon if available
        Icon icon = getIcon();
        int iconX = 10;
        int iconY = (getHeight() - 24) / 2;
        int textStartX = 40;
        
        if (icon != null) {
            icon.paintIcon(this, g2, iconX, iconY);
        }
        
        // Draw text
        g2.setColor(Color.WHITE);
        g2.setFont(UIConstants.BUTTON_FONT);
        FontMetrics fm = g2.getFontMetrics();
        int textY = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        g2.drawString(getText(), textStartX, textY);
    }
    
    private Color brighten(Color c, int amount) {
        return new Color(
            Math.min(c.getRed() + amount, 255),
            Math.min(c.getGreen() + amount, 255),
            Math.min(c.getBlue() + amount, 255)
        );
    }
    
    private Color darken(Color c, int amount) {
        return new Color(
            Math.max(c.getRed() - amount, 0),
            Math.max(c.getGreen() - amount, 0),
            Math.max(c.getBlue() - amount, 0)
        );
    }
}

// ===== CUSTOM COMPONENT: Modern Text Field =====
class ModernTextField extends JTextField {
    public ModernTextField(int columns) {
        super(columns);
        setupStyle();
    }
    
    private void setupStyle() {
        setFont(UIConstants.LABEL_FONT);
        setMargin(new Insets(UIConstants.SPACING_MD, UIConstants.SPACING_MD, UIConstants.SPACING_MD, UIConstants.SPACING_MD));
        setBackground(UIConstants.SURFACE_COLOR);
        setForeground(UIConstants.TEXT_PRIMARY);
        setCaretColor(UIConstants.PRIMARY_COLOR);
        setBorder(createModernBorder());
    }
    
    private Border createModernBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(UIConstants.SPACING_XS, UIConstants.SPACING_SM, UIConstants.SPACING_XS, UIConstants.SPACING_SM)
        );
    }
}

// ===== CUSTOM COMPONENT: Modern Combo Box =====
class ModernComboBox<T> extends JComboBox<T> {
    public ModernComboBox(T[] items) {
        super(items);
        setupStyle();
    }
    
    private void setupStyle() {
        setFont(UIConstants.LABEL_FONT);
        setForeground(UIConstants.TEXT_PRIMARY);
        setBackground(UIConstants.SURFACE_COLOR);
        setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1));
        setPreferredSize(new Dimension(150, UIConstants.BUTTON_HEIGHT));
    }
}
