package model;

/**
 * Node class for singly linked list implementation of rental cart
 */
public class CartNode {
    private Rentable vehicle;  // Reference to vehicle being rented
    private int rentalDays;    // Number of rental days
    private CartNode next;     // Pointer to next node in cart
    
    public CartNode(Rentable vehicle, int rentalDays) {
        this.vehicle = vehicle;
        this.rentalDays = rentalDays;
        this.next = null;
    }
    
    public Rentable getVehicle() { return vehicle; }
    public void setVehicle(Rentable vehicle) { this.vehicle = vehicle; }
    
    public int getRentalDays() { return rentalDays; }
    public void setRentalDays(int rentalDays) { this.rentalDays = rentalDays; }
    
    public CartNode getNext() { return next; }
    public void setNext(CartNode next) { this.next = next; }
}