# Hotel Reservation System - Complete Documentation

## 📌 Project Overview
A professional-grade Java desktop application for managing hotel reservations. Built with Swing/AWT, demonstrating OOP principles, clean architecture, and modern UI design patterns.

## 🏗️ Architecture & Class Relationships

### Class Hierarchy Diagram
```
┌─────────────────────────────────────────────────────────────┐
│                    HOTEL RESERVATION SYSTEM                 │
└─────────────────────────────────────────────────────────────┘

MODEL LAYER (com.hotel.model)
├── Room (Abstract Base Class)
│   ├── StandardRoom (₹80/night, 2 guests)
│   ├── DeluxeRoom (₹150/night, 3 guests)
│   └── Suite (₹300/night, 4 guests)
├── Customer (Represents hotel guests)
├── Reservation (Links Customer + Room + Dates)
└── Payment (Handles payment processing)

MANAGER LAYER (com.hotel.manager)
└── HotelManager (Central Business Logic)
    ├── Manages Room inventory
    ├── Processes Reservations
    ├── Handles Payments
    ├── Manages Customers
    └── Data Persistence (File I/O)

UI LAYER (com.hotel.ui)
├── BaseFrame (Abstract Base for all screens)
├── HomeFrame (Dashboard & Navigation)
├── SearchBookingFrame (Room search & booking)
├── BookingsFrame (View all reservations)
└── CancelReservationFrame (Cancel bookings)

UTILITY LAYER (com.hotel.util)
├── UIConstants (Styling & colors)
└── SessionManager (Session handling)

MAIN ENTRY
└── HotelReservationSystem (main method)
```

## 🧩 OOP Principles Implementation

### 1. **Encapsulation**
- All class attributes are `private` with public getter/setter methods
- Validation in setters (e.g., email must contain '@', price must be positive)
- Controlled access to internal data

**Example (Customer.java):**
```java
private String email;

public void setEmail(String email) {
    if (email != null && email.contains("@")) {
        this.email = email;  // Only valid emails accepted
    }
}
```

### 2. **Inheritance**
- `Room` is abstract base class
- `StandardRoom`, `DeluxeRoom`, and `Suite` extend `Room`
- Subclasses inherit common properties (roomId, price, availability)
- Subclasses override properties like price and capacity

**Example:**
```java
public abstract class Room implements Serializable {
    protected double pricePerNight;
    // Common properties...
}

public class DeluxeRoom extends Room {
    public DeluxeRoom(int roomId) {
        super(roomId, "Deluxe Room", 150.0, 3, "Premium room");
    }
}
```

### 3. **Polymorphism**
- Abstract method `getRoomDetails()` implemented differently in each subclass
- `getDiscount()` method returns different values per room type
- Runtime polymorphism when processing different room types

**Example:**
```java
// In Room (abstract)
public abstract String getRoomDetails();

// In StandardRoom
@Override
public String getRoomDetails() {
    return "Standard Room (ID: " + roomId + ")\n" +
           "Price: $" + pricePerNight + " per night\n" +
           "Capacity: " + capacity + " guests\n" +
           "Amenities: Basic bed, bathroom, TV, WiFi";
}

// In Suite
@Override
public String getRoomDetails() {
    return "Suite (ID: " + roomId + ")\n" +
           "Price: $" + pricePerNight + " per night\n" +
           "Amenities: Multiple beds, living area, luxury bathroom...";
}
```

### 4. **Abstraction**
- `Room` is abstract - cannot instantiate directly, only through subclasses
- `BaseFrame` is abstract - enforces UI structure across all screens
- Users interact with interfaces, not implementation details
- HotelManager abstracts complex business logic

**Example:**
```java
// Cannot do this:
Room room = new Room(); // ❌ COMPILE ERROR

// Must do this:
Room room = new StandardRoom(101); // ✅ Creates StandardRoom instance
```

## 💾 Data Storage Architecture

### Serialization & File I/O
All data persists to disk using Java object serialization:

```
data/
├── rooms.dat           (List<Room> - all room objects)
├── reservations.dat    (List<Reservation> - all bookings)
├── payments.dat        (List<Payment> - all transactions)
└── customers.dat       (List<Customer> - all guests)
```

**Data Flow:**
1. Objects created in memory
2. Automatically saved to `.dat` files
3. On application restart, data reloaded from files
4. Thread-safe operations with `synchronized` methods

## 📋 Core Features

### 1. Room Search & Booking
- Filter rooms by category (Standard/Deluxe/Suite)
- View availability in real-time
- Book with check-in/check-out dates
- Automatic price calculation
- Discount applied based on room type

### 2. Reservation Management
- Create reservations with customer info
- Automatic room marking as unavailable
- Calculate stay duration
- Total price calculation with discounts

### 3. Payment Processing
- Simulate payment with 99% success rate
- Generate unique transaction IDs
- Record payment details
- Payment receipt generation

### 4. Cancellation System
- Cancel existing reservations
- Automatically free up rooms
- Status tracking (CONFIRMED/CANCELLED)
- Cancellation validation

### 5. Dashboard & Statistics
- Real-time room availability stats
- Total revenue calculation
- Reservation summary
- Occupancy rates

## 🎨 UI Design Elements

### Color Scheme
```java
PRIMARY_COLOR    = Blue (#2196F3)        - Main actions
SECONDARY_COLOR  = Green (#4CAF50)       - Confirmations
ACCENT_COLOR     = Orange (#FF9800)      - Secondary actions
DANGER_COLOR     = Red (#F44336)         - Deletions/Cancellations
BACKGROUND_COLOR = Light Gray (#F5F5F5)  - Main background
```

### Typography
- **Titles**: Segoe UI, Bold, 28pt
- **Headings**: Segoe UI, Bold, 18pt
- **Labels**: Segoe UI, Regular, 14pt
- **Buttons**: Segoe UI, Bold, 14pt
- **Small Text**: Segoe UI, Regular, 12pt

### Layout Strategy
- **BorderLayout**: Main frame structure (North, Center, South)
- **GridLayout**: Form layouts and multi-column displays
- **FlowLayout**: Navigation bars and button groups
- **Tabbed Panes**: Multi-step workflows (Search, Customer, Booking)

## 🚀 Running the Application

### Compilation
```bash
cd "Hostel Reservation System"
javac -d bin src/com/hotel/**/*.java src/com/hotel/*/*/*.java
```

### Execution
```bash
java -cp bin com.hotel.HotelReservationSystem
```

### Requirements
- Java 8 or higher
- Swing/AWT (included in JDK)
- No external dependencies

## 📊 Data Flow Example

### Complete Booking Flow
```
User Input
    ↓
SearchBookingFrame collects data
    ↓
HotelManager.bookRoom()
    ├── Creates Customer object
    ├── Marks Room as unavailable
    ├── Creates Reservation
    └── Saves to rooms.dat
    ↓
HotelManager.processPayment()
    ├── Creates Payment object
    ├── Simulates processing
    ├── Sets payment status
    └── Saves to payments.dat
    ↓
Show confirmation with receipt
    ↓
Return to HomeFrame
```

## 📝 Class Details

### Room Classes
| Class | Price | Capacity | Features |
|-------|-------|----------|----------|
| StandardRoom | $80 | 2 | Basic, 5% discount |
| DeluxeRoom | $150 | 3 | Premium, 8% discount |
| Suite | $300 | 4 | Luxury, 10% discount |

### Key Methods

**HotelManager**
- `bookRoom(customer, room, checkIn, checkOut)` - Create booking
- `cancelReservation(reservationId)` - Cancel booking
- `processPayment(reservationId, method)` - Handle payment
- `searchAvailableRoomsByType(type)` - Filter rooms
- `getStatistics()` - Dashboard stats

**Reservation**
- `calculateTotalPrice()` - Compute total with discount
- `getNumberOfNights()` - Calculate duration
- `getReservationDetails()` - Format details

**Payment**
- `processPayment()` - Simulate payment
- `generateTransactionId()` - Unique ID
- `getPaymentReceipt()` - Format receipt

## 🔒 Thread Safety
- `synchronized` keyword on critical manager methods
- `Serializable` interfaces for data persistence
- Thread-safe UI updates via SwingUtilities.invokeLater()

## 🎯 Validation & Error Handling

### Input Validation
- Non-empty customer names
- Valid email format (contains '@')
- Check-out date after check-in date
- Positive room prices
- Reservation ID must be numeric

### Error Handling
- Try-catch blocks around file I/O
- User-friendly error dialogs
- Exception messages logged to console
- Graceful degradation on missing data

## 📦 Package Structure
```
src/com/hotel/
├── HotelReservationSystem.java  (Main entry point)
├── model/                        (Data models)
│   ├── Room.java
│   ├── StandardRoom.java
│   ├── DeluxeRoom.java
│   ├── Suite.java
│   ├── Customer.java
│   ├── Reservation.java
│   └── Payment.java
├── manager/                      (Business logic)
│   └── HotelManager.java
├── ui/                          (User interface)
│   ├── BaseFrame.java
│   ├── HomeFrame.java
│   ├── SearchBookingFrame.java
│   ├── BookingsFrame.java
│   └── CancelReservationFrame.java
└── util/                        (Utilities)
    ├── UIConstants.java
    └── SessionManager.java
```

## 🎓 Learning Outcomes
This project demonstrates:
1. ✅ Object-Oriented Programming principles
2. ✅ Java Swing GUI framework
3. ✅ File I/O and serialization
4. ✅ Abstract classes and inheritance
5. ✅ Polymorphism and method overriding
6. ✅ Application architecture patterns
7. ✅ Multi-screen navigation
8. ✅ Data validation and error handling
9. ✅ Professional code organization
10. ✅ Modern UI/UX design

## 🔮 Possible Enhancements
- Database integration (MySQL/PostgreSQL)
- User authentication system
- Email notifications
- Advanced search filters (price range, amenities)
- Room images/gallery
- Online payment gateway integration
- Admin panel for room management
- Review and rating system
- Multi-language support
- Printed booking confirmations
