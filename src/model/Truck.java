package model;

/**
 * Truck class - inherits from Vehicle
 * Additional attributes: cargoCapacity (tons), isDiesel
 */
public class Truck extends Vehicle {
    private double cargoCapacity;
    private boolean isDiesel;
    
    public Truck(int vehicleId, String model, double dailyRate,
                 String insuranceType, double cargoCapacity, boolean isDiesel) {
        super(vehicleId, model, dailyRate, insuranceType);
        this.cargoCapacity = cargoCapacity;
        this.isDiesel = isDiesel;
    }
    
    @Override
    public double calculateRentalCost(int days) {
        // Truck: dailyRate * days * insurance multiplier
        // Extra $20/day for diesel, $15 per ton of cargo capacity
        double baseCost = dailyRate * days * getInsuranceMultiplier();
        
        if (isDiesel) {
            baseCost += (20 * days);
        }
        
        baseCost += (cargoCapacity * 15 * days);
        
        return baseCost;
    }
    
    @Override
    public void printVehicleDetails() {
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│ TRUCK DETAILS                            │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.printf("│ ID: %-37d│\n", vehicleId);
        System.out.printf("│ Model: %-34s│\n", model);
        System.out.printf("│ Daily Rate: $%-30.2f│\n", dailyRate);
        System.out.printf("│ Insurance: %-31s│\n", insuranceType);
        System.out.printf("│ Cargo Capacity: %-26.1f tons│\n", cargoCapacity);
        System.out.printf("│ Diesel: %-34s│\n", isDiesel ? "Yes" : "No");
        System.out.printf("│ Available: %-31s│\n", available ? "Yes" : "No");
        System.out.println("└─────────────────────────────────────────┘");
    }
    
    @Override
    public String getVehicleType() {
        return "Truck";
    }
    
    // Getters for file saving
    public double getCargoCapacity() { return cargoCapacity; }
    public boolean isDiesel() { return isDiesel; }
}