package model;

/**
 * Abstract base class implementing common vehicle attributes
 * Reduces code duplication across Car, Motorcycle, Truck
 */
public abstract class Vehicle implements Rentable {
    protected int vehicleId;
    protected String model;
    protected double dailyRate;
    protected String insuranceType;
    protected boolean available;
    
    public Vehicle(int vehicleId, String model, double dailyRate, String insuranceType) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.dailyRate = dailyRate;
        this.insuranceType = insuranceType;
        this.available = true;
    }
    
    @Override
    public int getVehicleId() { return vehicleId; }
    
    @Override
    public String getModel() { return model; }
    
    @Override
    public String getInsuranceType() { return insuranceType; }
    
    @Override
    public boolean isAvailable() { return available; }
    
    @Override
    public void setAvailable(boolean available) { this.available = available; }
    
    @Override
    public double getDailyRate() { return dailyRate; }
    
    /**
     * Helper method to calculate insurance multiplier
     * Basic: 1.0, Premium: 1.3, Comprehensive: 1.6
     */
    protected double getInsuranceMultiplier() {
        switch (insuranceType.toLowerCase()) {
            case "premium": return 1.3;
            case "comprehensive": return 1.6;
            default: return 1.0; // Basic
        }
    }
    
    // Common toString for debugging
    @Override
    public String toString() {
        return String.format("[%d] %s - $%.2f/day (%s)", 
            vehicleId, model, dailyRate, available ? "Available" : "Rented");
    }
}