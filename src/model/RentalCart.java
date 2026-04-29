package model;

/**
 * Self-implemented singly linked list for rental cart
 * No java.util.LinkedList allowed - pure custom implementation
 * This is a MODEL class - handles data structure only, no business logic
 */
public class RentalCart {
    private CartNode head;
    private int size;
    
    public RentalCart() {
        this.head = null;
        this.size = 0;
    }
    
    /**
     * Add vehicle to cart (adds to end of list)
     */
    public void addItem(Rentable vehicle, int rentalDays) {
        CartNode newNode = new CartNode(vehicle, rentalDays);
        
        if (head == null) {
            head = newNode;
        } else {
            CartNode current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        size++;
    }
    
    /**
     * Update existing item's rental days
     */
    public boolean updateItemDays(int vehicleId, int newDays) {
        CartNode node = findItem(vehicleId);
        if (node != null) {
            node.setRentalDays(newDays);
            return true;
        }
        return false;
    }
    
    /**
     * Remove item from cart by vehicle ID
     * @return the removed node, or null if not found
     */
    public CartNode removeItem(int vehicleId) {
        if (head == null) return null;
        
        if (head.getVehicle().getVehicleId() == vehicleId) {
            CartNode removed = head;
            head = head.getNext();
            size--;
            return removed;
        }
        
        CartNode current = head;
        while (current.getNext() != null && 
               current.getNext().getVehicle().getVehicleId() != vehicleId) {
            current = current.getNext();
        }
        
        if (current.getNext() != null) {
            CartNode removed = current.getNext();
            current.setNext(removed.getNext());
            size--;
            return removed;
        }
        
        return null;
    }
    
    /**
     * Find item in cart by vehicle ID
     */
    public CartNode findItem(int vehicleId) {
        CartNode current = head;
        while (current != null) {
            if (current.getVehicle().getVehicleId() == vehicleId) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }
    
    /**
     * Clear entire cart
     */
    public void clear() {
        head = null;
        size = 0;
    }
    
    /**
     * Check if cart is empty
     */
    public boolean isEmpty() {
        return head == null;
    }
    
    /**
     * Get cart size
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Get head node (for iteration)
     */
    public CartNode getHead() {
        return head;
    }
    
    /**
     * Get all items as array (for easy iteration)
     */
    public CartNode[] getAllItems() {
        CartNode[] items = new CartNode[size];
        CartNode current = head;
        int index = 0;
        while (current != null) {
            items[index++] = current;
            current = current.getNext();
        }
        return items;
    }
}