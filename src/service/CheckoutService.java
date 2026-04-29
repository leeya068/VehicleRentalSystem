package service;

import model.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Service for checkout and billing operations
 */
public class CheckoutService {
    private CartService cartService;
    private UndoService undoService;
    
    public CheckoutService(CartService cartService, UndoService undoService) {
        this.cartService = cartService;
        this.undoService = undoService;
    }
    
    /**
     * Calculate total bill amount
     */
    public double calculateTotal() {
        double total = 0;
        CartNode[] items = cartService.getAllCartItems();
        for (CartNode item : items) {
            total += item.getVehicle().calculateRentalCost(item.getRentalDays());
        }
        return total;
    }
    
    /**
     * Generate detailed bill (does not modify data)
     */
    public String generateBill() {
        if (cartService.isCartEmpty()) {
            return "Cart is empty. No bill to generate.";
        }
        
        StringBuilder bill = new StringBuilder();
        bill.append("\n╔═══════════════════════════════════════════════════════════════════╗\n");
        bill.append("║                         RENTAL INVOICE                              ║\n");
        bill.append("╠═══════════════════════════════════════════════════════════════════╣\n");
        
        // Add date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        bill.append(String.format("║ Date: %-59s ║\n", now.format(formatter)));
        bill.append("╠═══════════════════════════════════════════════════════════════════╣\n");
        
        CartNode[] items = cartService.getAllCartItems();
        double grandTotal = 0;
        int itemNumber = 1;
        
        for (CartNode item : items) {
            Rentable v = item.getVehicle();
            int days = item.getRentalDays();
            double subtotal = v.calculateRentalCost(days);
            grandTotal += subtotal;
            
            bill.append(String.format("║ %d. %-50s ║\n", itemNumber, v.getModel()));
            bill.append(String.format("║    Type: %-8s | Days: %-3d | Daily Rate: $%-8.2f ║\n", 
                v.getVehicleType(), days, v.getDailyRate()));
            bill.append(String.format("║    Insurance: %-12s | Subtotal: $%-29.2f ║\n", 
                v.getInsuranceType(), subtotal));
            bill.append("╠───────────────────────────────────────────────────────────────╣\n");
            itemNumber++;
        }
        
        bill.append(String.format("║ GRAND TOTAL: $%-56.2f ║\n", grandTotal));
        bill.append("╚═══════════════════════════════════════════════════════════════════╝\n");
        
        return bill.toString();
    }
    
    /**
     * Complete checkout - finalizes rental, clears cart and undo stack
     */
    public boolean checkout() {
        if (cartService.isCartEmpty()) {
            System.out.println("Cannot checkout - cart is empty!");
            return false;
        }
        
        // Generate and display bill
        System.out.println(generateBill());
        
        // Note: Inventory availability is already updated (set to false when adding to cart)
        // No further action needed - vehicles remain "rented" (available = false)
        
        // Clear cart and undo stack
        cartService.clearCart();
        undoService.clearUndoStack();
        
        System.out.println("Checkout complete! Thank you for renting from us.");
        return true;
    }
}