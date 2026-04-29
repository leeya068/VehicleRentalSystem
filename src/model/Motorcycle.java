package model;

/**
 * Motorcycle class - inherits from Vehicle
 * Additional attributes: engineCC, hasSidecar
 */
public class Motorcycle extends Vehicle {
    private int engineCC;
    private boolean hasSidecar;
    
    public Motorcycle(int vehicleId, String model, double dailyRate,
                      String insuranceType, int engineCC, boolean hasSidecar) {
        super(vehicleId, model, dailyRate, insuranceType);
        this.engineCC = engineCC;
        this.hasSidecar = hasSidecar;
    }
    
    @Override
    public double calculateRentalCost(int days) {
        // Motorcycle: dailyRate * days * insurance multiplier
        // Extra $10/day for sidecar, $0.05 per CC for high displacement (>600cc)
        double baseCost = dailyRate * days * getInsuranceMultiplier();
        
        if (hasSidecar) {
            baseCost += (10 * days);
        }
        
        if (engineCC > 600) {
            baseCost += (0.05 * engineCC * days);
        }
        
        return baseCost;
    }
    
    @Override
    public void printVehicleDetails() {
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│ MOTORCYCLE DETAILS                       │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.printf("│ ID: %-37d│\n", vehicleId);
        System.out.printf("│ Model: %-34s│\n", model);
        System.out.printf("│ Daily Rate: $%-30.2f│\n", dailyRate);
        System.out.printf("│ Insurance: %-31s│\n", insuranceType);
        System.out.printf("│ Engine CC: %-31d│\n", engineCC);
        System.out.printf("│ Sidecar: %-33s│\n", hasSidecar ? "Yes" : "No");
        System.out.printf("│ Available: %-31s│\n", available ? "Yes" : "No");
        System.out.println("└─────────────────────────────────────────┘");
    }
    
    @Override
    public String getVehicleType() {
        return "Motorcycle";
    }
    
    // Getters for file saving
    public int getEngineCC() { return engineCC; }
    public boolean hasSidecar() { return hasSidecar; }
}