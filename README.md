# 🚗 Vehicle Rental System

A comprehensive console-based Vehicle Rental Management System built in Java following MVC (Model-View-Controller) architecture. This system demonstrates Object-Oriented Programming principles, custom data structures, and file I/O operations.

## 🎯 Overview

The Vehicle Rental System is a Java application that allows a rental company to:
- Manage their vehicle inventory (Cars, Motorcycles, Trucks)
- Handle customer rentals through a shopping cart system
- Process checkout with detailed billing
- Support undo operations for cart additions
- Persist data between sessions using file storage

## ✨ Features

### Inventory Management
- ✅ Load inventory from file (`data/vehicles.txt`)
- ✅ Display all vehicles in a formatted table
- ✅ Search vehicle by ID (exact match)
- ✅ Search vehicle by model (partial, case-insensitive)
- ✅ Add new vehicle (Car, Motorcycle, or Truck)
- ✅ Remove vehicle (with rental status check)
- ✅ Update vehicle availability
- ✅ Sort vehicles by rental cost (ascending)
- ✅ Show the most expensive vehicle for X days

### Shopping Cart (Custom Linked List)
- ✅ Add vehicle to cart with availability check
- ✅ View cart with itemized subtotals
- ✅ Remove item from cart (restores availability)
- ✅ Update rental days in cart
- ✅ Clear entire cart (restores all availability)

### Undo Feature (Custom Stack)
- ✅ Undo last cart addition (removes item, restores availability)
- ✅ Stack-based implementation using a singly linked list
- ✅ Clear undo stack after checkout

### Checkout & Billing
- ✅ Generate a detailed invoice with all items
- ✅ Calculate total bill amount
- ✅ Display itemized bill with subtotals per vehicle
- ✅ Permanent inventory update after checkout
- ✅ Clear cart and undo stack

### Data Persistence
- ✅ Automatic loading from `vehicles.txt` on startup
- ✅ Automatic saving to `vehicles.txt` on exit

## 🛠 Technologies Used

| Technology | Purpose |
|------------|---------|
| Java (JDK 8+) | Core programming language |
| Java Collections Framework | ArrayList for random access |
| Custom Singly Linked List | Shopping cart implementation |
| Custom Stack (LIFO) | Undo functionality |
| File I/O (BufferedReader/Writer) | Data persistence |
| MVC Architecture | Code organization |

## 📁 Project Structure
```
VehicleRentalSystem/
│
├── src/ # Source code directory
│ ├── Main.java # Application entry point
│ │
│ ├── controller/ # Controller layer
│ │ └── RentalController.java # Main controller (handles user input)
│ │
│ ├── model/ # Model layer (data structures)
│ │ ├── Rentable.java # ADT interface
│ │ ├── Vehicle.java # Abstract base class
│ │ ├── Car.java # Car implementation
│ │ ├── Motorcycle.java # Motorcycle implementation
│ │ ├── Truck.java # Truck implementation
│ │ ├── CartNode.java # Singly linked list node
│ │ ├── RentalCart.java # Custom linked list for cart
│ │ ├── StackNode.java # Stack node
│ │ ├── LinkedListStack.java # Custom stack implementation
│ │ └── CartAction.java # Undo action data
│ │
│ ├── repository/ # Repository layer (data access)
│ │ ├── VehicleRepository.java # ArrayList-based storage
│ │ └── FileManager.java # File I/O operations
│ │
│ ├── service/ # Service layer (business logic)
│ │ ├── InventoryService.java # Inventory business logic
│ │ ├── CartService.java # Cart business logic
│ │ ├── UndoService.java # Undo stack logic
│ │ └── CheckoutService.java # Billing logic
│ │
│ └── view/ # View layer (UI display)
│ ├── MenuView.java # Menu display
│ ├── InventoryView.java # Inventory table display
│ ├── CartView.java # Cart display
│ └── BillView.java # Invoice display
│
├── data/ # Data directory
│ └── vehicles.txt # Vehicle inventory file
│
├── bin/ # Compiled classes (auto-generated)
├── .vscode/ # VS Code configuration
│ └── settings.json
│
└── README.md # This file
```


## 🔧 Data Structures Implemented

### From Grocery Store Assignment:
| Data Structure | Usage | Justification |
|----------------|-------|---------------|
| `ArrayList<Product>` | Vehicle inventory | O(1) random access by index, frequent ID lookups |
| Custom Singly Linked List | Shopping cart | Sequential access, frequent front/back additions/removals |
| Custom Stack (Linked List based) | Undo feature | LIFO access pattern for undo operations |

### From University Assignment:
| Concept | Usage | Implementation |
|---------|-------|----------------|
| Interface (ADT) | `Rentable` | Defines contract for all vehicle types |
| Generics | `RentalManager<T extends Rentable>` | Type-safe vehicle management |
| Polymorphism | `calculateRentalCost()` | Different calculation per vehicle type |
| Sorting by calculated value | Sort vehicles by rental cost | Similar to sorting courses by workload |

## 💻 Installation & Setup

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Any Java IDE (VS Code, IntelliJ IDEA, Eclipse) or command line

1. **Clone or download the project**
```
git clone https://github.com/yourusername/VehicleRentalSystem.git
cd VehicleRentalSystem
```

2. **Open in VS Code**
```
code .
```

3. **Ensure Java extensions are installed**
- Extension Pack for Java (Microsoft)
- Debugger for Java

4. **Clean and rebuild**
- Press ```Ctrl+Shift+P```
- Type "Java: Clean Java Language Server Workspace"
- Reload VS Code

## 📖 Usage Guide
### Main Menu Options
```
╔════════════════════════════════════════╗
║         VEHICLE RENTAL SYSTEM          ║
╠════════════════════════════════════════╣
║ [INVENTORY]                            ║
║  1. Display all vehicles               ║
║  2. Search by ID                       ║
║  3. Search by model                    ║
║  4. Add new vehicle                    ║
║  5. Remove vehicle                     ║
║  6. Sort by cost                       ║
║  7. Most expensive vehicle             ║
╠════════════════════════════════════════╣
║ [CART]                                 ║
║  8. Add to cart                        ║
║  9. View cart                          ║
║ 10. Remove from cart                   ║
║ 11. Update rental days                 ║
║ 12. Undo last addition                 ║
║ 13. Clear cart                         ║
╠════════════════════════════════════════╣
║ [CHECKOUT]                             ║
║ 14. Checkout & generate bill           ║
║ 15. Save and exit                      ║
╚════════════════════════════════════════╝
```

### Step-by-Step Rental Process
1. Browse available vehicles (Option 1)
2. Add vehicle to cart (Option 8)
   - Enter vehicle ID
   - Enter number of rental days
   - Confirm addition
3. View cart (Option 9)
4. Optional: Undo last addition (Option 12)
5. Checkout (Option 14)
6. Save and exit (Option 15)

### Adding a New Vehicle
```
--- Add New Vehicle ---
Vehicle Types:
  1. Car
  2. Motorcycle
  3. Truck

Select type: 1
Enter vehicle ID: 101
Enter model: Tesla Model S
Enter daily rate: $120
Insurance type (Basic/Premium/Comprehensive): Premium
Number of doors: 4
Has AC? (y/n): y
```

### Sample Data Format
vehicles.txt Format
```
ID|Model|Type|DailyRate|Insurance|TypeSpecificField1|TypeSpecificField2
```

Examples:
```
1|Toyota Camry|Car|50.0|Premium|4|true
2|Harley Davidson|Motorcycle|40.0|Basic|883|false
3|Ford F-150|Truck|80.0|Comprehensive|2.5|true
```
### Type-specific fields:
- Car: ```numberOfDoors|hasAC```
- Motorcycle: ```engineCC|hasSidecar```
- Truck: ```cargoCapacity|isDiesel```

## 🎯 Features in Detail
### 1. Vehicle Types & Cost Calculation
Car
```
Cost = (DailyRate × Days × InsuranceMultiplier) + (AC ? $5/day : $0)
```

Motorcycle
```
Cost = (DailyRate × Days × InsuranceMultiplier) + 
       (Sidecar ? $10/day : $0) +
       (EngineCC > 600 ? $0.05 × EngineCC × Days : $0)
```

Truck
```
Cost = (DailyRate × Days × InsuranceMultiplier) + 
       (Diesel ? $20/day : $0) +
       (CargoCapacity × $15 × Days)
```

#### Insurance Multipliers
| Insurance Type | Multiplier |
|----------------|------------|
| Basic          | 1.0×       |
| Premium        | 1.3×       |
| Comprehensive  | 1.6×       |

### 2. Undo Feature
- Each cart addition is pushed onto a stack
- Undo operation pops the last action and reverses it
- Stack implemented using custom singly linked list (O(1) push/pop)

### 3. File Persistence
- Load: Reads ```data/vehicles.txt``` at startup
- Save: Writes current inventory on exit
- Format: Pipe-delimited (|) for easy parsing

## 🔮 Future Enhancements
Potential improvements for future versions:
- GUI Interface - JavaFX or Swing frontend
- Database Integration - MySQL/PostgreSQL instead of file storage
- Customer Management - Track customer rental history
- Payment System - Credit card processing
- Email Receipts - Send invoices via email
- Rental Calendar - Visual availability calendar
- Late Fee Calculation - Automatic late fee assessment
- Loyalty Program - Discounts for frequent renters
- Multi-branch Support - Multiple rental locations
- Report Generation - PDF/Excel export of rental reports
- Authentication System - Login for employees and admins
- Real-time Availability - Live sync across terminals
