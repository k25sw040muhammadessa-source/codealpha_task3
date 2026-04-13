# Hotel Reservation System - API Quick Reference

## 🎯 Quick Class Reference

### Model: Room (Abstract)
```java
// Constructor
abstract class Room {
    Room(int roomId, String roomType, double pricePerNight, 
         int capacity, String description)
}

// Methods
int getRoomId()
String getRoomType()
double getPricePerNight()
boolean isAvailable()
void setAvailable(boolean available)
int getCapacity()
String getDescription()
abstract String getRoomDetails()
abstract double getDiscount()
```

### Model: StandardRoom
```java
StandardRoom(int roomId)      // $80/night, 2 guests, 5% discount
// Inherits all Room methods
```

### Model: DeluxeRoom
```java
DeluxeRoom(int roomId)        // $150/night, 3 guests, 8% discount
// Inherits all Room methods
```

### Model: Suite
```java
Suite(int roomId)             // $300/night, 4 guests, 10% discount
// Inherits all Room methods
```

### Model: Customer
```java
Customer(int customerId, String firstName, String lastName, 
         String email, String phone, String address)

// Getters
int getCustomerId()
String getFirstName()
String getLastName()
String getEmail()
String getPhone()
String getAddress()
String getFullName()          // Returns "firstName lastName"

// Setters
void setFirstName(String firstName)
void setLastName(String lastName)
void setEmail(String email)   // Validates: must contain '@'
void setPhone(String phone)
void setAddress(String address)
```

### Model: Reservation
```java
Reservation(int reservationId, Customer customer, Room room,
            LocalDate checkInDate, LocalDate checkOutDate)

// Getters
int getReservationId()
Customer getCustomer()
Room getRoom()
LocalDate getCheckInDate()
LocalDate getCheckOutDate()
double getTotalPrice()
String getStatus()            // "CONFIRMED" or "CANCELLED"
LocalDate getReservationDate()

// Methods
long getNumberOfNights()      // Days between check-in/check-out
void setStatus(String status)
void cancelReservation()      // Sets status to CANCELLED
String getReservationDetails() // Formatted string with all info

// Private
calculateTotalPrice()         // Auto-called in constructor
                             // With room-specific discount applied
```

### Model: Payment
```java
Payment(int paymentId, int reservationId, double amount, 
        String paymentMethod)

// Getters
int getPaymentId()
int getReservationId()
double getAmount()
String getPaymentMethod()    // CREDIT_CARD, DEBIT_CARD, CASH
String getStatus()            // PENDING, COMPLETED, FAILED
LocalDateTime getPaymentDateTime()
String getTransactionId()

// Methods
boolean processPayment()      // Simulates payment, returns success
                             // 99% success rate, 500ms delay
String getPaymentReceipt()    // Formatted receipt string
void setStatus(String status)

// Private
String generateTransactionId() // Creates unique ID: PAY-YYYYMMDD-#####
```

### Manager: HotelManager
```java
// Constructor
HotelManager()                // Auto-loads data from .dat files

// Room Operations
List<Room> getAllRooms()
List<Room> getAvailableRooms()
List<Room> searchAvailableRoomsByType(String roomType)
                             // Filters by "Standard Room", "Deluxe Room", "Suite"
String[] getRoomTypes()      // Returns ["Standard Room", "Deluxe Room", "Suite"]

// Booking Operations
synchronized Reservation bookRoom(Customer customer, Room room,
                                 LocalDate checkIn, LocalDate checkOut)
                             // Creates reservation, marks room unavailable
                             // Returns Reservation object
synchronized boolean cancelReservation(int reservationId)
                             // Cancels booking, frees room
                             // Returns true if successful

Reservation getReservation(int reservationId)
List<Reservation> getAllReservations()
List<Reservation> getActiveReservations()  // Only CONFIRMED status

// Payment Operations
synchronized Payment processPayment(int reservationId, 
                                   String paymentMethod)
                             // Simulates payment processing
                             // Returns Payment object
Payment getPayment(int reservationId)
List<Payment> getAllPayments()

// Customer Operations
Customer registerCustomer(String firstName, String lastName,
                         String email, String phone, String address)
                             // Creates and returns Customer object
Customer getCustomer(int customerId)
List<Customer> getAllCustomers()

// Statistics
String getStatistics()       // Returns formatted stats string
                             // Includes room availability, revenue, etc.

// Data Persistence (Private)
void loadData()              // Auto-called from constructor
void saveData()              // Auto-called after modifications
```

### Utility: UIConstants
```java
// Colors
PRIMARY_COLOR       = new Color(33, 150, 243)     // Blue
SECONDARY_COLOR     = new Color(76, 175, 80)      // Green
ACCENT_COLOR        = new Color(255, 152, 0)      // Orange
DANGER_COLOR        = new Color(244, 67, 54)      // Red
BACKGROUND_COLOR    = new Color(245, 245, 245)    // Light Gray
TEXT_PRIMARY        = new Color(33, 33, 33)       // Dark Gray
TEXT_SECONDARY      = new Color(117, 117, 117)    // Medium Gray
BORDER_COLOR        = new Color(189, 189, 189)    // Border Gray

// Fonts
TITLE_FONT          = new Font("Segoe UI", Font.BOLD, 28)
HEADING_FONT        = new Font("Segoe UI", Font.BOLD, 18)
LABEL_FONT          = new Font("Segoe UI", Font.PLAIN, 14)
BUTTON_FONT         = new Font("Segoe UI", Font.BOLD, 14)
SMALL_FONT          = new Font("Segoe UI", Font.PLAIN, 12)

// Sizes
PADDING_SMALL       = 5
PADDING_MEDIUM      = 10
PADDING_LARGE       = 20
WINDOW_WIDTH        = 1000
WINDOW_HEIGHT       = 700

// Static Utility Methods
static JButton createStyledButton(String text, Color bgColor, 
                                 ActionListener listener)
static JLabel createStyledLabel(String text, Font font)
static JTextField createStyledTextField(int columns)
static <T> JComboBox<T> createStyledComboBox(T[] items)
static JPanel createRoundedPanel(Color bgColor)
```

### Utility: SessionManager (Singleton)
```java
// Get singleton instance
static SessionManager getInstance()

// Session Data Management
void setData(String key, Object value)
Object getData(String key)
void clearData(String key)
void clearAllData()

// Customer ID Management
int getCurrentCustomerId()
void setCurrentCustomerId(int id)
```

### UI: BaseFrame (Abstract)
```java
// Constructor
BaseFrame(String title, HotelManager hotelManager)
          // Extends JFrame, auto-configures window

// Protected Methods (for subclasses)
abstract void initializeComponents()
void setupNavigationPanel()
protected JButton createNavButton(String text, ActionListener listener)
void goBack()                // Returns to HomeFrame

// Protected Fields
HotelManager hotelManager
JPanel contentPanel
JPanel navigationPanel
```

### UI: HomeFrame
```java
HomeFrame(HotelManager hotelManager)  // Shows dashboard

// Associated Buttons
"Search & Book Rooms"        → Opens SearchBookingFrame
"My Bookings"                → Opens BookingsFrame
"Cancel Reservation"         → Opens CancelReservationFrame
"Exit"                       → Closes application

// Displays
- Total available rooms
- Total active reservations
- Revenue statistics
```

### UI: SearchBookingFrame
```java
SearchBookingFrame(HotelManager hotelManager)  // Room search & booking

// Tabs / Sections
1. Search Rooms Tab
   - Room type filter (ComboBox)
   - Check-in date (Spinner)
   - Check-out date (Spinner)
   - Available rooms table
   - Search button

2. Customer Info Tab
   - First Name (TextField)
   - Last Name (TextField)
   - Email (TextField with validation)
   - Phone (TextField)
   - Address (TextField)

3. Complete Booking Tab
   - Selected room details display
   - Total price calculation
   - Payment method selection
   - "Book Now" button
   - Confirmation dialog
```

### UI: BookingsFrame
```java
BookingsFrame(HotelManager hotelManager)  // View all reservations

// Display
- Table of all CONFIRMED reservations
- Columns: Reservation ID, Customer Name, Room Type, 
          Check-in, Check-out, Status, Total Price
- Click row for detailed view
- Payment status indicator
```

### UI: CancelReservationFrame
```java
CancelReservationFrame(HotelManager hotelManager)

// Components
- Reservation ID input field
- "Search Reservation" button
- Reservation details display
- "Cancel Booking" button
- Confirmation dialog

// Logic
- Validate reservation exists
- Confirm cancellation request
- Set room back to available
- Update UI with success message
```

---

## 📊 Common Usage Patterns

### Create and Book a Room
```java
HotelManager manager = new HotelManager();

// 1. Create customer
Customer customer = manager.registerCustomer(
    "John", "Doe", "john@email.com", "555-1234", "123 Main St"
);

// 2. Find available room
List<Room> availableRooms = manager.searchAvailableRoomsByType("Deluxe Room");
Room room = availableRooms.get(0);

// 3. Create reservation
LocalDate checkIn = LocalDate.now().plusDays(1);
LocalDate checkOut = LocalDate.now().plusDays(3);
Reservation res = manager.bookRoom(customer, room, checkIn, checkOut);

// 4. Process payment
Payment payment = manager.processPayment(res.getReservationId(), "CREDIT_CARD");

// Result: Room is now booked, data saved to disk
```

### Search and Display Rooms
```java
HotelManager manager = new HotelManager();

// Get all available rooms
List<Room> available = manager.getAvailableRooms();

// Get rooms by type
List<Room> deluxe = manager.searchAvailableRoomsByType("Deluxe Room");

// Display details
for (Room room : deluxe) {
    System.out.println(room.getRoomDetails());
    System.out.println("Discount: " + (room.getDiscount() * 100) + "%");
}
```

### Cancel a Reservation
```java
HotelManager manager = new HotelManager();

int reservationId = 5000;
boolean success = manager.cancelReservation(reservationId);

if (success) {
    System.out.println("Reservation cancelled!");
    // Room is now available again
} else {
    System.out.println("Cancellation failed");
}
```

### Get Booking Details
```java
HotelManager manager = new HotelManager();

Reservation res = manager.getReservation(5000);

if (res != null) {
    System.out.println("Customer: " + res.getCustomer().getFullName());
    System.out.println("Room: " + res.getRoom().getRoomType());
    System.out.println("Nights: " + res.getNumberOfNights());
    System.out.println("Total: $" + res.getTotalPrice());
}
```

### Display Statistics
```java
HotelManager manager = new HotelManager();

String stats = manager.getStatistics();
System.out.println(stats);
/* Output:
=== HOTEL STATISTICS ===
Total Rooms: 15
Available Rooms: 8
Occupied Rooms: 7
Total Revenue: $12,450.50
Total Bookings: 45
... etc
*/
```

---

## 🎨 UI customization Examples

### Change Primary Color
```java
// In UIConstants.java
public static final Color PRIMARY_COLOR = new Color(0, 128, 255);  // Different blue
```

### Add Custom Font
```java
// In UIConstants.java
public static final Font CUSTOM_FONT = new Font("Arial", Font.BOLD, 16);

// Use in UI
JLabel label = UIConstants.createStyledLabel("Text", UIConstants.CUSTOM_FONT);
```

### Create Custom Button
```java
// In any UI frame
JButton customBtn = UIConstants.createStyledButton(
    "Custom Button", 
    UIConstants.PRIMARY_COLOR,
    e -> System.out.println("Clicked!")
);
panel.add(customBtn);
```

---

## 🔍 Debugging Tips

### Print Manager State
```java
HotelManager manager = new HotelManager();

// All rooms
manager.getAllRooms().forEach(System.out::println);

// All reservations
manager.getAllReservations().forEach(r -> 
    System.out.println(r.getReservationDetails())
);

// Statistics
System.out.println(manager.getStatistics());
```

### Check Serialization
```java
// These files should exist after first run:
// - data/rooms.dat
// - data/reservations.dat
// - data/customers.dat
// - data/payments.dat

// To verify, Java can deserialize:
java.io.File file = new java.io.File("data/rooms.dat");
System.out.println("File exists: " + file.exists());
System.out.println("File size: " + file.length() + " bytes");
```

---

## 📋 Method Signatures Quick List

### Create Objects
```
new StandardRoom(102)
new DeluxeRoom(202)
new Suite(302)
new Customer(1000, "John", "Doe", "john@test.com", "555-1234", "Address")
new Reservation(5000, customer, room, LocalDate.now(), LocalDate.now().plusDays(2))
new Payment(10000, 5000, 300.0, "CREDIT_CARD")
new HotelManager()
```

### Common Method Calls
```
manager.bookRoom(customer, room, checkIn, checkOut)
manager.cancelReservation(5000)
manager.processPayment(5000, "CREDIT_CARD")
manager.searchAvailableRoomsByType("Deluxe Room")
manager.getReservation(5000)
manager.getStatistics()
```

---

## ✅ Data Format Reference

### .dat File Content (Serialized Objects)
```
rooms.dat
└── List<Room>
    ├── StandardRoom(101)
    ├── DeluxeRoom(201)
    ├── Suite(301)
    └── ...

reservations.dat
└── List<Reservation>
    ├── Reservation(5000, customer1, room1, ...)
    ├── Reservation(5001, customer2, room2, ...)
    └── ...

customers.dat
└── List<Customer>
    ├── Customer(1000, "John", "Doe", ...)
    ├── Customer(1001, "Jane", "Smith", ...)
    └── ...

payments.dat
└── List<Payment>
    ├── Payment(10000, 5000, 300.0, "CREDIT_CARD")
    ├── Payment(10001, 5001, 150.0, "DEBIT_CARD")
    └── ...
```

---

**💡 Tip**: Keep this reference open while developing or customizing the application!
