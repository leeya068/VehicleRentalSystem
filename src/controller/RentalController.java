package controller;

import model.Rentable;
import model.CartNode;
import repository.FileManager;
import service.InventoryService;
import service.CartService;
import service.UndoService;
import service.CheckoutService;
import view.MenuView;
import view.InventoryView;
import view.CartView;
import view.BillView;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main controller - handles user input and orchestrates the application
 * Follows MVC pattern: Controller receives input, updates Model via Service, 
 * and returns output to View
 */
public class RentalController {
    private InventoryService inventoryService;
    private CartService cartService;
    private UndoService undoService;
    private CheckoutService checkoutService;
    private Scanner scanner;
    
    public RentalController() {
        // Initialize repository and services
        var repository = new repository.VehicleRepository();
        
        // Load vehicles from file
        ArrayList<Rentable> loadedVehicles = FileManager.loadFromFile();
        for (Rentable v : loadedVehicles) {
            repository.add(v);
        }
        
        // Initialize services
        this.inventoryService = new InventoryService(repository);
        this.cartService = new CartService(inventoryService);
        this.undoService = new UndoService(cartService);
        this.checkoutService = new CheckoutService(cartService, undoService);
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        MenuView.displayWelcome();
        
        boolean running = true;
        while (running) {
            MenuView.displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1: displayAllVehicles(); break;
                case 2: searchVehicleById(); break;
                case 3: searchVehicleByName(); break;
                case 4: addVehicle(); break;
                case 5: removeVehicle(); break;
                case 6: sortVehiclesByCost(); break;
                case 7: showMostExpensiveVehicle(); break;
                case 8: addToCart(); break;
                case 9: viewCart(); break;
                case 10: removeFromCart(); break;
                case 11: updateCartDays(); break;
                case 12: undoLastAddition(); break;
                case 13: clearCart(); break;
                case 14: checkout(); break;
                case 15: running = false; break;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
        
        saveAndExit();
        scanner.close();
        System.out.println("Thank you for using Vehicle Rental System!");
    }
    
    // ==================== INVENTORY CONTROLLER METHODS ====================
    
    private void displayAllVehicles() {
        InventoryView.displayAllVehicles(inventoryService.getAllVehicles());
    }
    
    private void searchVehicleById() {
        int id = getIntInput("Enter vehicle ID: ");
        Rentable vehicle = inventoryService.searchById(id);
        InventoryView.displayVehicleDetails(vehicle);
        if (vehicle == null) {
            MenuView.displayError("Vehicle not found.");
        }
    }
    
    private void searchVehicleByName() {
        System.out.print("Enter model name (partial match): ");
        String searchTerm = scanner.nextLine();
        ArrayList<Rentable> results = inventoryService.searchByModel(searchTerm);
        InventoryView.displaySearchResults(results, searchTerm);
    }
    
    private void addVehicle() {
        MenuView.displayVehicleTypeMenu();
        int type = getIntInput("Select type: ");
        
        int id = getIntInput("Enter vehicle ID: ");
        if (inventoryService.searchById(id) != null) {
            MenuView.displayError("Vehicle ID already exists!");
            return;
        }
        
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        double dailyRate = getDoubleInput("Enter daily rate: $");
        
        System.out.print("Insurance type (Basic/Premium/Comprehensive): ");
        String insurance = scanner.nextLine();
        
        Rentable newVehicle = null;
        
        switch (type) {
            case 1: // Car
                int doors = getIntInput("Number of doors: ");
                boolean hasAC = getYesNoInput("Has AC? (y/n): ");
                newVehicle = new model.Car(id, model, dailyRate, insurance, doors, hasAC);
                break;
            case 2: // Motorcycle
                int engineCC = getIntInput("Engine CC: ");
                boolean hasSidecar = getYesNoInput("Has sidecar? (y/n): ");
                newVehicle = new model.Motorcycle(id, model, dailyRate, insurance, engineCC, hasSidecar);
                break;
            case 3: // Truck
                double cargoCap = getDoubleInput("Cargo capacity (tons): ");
                boolean isDiesel = getYesNoInput("Is diesel? (y/n): ");
                newVehicle = new model.Truck(id, model, dailyRate, insurance, cargoCap, isDiesel);
                break;
            default:
                MenuView.displayError("Invalid vehicle type.");
                return;
        }
        
        if (inventoryService.addVehicle(newVehicle)) {
            MenuView.displaySuccess("Vehicle added successfully!");
        }
    }
    
    private void removeVehicle() {
        int id = getIntInput("Enter vehicle ID to remove: ");
        
        // Check if vehicle is in cart
        if (cartService.findInCart(id) != null) {
            MenuView.displayError("Cannot remove - vehicle is in rental cart!");
            return;
        }
        
        inventoryService.removeVehicle(id);
    }
    
    private void sortVehiclesByCost() {
        int days = getIntInput("Sort by cost for how many days? ");
        inventoryService.sortVehiclesByCost(days);
        System.out.println("\nVehicles sorted by rental cost for " + days + " days (ascending):");
        displayAllVehicles();
    }
    
    private void showMostExpensiveVehicle() {
        int days = getIntInput("Calculate cost for how many days? ");
        Rentable mostExpensive = inventoryService.getMostExpensiveVehicle(days);
        if (mostExpensive != null) {
            System.out.println("\nMost expensive vehicle for " + days + " days:");
            mostExpensive.printVehicleDetails();
            System.out.printf("Total cost: $%.2f\n", mostExpensive.calculateRentalCost(days));
        } else {
            MenuView.displayError("No vehicles in inventory.");
        }
    }
    
    // ==================== CART CONTROLLER METHODS ====================
    
    private void addToCart() {
        int id = getIntInput("Enter vehicle ID to rent: ");
        Rentable vehicle = inventoryService.searchById(id);
        
        if (vehicle == null) {
            MenuView.displayError("Vehicle not found.");
            return;
        }
        
        int days = getIntInput("Enter number of rental days: ");
        
        // Calculate cost for confirmation
        double cost = vehicle.calculateRentalCost(days);
        System.out.printf("Total rental cost: $%.2f\n", cost);
        System.out.print("Confirm add to cart? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("y")) {
            if (cartService.addToCart(id, days)) {
                // Record for undo
                undoService.recordAction(vehicle, days);
                MenuView.displaySuccess("Item added to cart. Use option 12 to undo.");
            }
        }
    }
    
    private void viewCart() {
        if (cartService.isCartEmpty()) {
            CartView.displayEmptyCart();
            return;
        }
        
        CartView.displayCartHeader();
        CartNode[] items = cartService.getAllCartItems();
        for (int i = 0; i < items.length; i++) {
            CartView.displayCartItem(items[i], i);
        }
        CartView.displayCartFooter(checkoutService.calculateTotal());
    }
    
    private void removeFromCart() {
        if (cartService.isCartEmpty()) {
            MenuView.displayError("Cart is empty.");
            return;
        }
        
        viewCart();
        int id = getIntInput("Enter vehicle ID to remove from cart: ");
        cartService.removeFromCart(id);
    }
    
    private void updateCartDays() {
        if (cartService.isCartEmpty()) {
            MenuView.displayError("Cart is empty.");
            return;
        }
        
        viewCart();
        int id = getIntInput("Enter vehicle ID to update rental days: ");
        int newDays = getIntInput("Enter new number of days: ");
        cartService.updateRentalDays(id, newDays);
    }
    
    private void undoLastAddition() {
        if (undoService.canUndo()) {
            undoService.undoLastAddition();
        } else {
            MenuView.displayInfo("Nothing to undo - no recent additions.");
        }
    }
    
    private void clearCart() {
        if (cartService.isCartEmpty()) {
            MenuView.displayError("Cart is already empty.");
            return;
        }
        
        System.out.print("Are you sure you want to clear entire cart? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("y")) {
            cartService.clearCart();
            undoService.clearUndoStack();
            MenuView.displaySuccess("Cart cleared.");
        }
    }
    
    // ==================== CHECKOUT & EXIT ====================
    
    private void checkout() {
        if (cartService.isCartEmpty()) {
            MenuView.displayError("Cannot checkout - cart is empty!");
            return;
        }
        
        checkoutService.checkout();
    }
    
    private void saveAndExit() {
        FileManager.saveToFile(inventoryService.getAllVehicles());
        MenuView.displaySuccess("Inventory saved.");
    }
    
    // ==================== HELPER METHODS ====================
    
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. " + prompt);
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return input;
    }
    
    private double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. " + prompt);
            scanner.next();
        }
        double input = scanner.nextDouble();
        scanner.nextLine();
        return input;
    }
    
    private boolean getYesNoInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        return input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes");
    }
}