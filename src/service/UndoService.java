package service;

import model.*;

/**
 * Service for undo functionality using custom stack
 * Stack is implemented using singly linked list (from assignment requirement)
 */
public class UndoService {
    private LinkedListStack<CartAction> undoStack;
    private CartService cartService;
    
    public UndoService(CartService cartService) {
        this.undoStack = new LinkedListStack<>();
        this.cartService = cartService;
    }
    
    /**
     * Record an action for potential undo
     */
    public void recordAction(Rentable vehicle, int rentalDays) {
        undoStack.push(new CartAction(vehicle, rentalDays));
    }
    
    /**
     * Undo the last cart addition
     */
    public boolean undoLastAddition() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo - no recent additions.");
            return false;
        }
        
        CartAction lastAction = undoStack.pop();
        Rentable vehicle = lastAction.getVehicle();
        
        // Remove from cart and restore availability
        CartNode removed = cartService.removeFromCart(vehicle.getVehicleId());
        
        if (removed != null) {
            System.out.printf("Undid addition of: %s (%d days)\n", 
                vehicle.getModel(), lastAction.getRentalDays());
            return true;
        } else {
            System.out.println("Undo failed - item not found in cart.");
            return false;
        }
    }
    
    /**
     * Clear undo stack (used after checkout)
     */
    public void clearUndoStack() {
        undoStack.clear();
    }
    
    /**
     * Check if undo is available
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }
    
    /**
     * Get undo stack size
     */
    public int getUndoStackSize() {
        return undoStack.getSize();
    }
}