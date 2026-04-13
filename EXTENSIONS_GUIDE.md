# Hotel Reservation System - Extension & Customization Guide

## 🎯 This Guide Explains

How to extend and customize the Hotel Reservation System for additional features, database integration, and deployment scenarios.

---

## 🔧 Part 1: Adding New Room Types

### Step 1: Create New Room Class

Create a new file `src/com/hotel/model/PremiumSuite.java`:

```java
package com.hotel.model;

/**
 * PremiumSuite extends Suite for ultra-luxury accommodations.
 */
public class PremiumSuite extends Room {
    private static final long serialVersionUID = 1L;
    
    public PremiumSuite(int roomId) {
        super(roomId, "Premium Suite", 500.0, 6, 
              "Ultra-luxury suite with concierge service");
    }
    
    @Override
    public String getRoomDetails() {
        return "Premium Suite (ID: " + roomId + ")\n" +
               "Price: $" + pricePerNight + " per night\n" +
               "Capacity: " + capacity + " guests\n" +
               "Amenities: Full service, private elevator, personal chef available";
    }
    
    @Override
    public double getDiscount() {
        return 0.15; // 15% discount
    }
}
```

### Step 2: Update HotelManager

In `HotelManager.java`, update `initializeDefaultRooms()`:

```java
// Premium Suites
for (int i = 0; i < 3; i++) {
    rooms.add(new PremiumSuite(400 + i));
}
```

### Step 3: Recompile

```bash
javac -d bin src/com/hotel/model/PremiumSuite.java
javac -d bin src/com/hotel/manager/HotelManager.java
```

---

## 💾 Part 2: Migrate to Database Backend

### Option A: SQLite Integration

#### Step 1: Add SQLite Dependency

Download `sqlite-jdbc.jar` and add to classpath.

#### Step 2: Create DatabaseManager

Create `src/com/hotel/manager/DatabaseManager.java`:

```java
package com.hotel.manager;

import java.sql.*;
import com.hotel.model.*;
import java.util.*;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:data/hotel.db";
    private Connection connection;
    
    public DatabaseManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
            createTables();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void createTables() {
        String roomsSQL = "CREATE TABLE IF NOT EXISTS rooms (" +
            "room_id INTEGER PRIMARY KEY, " +
            "room_type TEXT, " +
            "price_per_night REAL, " +
            "capacity INTEGER, " +
            "available BOOLEAN" +
            ")";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(roomsSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void saveRoom(Room room) {
        String sql = "INSERT OR REPLACE INTO rooms VALUES (?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, room.getRoomId());
            pstmt.setString(2, room.getRoomType());
            pstmt.setDouble(3, room.getPricePerNight());
            pstmt.setInt(4, room.getCapacity());
            pstmt.setBoolean(5, room.isAvailable());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Similar methods for other entities...
}
```

#### Step 3: Modify HotelManager

Replace serialization with database calls:

```java
public class HotelManager {
    private DatabaseManager dbManager;
    
    public HotelManager() {
        this.dbManager = new DatabaseManager();
        loadDataFromDatabase();  // Instead of loadData()
    }
    
    private void loadDataFromDatabase() {
        // Query database instead of deserializing files
    }
    
    private void saveData() {
        // Save to database instead of serializing
        for (Room room : rooms) {
            dbManager.saveRoom(room);
        }
    }
}
```

---

## 📧 Part 3: Add Email Notification System

### Step 1: Setup Email Service

Create `src/com/hotel/service/EmailService.java`:

```java
package com.hotel.service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailService {
    private static final String FROM = "noreply@hotelreservation.com";
    
    public static void sendBookingConfirmation(String email, int bookingId, double totalPrice) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("your_email@gmail.com", "your_password");
                }
            });
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Booking Confirmation - ID: " + bookingId);
            message.setText("Your booking has been confirmed!\n\n" +
                          "Booking ID: " + bookingId + "\n" +
                          "Total Price: $" + totalPrice + "\n\n" +
                          "Thank you for choosing our hotel!");
            
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
```

### Step 2: Integrate into HotelManager

```java
public synchronized Reservation bookRoom(Customer customer, Room room, 
                                         LocalDate checkIn, LocalDate checkOut) {
    // ... existing code ...
    
    // Send confirmation email
    EmailService.sendBookingConfirmation(customer.getEmail(), 
                                        reservation.getReservationId(), 
                                        reservation.getTotalPrice());
    
    return reservation;
}
```

---

## 🎨 Part 4: Customize UI Theme

### Change Color Scheme

Edit `src/com/hotel/util/UIConstants.java`:

```java
// Original - Blue theme
public static final Color PRIMARY_COLOR = new Color(33, 150, 243);        // Blue
public static final Color SECONDARY_COLOR = new Color(76, 175, 80);       // Green
public static final Color ACCENT_COLOR = new Color(255, 152, 0);          // Orange

// Dark theme
public static final Color PRIMARY_COLOR = new Color(51, 51, 51);          // Dark Gray
public static final Color SECONDARY_COLOR = new Color(102, 204, 102);     // Light Green
public static final Color ACCENT_COLOR = new Color(255, 204, 0);          // Gold
public static final Color BACKGROUND_COLOR = new Color(30, 30, 30);       // Dark background
```

### Add Custom Fonts

```java
// In UIConstants.java
public static final Font CUSTOM_FONT = new Font("Trebuchet MS", Font.BOLD, 16);

// Use in UI frames
JLabel label = UIConstants.createStyledLabel("Custom Text", UIConstants.CUSTOM_FONT);
```

### Create Custom Components

```java
// In UIConstants.java
public static class RoundedButton extends JButton {
    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setOpaque(true);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.lightGray);
        } else {
            g.setColor(getBackground());
        }
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        super.paintComponent(g);
    }
}
```

---

## 📊 Part 5: Add Reporting & Analytics

### Create Report Generator

Create `src/com/hotel/util/ReportGenerator.java`:

```java
package com.hotel.util;

import com.hotel.manager.HotelManager;
import com.hotel.model.*;
import java.time.LocalDate;
import java.util.*;

public class ReportGenerator {
    private HotelManager manager;
    
    public ReportGenerator(HotelManager manager) {
        this.manager = manager;
    }
    
    /**
     * Generate revenue report for date range
     */
    public String generateRevenueReport(LocalDate startDate, LocalDate endDate) {
        List<Payment> allPayments = manager.getAllPayments();
        double totalRevenue = 0;
        int successfulPayments = 0;
        
        for (Payment payment : allPayments) {
            if (payment.getPaymentDateTime().toLocalDate().isAfter(startDate) &&
                payment.getPaymentDateTime().toLocalDate().isBefore(endDate) &&
                payment.getStatus().equals("COMPLETED")) {
                totalRevenue += payment.getAmount();
                successfulPayments++;
            }
        }
        
        return "=== REVENUE REPORT ===\n" +
               "Period: " + startDate + " to " + endDate + "\n" +
               "Total Revenue: $" + String.format("%.2f", totalRevenue) + "\n" +
               "Successful Payments: " + successfulPayments;
    }
    
    /**
     * Generate occupancy report
     */
    public String generateOccupancyReport() {
        List<Room> allRooms = manager.getAllRooms();
        List<Reservation> activeReservations = manager.getActiveReservations();
        
        int occupied = allRooms.size() - manager.getAvailableRooms().size();
        double occupancyRate = (occupied * 100.0) / allRooms.size();
        
        return "=== OCCUPANCY REPORT ===\n" +
               "Total Rooms: " + allRooms.size() + "\n" +
               "Occupied: " + occupied + "\n" +
               "Available: " + manager.getAvailableRooms().size() + "\n" +
               "Occupancy Rate: " + String.format("%.1f", occupancyRate) + "%\n" +
               "Active Reservations: " + activeReservations.size();
    }
}
```

---

## 🔐 Part 6: Add User Authentication

### Create User Service

Create `src/com/hotel/service/UserService.java`:

```java
package com.hotel.service;

import java.util.*;

public class UserService {
    private Map<String, String> users = new HashMap<>();
    
    public UserService() {
        // Default admin user
        users.put("admin", "admin123");
    }
    
    public boolean authenticate(String username, String password) {
        return users.containsKey(username) && 
               users.get(username).equals(hashPassword(password));
    }
    
    public void registerUser(String username, String password) {
        users.put(username, hashPassword(password));
    }
    
    private String hashPassword(String password) {
        // Use proper hashing in production (bcrypt, scrypt, etc.)
        return Integer.toHexString(password.hashCode());
    }
}
```

### Create Login Frame

```java
public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserService userService;
    
    public LoginFrame() {
        setTitle("Hotel Reservation System - Login");
        userService = new UserService();
        // ... UI setup ...
        
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            if (userService.authenticate(username, password)) {
                new HomeFrame(new HotelManager());
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            }
        });
    }
}
```

---

## 🌐 Part 7: Web Interface (Optional)

### Expose API with Spring Boot

Create `RestController.java`:

```java
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private HotelManager hotelManager;
    
    @GetMapping
    public List<Room> getAllRooms() {
        return hotelManager.getAllRooms();
    }
    
    @GetMapping("/available")
    public List<Room> getAvailableRooms() {
        return hotelManager.getAvailableRooms();
    }
    
    @PostMapping("/book")
    public Reservation bookRoom(@RequestBody BookingRequest request) {
        return hotelManager.bookRoom(request.getCustomer(), request.getRoom(),
                                    request.getCheckIn(), request.getCheckOut());
    }
}
```

---

## 🔌 Part 8: Plugin Architecture

### Create Plugin Interface

```java
package com.hotel.plugin;

public interface HotelPlugin {
    String getName();
    String getVersion();
    void onRoomBooked(Reservation reservation);
    void onReservationCancelled(Reservation reservation);
    void onPaymentProcessed(Payment payment);
}
```

### Implement Plugin

```java
public class LoyaltyPointsPlugin implements HotelPlugin {
    @Override
    public String getName() {
        return "Loyalty Points System";
    }
    
    @Override
    public void onRoomBooked(Reservation reservation) {
        // Award points based on booking amount
        int points = (int)(reservation.getTotalPrice() / 10);
        System.out.println("Awarded " + points + " loyalty points");
    }
    
    @Override
    public void onPaymentProcessed(Payment payment) {
        // Update loyalty account
    }
}
```

### Register Plugins

```java
public class PluginManager {
    private List<HotelPlugin> plugins = new ArrayList<>();
    
    public void registerPlugin(HotelPlugin plugin) {
        plugins.add(plugin);
        System.out.println("Plugin registered: " + plugin.getName());
    }
    
    public void onRoomBooked(Reservation reservation) {
        for (HotelPlugin plugin : plugins) {
            plugin.onRoomBooked(reservation);
        }
    }
}
```

---

## 📱 Part 9: Mobile App Integration

### Create Android Adapter

```java
// Android code to communicate with desktop app
public class HotelAPIClient {
    private static final String BASE_URL = "http://localhost:8080/api";
    
    public List<Room> getAvailableRooms() {
        // HTTP GET http://localhost:8080/api/rooms/available
        // Parse JSON response
    }
    
    public boolean bookRoom(Reservation reservation) {
        // HTTP POST to http://localhost:8080/api/rooms/book
        // Send reservation as JSON
    }
}
```

---

## 🧪 Part 10: Advanced Testing

### Unit Test Example

```java
import org.junit.Test;
import static org.junit.Assert.*;

public class RoomTest {
    @Test
    public void testStandardRoomDiscount() {
        Room room = new StandardRoom(101);
        assertEquals(0.05, room.getDiscount(), 0.001);
        assertEquals(80.0, room.getPricePerNight(), 0.001);
    }
    
    @Test
    public void testReservationPriceCalculation() {
        Customer customer = new Customer(1, "John", "Doe", "john@test.com", "555", "addr");
        Room room = new DeluxeRoom(201);
        Reservation res = new Reservation(5001, customer, room,
                                         LocalDate.now(),
                                         LocalDate.now().plusDays(3));
        
        // 3 nights * $150 - (3 nights * $150 * 0.08 discount) = $414
        assertEquals(414.0, res.getTotalPrice(), 0.001);
    }
}
```

---

## 🚀 Part 11: Performance Optimization

### Implement Caching

```java
public class CacheManager {
    private Map<String, List<?>> cache = new HashMap<>();
    private long CACHE_DURATION = 60000; // 1 minute
    
    public List<Room> getAvailableRooms(HotelManager manager) {
        if (cache.containsKey("availableRooms")) {
            return (List<Room>) cache.get("availableRooms");
        }
        
        List<Room> rooms = manager.getAvailableRooms();
        cache.put("availableRooms", rooms);
        return rooms;
    }
}
```

### Add Connection Pooling (for DB)

```java
HikariConfig config = new HikariConfig();
config.setJdbcUrl("jdbc:mysql://localhost/hotel");
config.setUsername("root");
config.setPassword("password");
config.setMaximumPoolSize(10);
HikariDataSource dataSource = new HikariDataSource(config);
```

---

## 📦 Deployment Scenarios

### Desktop Deployment
```bash
# Create executable JAR
jar cfe HotelReservation.jar com.hotel.HotelReservationSystem -C bin .

# Run JAR
java -jar HotelReservation.jar
```

### Server Deployment
```bash
# Package as WAR for Tomcat
# Deploy to Application Server
# Access via web browser on http://localhost:8080/hotel
```

### Docker Deployment
```dockerfile
FROM openjdk:11
WORKDIR /app
COPY bin /app/bin
COPY data /app/data
CMD ["java", "-cp", "/app/bin", "com.hotel.HotelReservationSystem"]
```

---

## ✅ Customization Checklist

- [ ] Added new room types
- [ ] Integrated database backend
- [ ] Customized UI theme/colors
- [ ] Added reporting functionality
- [ ] Implemented user authentication
- [ ] Created email notifications
- [ ] Built REST API for web access
- [ ] Developed mobile app connector
- [ ] Added unit tests
- [ ] Optimized performance
- [ ] Created deployment package

---

**Happy Extending!** 🎉

