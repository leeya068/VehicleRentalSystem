package model;

/**
 * Car class - inherits from Vehicle
 * Additional attributes: numberOfDoors, hasAC
 */
public class Car extends Vehicle {
    private int numberOfDoors;
    private boolean hasAC;
    
    public Car(int vehicleId, String model, double dailyRate, 
               String insuranceType, int numberOfDoors, boolean hasAC) {
        super(vehicleId, model, dailyRate, insuranceType);
        this.numberOfDoors = numberOfDoors;
        this.hasAC = hasAC;
    }
    
    @Override
    public double calculateRentalCost(int days) {
        // Car: dailyRate * days * insurance multiplier
        // Additional $5 per day for AC if present
        double baseCost = dailyRate * days * getInsuranceMultiplier();
        if (hasAC) {
            baseCost += (5 * days);
        }
        return baseCost;
    }
    
    @Override
    public void printVehicleDetails() {
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│ CAR DETAILS                             │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.printf("│ ID: %-37d│\n", vehicleId);
        System.out.printf("│ Model: %-34s│\n", model);
        System.out.printf("│ Daily Rate: $%-30.2f│\n", dailyRate);
        System.out.printf("│ Insurance: %-31s│\n", insuranceType);
        System.out.printf("│ Doors: %-35d│\n", numberOfDoors);
        System.out.printf("│ AC: %-37s│\n", hasAC ? "Yes" : "No");
        System.out.printf("│ Available: %-31s│\n", available ? "Yes" : "No");
        System.out.println("└─────────────────────────────────────────┘");
    }
    
    @Override
    public String getVehicleType() {
        return "Car";
    }
    
    // Getters for file saving
    public int getNumberOfDoors() { return numberOfDoors; }
    public boolean hasAC() { return hasAC; }
}