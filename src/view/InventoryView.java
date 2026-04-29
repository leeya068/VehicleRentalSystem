package view;

import model.Rentable;
import java.util.ArrayList;

/**
 * Handles inventory display formatting
 */
public class InventoryView {
    
    public static void displayInventoryHeader() {
        System.out.println("\n┌─────┬───────────────────────┬────────────┬─────────────────┬────────────┐");
        System.out.println("│ ID  │ Model                  │ Daily Rate │ Insurance       │ Available  │");
        System.out.println("├─────┼───────────────────────┼────────────┼─────────────────┼────────────┤");
    }
    
    public static void displayInventoryFooter() {
        System.out.println("└─────┴───────────────────────┴────────────┴─────────────────┴────────────┘");
    }
    
    public static void displayVehicle(Rentable v) {
        System.out.printf("│ %-3d │ %-21s │ $%-10.2f │ %-15s │ %-10s │\n",
            v.getVehicleId(),
            v.getModel().length() > 21 ? v.getModel().substring(0, 18) + "..." : v.getModel(),
            v.getDailyRate(),
            v.getInsuranceType(),
            v.isAvailable() ? "Yes" : "No");
    }
    
    public static void displayAllVehicles(ArrayList<Rentable> vehicles) {
        if (vehicles.isEmpty()) {
            System.out.println("\nNo vehicles in inventory.");
            return;
        }
        
        displayInventoryHeader();
        for (Rentable v : vehicles) {
            displayVehicle(v);
        }
        displayInventoryFooter();
        System.out.println("\nTotal vehicles: " + vehicles.size());
    }
    
    public static void displaySearchResults(ArrayList<Rentable> results, String searchTerm) {
        if (results.isEmpty()) {
            System.out.println("\nNo vehicles found matching: " + searchTerm);
        } else {
            System.out.println("\nFound " + results.size() + " vehicle(s) matching '" + searchTerm + "':");
            displayAllVehicles(results);
        }
    }
    
    public static void displayVehicleDetails(Rentable vehicle) {
        if (vehicle != null) {
            vehicle.printVehicleDetails();
            System.out.printf("7-Day Rental Cost: $%.2f\n", vehicle.calculateRentalCost(7));
        }
    }
}