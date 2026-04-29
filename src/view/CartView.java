package view;

import model.CartNode;
import model.Rentable;

/**
 * Handles shopping cart display formatting
 */
public class CartView {
    
    public static void displayCartHeader() {
        System.out.println("\n┌─────────────────────────────────────────────────────────────────┐");
        System.out.println("│                        RENTAL CART                                 │");
        System.out.println("├─────┬───────────────────────┬──────────┬────────────┬─────────────┤");
        System.out.println("│ ID  │ Model                  │ Days     │ Daily Rate │ Subtotal    │");
        System.out.println("├─────┼───────────────────────┼──────────┼────────────┼─────────────┤");
    }
    
    public static void displayCartFooter(double total) {
        System.out.println("├─────┴───────────────────────┴──────────┴────────────┴─────────────┤");
        System.out.printf("│ TOTAL: $%-67.2f │\n", total);
        System.out.println("└─────────────────────────────────────────────────────────────────┘");
    }
    
    public static void displayCartItem(CartNode item, int index) {
        Rentable v = item.getVehicle();
        int days = item.getRentalDays();
        double subtotal = v.calculateRentalCost(days);
        
        System.out.printf("│ %-3d │ %-21s │ %-8d │ $%-10.2f │ $%-11.2f │\n",
            v.getVehicleId(),
            v.getModel().length() > 21 ? v.getModel().substring(0, 18) + "..." : v.getModel(),
            days, v.getDailyRate(), subtotal);
    }
    
    public static void displayEmptyCart() {
        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.println("│ CART IS EMPTY                            │");
        System.out.println("└─────────────────────────────────────────┘");
    }
    
    public static void displayCartSummary(int itemCount, double total) {
        System.out.printf("\nCart Summary: %d item(s), Total: $%.2f\n", itemCount, total);
    }
}