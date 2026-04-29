package model;

/**
 * ADT (Abstract Data Type) interface for all rentable vehicles.
 * Defines the contract that all vehicle types must follow.
 */
public interface Rentable {
    
    /**
     * Returns the unique vehicle ID
     * @return vehicle ID as integer
     */
    int getVehicleId();
    
    /**
     * Returns the vehicle model/brand name
     * @return vehicle name as String
     */
    String getModel();
    
    /**
     * Calculates rental cost for given number of days
     * Each vehicle type implements this differently
     * @param days number of rental days
     * @return total rental cost as double
     */
    double calculateRentalCost(int days);
    
    /**
     * Returns insurance type (Basic, Premium, Comprehensive)
     * @return insurance type as String
     */
    String getInsuranceType();
    
    /**
     * Returns vehicle's availability status
     * @return true if available, false if rented
     */
    boolean isAvailable();
    
    /**
     * Sets vehicle availability
     * @param available new availability status
     */
    void setAvailable(boolean available);
    
    /**
     * Prints all vehicle details to console
     */
    void printVehicleDetails();
    
    /**
     * Returns daily rental rate (base rate before insurance)
     * @return daily rate as double
     */
    double getDailyRate();
    
    /**
     * Returns vehicle type as string (Car, Motorcycle, Truck)
     */
    String getVehicleType();
}