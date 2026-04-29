package service;

import model.*;

/**
 * Service layer for shopping cart business logic
 * Handles cart operations with validation and side effects
 */
public class CartService {
    private RentalCart cart;
    private InventoryService inventoryService;
    
    public CartService(InventoryService inventoryService) {
        this.cart = new RentalCart();
        this.inventoryService = inventoryService;
    }
    
    /**
     * Add item to cart with availability check
     */
    public boolean addToCart(int vehicleId, int rentalDays) {
        // Validate rental days
        if (rentalDays <= 0) {
            System.out.println("Rental days must be positive.");
            return false;
        }
        
        // Check availability in inventory
        if (!inventoryService.isAvailableForRental(vehicleId)) {
            System.out.println("Vehicle is not available for rent.");
            return false;
        }
        
        Rentable vehicle = inventoryService.searchById(vehicleId);
        
        // Check if already in cart (update instead)
        CartNode existing = cart.findItem(vehicleId);
        if (existing != null) {
            cart.updateItemDays(vehicleId, rentalDays);
            System.out.println("Updated rental days for: " + vehicle.getModel());
            return true;
        }
        
        // Add to cart
        cart.addItem(vehicle, rentalDays);
        
        // Temporarily reduce stock (set availability to false)
        inventoryService.updateAvailability(vehicleId, false);
        
        System.out.println("Added to cart: " + vehicle.getModel() + " for " + rentalDays + " days");
        return true;
    }
    
    /**
     * Remove item from cart and restore availability
     */
    public CartNode removeFromCart(int vehicleId) {
        CartNode removed = cart.removeItem(vehicleId);
        if (removed != null) {
            // Restore availability in inventory
            inventoryService.updateAvailability(vehicleId, true);
            System.out.println("Removed from cart: " + removed.getVehicle().getModel());
        } else {
            System.out.println("Vehicle not found in cart.");
        }
        return removed;
    }
    
    /**
     * Update rental days for item in cart
     */
    public boolean updateRentalDays(int vehicleId, int newDays) {
        if (newDays <= 0) {
            System.out.println("Rental days must be positive.");
            return false;
        }
        
        boolean updated = cart.updateItemDays(vehicleId, newDays);
        if (updated) {
            System.out.println("Updated rental days for vehicle ID: " + vehicleId);
        } else {
            System.out.println("Vehicle not found in cart.");
        }
        return updated;
    }
    
    /**
     * Clear entire cart and restore all availability
     */
    public void clearCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is already empty.");
            return;
        }
        
        // Restore all vehicles' availability
        CartNode[] items = cart.getAllItems();
        for (CartNode item : items) {
            inventoryService.updateAvailability(item.getVehicle().getVehicleId(), true);
        }
        
        cart.clear();
        System.out.println("Cart cleared. All vehicles restored to inventory.");
    }
    
    /**
     * Get cart object (for undo operations)
     */
    public RentalCart getCart() {
        return cart;
    }
    
    /**
     * Check if cart is empty
     */
    public boolean isCartEmpty() {
        return cart.isEmpty();
    }
    
    /**
     * Get cart size
     */
    public int getCartSize() {
        return cart.getSize();
    }
    
    /**
     * Find item in cart
     */
    public CartNode findInCart(int vehicleId) {
        return cart.findItem(vehicleId);
    }
    
    /**
     * Get all cart items
     */
    public CartNode[] getAllCartItems() {
        return cart.getAllItems();
    }
}