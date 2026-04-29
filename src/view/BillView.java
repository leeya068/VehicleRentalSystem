package view;

import model.CartNode;
import model.Rentable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles bill/invoice display formatting
 */
public class BillView {
    
    public static void displayBill(CartNode[] items, double grandTotal) {
        if (items == null || items.length == 0) {
            System.out.println("\nCannot generate bill - cart is empty!");
            return;
        }
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                         RENTAL INVOICE                              ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        
        // Add date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.printf("║ Date: %-59s ║\n", now.format(formatter));
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        
        int itemNumber = 1;
        for (CartNode item : items) {
            Rentable v = item.getVehicle();
            int days = item.getRentalDays();
            double subtotal = v.calculateRentalCost(days);
            
            System.out.printf("║ %d. %-50s ║\n", itemNumber, v.getModel());
            System.out.printf("║    Type: %-8s | Days: %-3d | Daily Rate: $%-8.2f ║\n", 
                v.getVehicleType(), days, v.getDailyRate());
            System.out.printf("║    Insurance: %-12s | Subtotal: $%-29.2f ║\n", 
                v.getInsuranceType(), subtotal);
            System.out.println("╠───────────────────────────────────────────────────────────────╣");
            itemNumber++;
        }
        
        System.out.printf("║ GRAND TOTAL: $%-56.2f ║\n", grandTotal);
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
    }
    
    public static void displaySimpleReceipt(CartNode[] items, double total) {
        System.out.println("\n=== RENTAL RECEIPT ===");
        for (CartNode item : items) {
            Rentable v = item.getVehicle();
            System.out.printf("  %s x%d days = $%.2f\n", 
                v.getModel(), item.getRentalDays(), 
                v.calculateRentalCost(item.getRentalDays()));
        }
        System.out.printf("  TOTAL: $%.2f\n", total);
        System.out.println("=====================");
    }
}