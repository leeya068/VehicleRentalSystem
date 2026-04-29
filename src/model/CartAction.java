package model;

/**
 * Stores information about a cart addition for undo functionality
 * Each time a vehicle is added to cart, we push a CartAction onto stack
 */
public class CartAction {
    private Rentable vehicle;
    private int rentalDays;
    
    public CartAction(Rentable vehicle, int rentalDays) {
        this.vehicle = vehicle;
        this.rentalDays = rentalDays;
    }
    
    public Rentable getVehicle() { return vehicle; }
    public int getRentalDays() { return rentalDays; }
}