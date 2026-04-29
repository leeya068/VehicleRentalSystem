package view;

/**
 * Handles all menu display logic
 * Separates UI from business logic
 */
public class MenuView {
    
    public static void displayWelcome() {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║         WELCOME TO VEHICLE RENTAL SYSTEM            ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }
    
    public static void displayMainMenu() {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║                 VEHICLE RENTAL SYSTEM                ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║ [INVENTORY MANAGEMENT]                               ║");
        System.out.println("║  1. Display all vehicles                             ║");
        System.out.println("║  2. Search vehicle by ID                             ║");
        System.out.println("║  3. Search vehicle by model (partial)                ║");
        System.out.println("║  4. Add new vehicle                                  ║");
        System.out.println("║  5. Remove vehicle                                   ║");
        System.out.println("║  6. Sort vehicles by rental cost                     ║");
        System.out.println("║  7. Show most expensive vehicle (7 days)             ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║ [RENTAL CART]                                        ║");
        System.out.println("║  8. Add vehicle to cart                              ║");
        System.out.println("║  9. View cart                                        ║");
        System.out.println("║ 10. Remove item from cart                            ║");
        System.out.println("║ 11. Update rental days in cart                       ║");
        System.out.println("║ 12. Undo last cart addition (Stack)                  ║");
        System.out.println("║ 13. Clear entire cart                                ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║ [CHECKOUT & EXIT]                                    ║");
        System.out.println("║ 14. Checkout & generate bill                         ║");
        System.out.println("║ 15. Save and exit                                    ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }
    
    public static void displayVehicleTypeMenu() {
        System.out.println("\n--- Add New Vehicle ---");
        System.out.println("Vehicle Types:");
        System.out.println("  1. Car");
        System.out.println("  2. Motorcycle");
        System.out.println("  3. Truck");
    }
    
    public static void displaySeparator() {
        System.out.println("─".repeat(60));
    }
    
    public static void displayError(String message) {
        System.out.println("❌ ERROR: " + message);
    }
    
    public static void displaySuccess(String message) {
        System.out.println("✅ " + message);
    }
    
    public static void displayInfo(String message) {
        System.out.println("ℹ️  " + message);
    }
}