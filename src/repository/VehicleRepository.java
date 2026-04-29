package repository;

import model.Rentable;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Repository pattern - handles data storage and retrieval
 * Uses ArrayList for random access by index (from Grocery Store assignment)
 */
public class VehicleRepository {
    private ArrayList<Rentable> vehicles;
    
    public VehicleRepository() {
        this.vehicles = new ArrayList<>();
    }
    
    // ========== CREATE ==========
    public void add(Rentable vehicle) {
        vehicles.add(vehicle);
    }
    
    // ========== READ ==========
    public Rentable findById(int id) {
        for (Rentable v : vehicles) {
            if (v.getVehicleId() == id) {
                return v;
            }
        }
        return null;
    }
    
    public ArrayList<Rentable> findByModel(String searchTerm) {
        return vehicles.stream()
            .filter(v -> v.getModel().toLowerCase().contains(searchTerm.toLowerCase()))
            .collect(Collectors.toCollection(ArrayList::new));
    }
    
    public ArrayList<Rentable> findAll() {
        return new ArrayList<>(vehicles);
    }
    
    public boolean existsById(int id) {
        return findById(id) != null;
    }
    
    // ========== UPDATE ==========
    public boolean updateAvailability(int id, boolean available) {
        Rentable vehicle = findById(id);
        if (vehicle != null) {
            vehicle.setAvailable(available);
            return true;
        }
        return false;
    }
    
    // ========== DELETE ==========
    public boolean deleteById(int id) {
        return vehicles.removeIf(v -> v.getVehicleId() == id);
    }
    
    // ========== UTILITY ==========
    public int getSize() {
        return vehicles.size();
    }
    
    public boolean isEmpty() {
        return vehicles.isEmpty();
    }
    
    public void clear() {
        vehicles.clear();
    }
    
    /**
     * Get vehicle with highest rental cost for given days
     * Used for sorting (from University assignment concept)
     */
    public Rentable getVehicleWithHighestCost(int rentalDays) {
        if (vehicles.isEmpty()) return null;
        
        Rentable highest = vehicles.get(0);
        double highestCost = highest.calculateRentalCost(rentalDays);
        
        for (Rentable v : vehicles) {
            double cost = v.calculateRentalCost(rentalDays);
            if (cost > highestCost) {
                highestCost = cost;
                highest = v;
            }
        }
        return highest;
    }
    
    /**
     * Sort vehicles by rental cost (ascending)
     * Similar to sortCoursesByWorkload from University assignment
     */
    public void sortByRentalCost(int rentalDays) {
        vehicles.sort((v1, v2) -> {
            double cost1 = v1.calculateRentalCost(rentalDays);
            double cost2 = v2.calculateRentalCost(rentalDays);
            return Double.compare(cost1, cost2);
        });
    }
}