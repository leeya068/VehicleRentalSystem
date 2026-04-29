package service;

import model.Rentable;
import repository.VehicleRepository;
import java.util.ArrayList;

/**
 * Service layer for inventory business logic
 * Handles operations that require business rules, not just CRUD
 */
public class InventoryService {
    private VehicleRepository repository;
    
    public InventoryService(VehicleRepository repository) {
        this.repository = repository;
    }
    
    /**
     * Add vehicle with duplicate ID check
     */
    public boolean addVehicle(Rentable vehicle) {
        if (repository.existsById(vehicle.getVehicleId())) {
            System.out.println("Error: Vehicle ID " + vehicle.getVehicleId() + " already exists!");
            return false;
        }
        repository.add(vehicle);
        System.out.println("Added vehicle: " + vehicle.getModel());
        return true;
    }
    
    /**
     * Remove vehicle if not currently rented
     */
    public boolean removeVehicle(int id) {
        Rentable vehicle = repository.findById(id);
        if (vehicle == null) {
            System.out.println("Vehicle ID " + id + " not found.");
            return false;
        }
        
        if (!vehicle.isAvailable()) {
            System.out.println("Cannot remove - vehicle is currently rented!");
            return false;
        }
        
        repository.deleteById(id);
        System.out.println("Removed vehicle ID: " + id);
        return true;
    }
    
    /**
     * Search vehicle by ID
     */
    public Rentable searchById(int id) {
        return repository.findById(id);
    }
    
    /**
     * Search vehicles by model (partial match)
     */
    public ArrayList<Rentable> searchByModel(String searchTerm) {
        return repository.findByModel(searchTerm);
    }
    
    /**
     * Get all vehicles
     */
    public ArrayList<Rentable> getAllVehicles() {
        return repository.findAll();
    }
    
    /**
     * Check if vehicle is available for rental
     */
    public boolean isAvailableForRental(int id) {
        Rentable vehicle = repository.findById(id);
        return vehicle != null && vehicle.isAvailable();
    }
    
    /**
     * Update vehicle availability
     */
    public void updateAvailability(int id, boolean available) {
        repository.updateAvailability(id, available);
    }
    
    /**
     * Get vehicle with highest rental cost (7 days default)
     */
    public Rentable getMostExpensiveVehicle(int rentalDays) {
        return repository.getVehicleWithHighestCost(rentalDays);
    }
    
    /**
     * Sort vehicles by rental cost
     */
    public void sortVehiclesByCost(int rentalDays) {
        repository.sortByRentalCost(rentalDays);
    }
}