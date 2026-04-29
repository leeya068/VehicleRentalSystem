package repository;

import model.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Handles file I/O operations for vehicle inventory
 * File format: ID|Model|Type|DailyRate|Insurance|... (type-specific fields)
 */
public class FileManager {
    private static final String DATA_DIR = "data/";
    private static final String FILENAME = DATA_DIR + "vehicles.txt";
    
    /**
     * Load vehicles from file and populate ArrayList
     * Format examples:
     * Car: 1|Toyota Camry|Car|50.0|Premium|4|true
     * Motorcycle: 2|Harley Davidson|Motorcycle|40.0|Basic|883|false
     * Truck: 3|Ford F-150|Truck|80.0|Comprehensive|2.5|true
     */
    public static ArrayList<Rentable> loadFromFile() {
        ArrayList<Rentable> vehicles = new ArrayList<>();
        
        // Ensure data directory exists
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 5) continue;
                
                int id = Integer.parseInt(parts[0]);
                String model = parts[1];
                String type = parts[2];
                double dailyRate = Double.parseDouble(parts[3]);
                String insurance = parts[4];
                
                switch (type) {
                    case "Car":
                        int doors = Integer.parseInt(parts[5]);
                        boolean hasAC = Boolean.parseBoolean(parts[6]);
                        vehicles.add(new Car(id, model, dailyRate, insurance, doors, hasAC));
                        break;
                    case "Motorcycle":
                        int engineCC = Integer.parseInt(parts[5]);
                        boolean hasSidecar = Boolean.parseBoolean(parts[6]);
                        vehicles.add(new Motorcycle(id, model, dailyRate, insurance, engineCC, hasSidecar));
                        break;
                    case "Truck":
                        double cargoCap = Double.parseDouble(parts[5]);
                        boolean isDiesel = Boolean.parseBoolean(parts[6]);
                        vehicles.add(new Truck(id, model, dailyRate, insurance, cargoCap, isDiesel));
                        break;
                }
            }
            System.out.println("[FileManager] Loaded " + vehicles.size() + " vehicles from " + FILENAME);
        } catch (FileNotFoundException e) {
            System.out.println("[FileManager] No existing file found. Starting with empty inventory.");
        } catch (IOException e) {
            System.out.println("[FileManager] Error reading file: " + e.getMessage());
        }
        
        return vehicles;
    }
    
    /**
     * Save current inventory back to file (overwrites)
     */
    public static void saveToFile(ArrayList<Rentable> vehicles) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Rentable v : vehicles) {
                String line = encodeVehicle(v);
                writer.write(line);
                writer.newLine();
            }
            System.out.println("[FileManager] Saved " + vehicles.size() + " vehicles to " + FILENAME);
        } catch (IOException e) {
            System.out.println("[FileManager] Error saving file: " + e.getMessage());
        }
    }
    
    /**
     * Encode vehicle object to file format string
     */
    private static String encodeVehicle(Rentable v) {
        if (v instanceof Car) {
            Car c = (Car) v;
            return String.format("%d|%s|Car|%.2f|%s|%d|%b",
                c.getVehicleId(), c.getModel(), c.getDailyRate(), 
                c.getInsuranceType(), c.getNumberOfDoors(), c.hasAC());
        } else if (v instanceof Motorcycle) {
            Motorcycle m = (Motorcycle) v;
            return String.format("%d|%s|Motorcycle|%.2f|%s|%d|%b",
                m.getVehicleId(), m.getModel(), m.getDailyRate(),
                m.getInsuranceType(), m.getEngineCC(), m.hasSidecar());
        } else if (v instanceof Truck) {
            Truck t = (Truck) v;
            return String.format("%d|%s|Truck|%.2f|%s|%.1f|%b",
                t.getVehicleId(), t.getModel(), t.getDailyRate(),
                t.getInsuranceType(), t.getCargoCapacity(), t.isDiesel());
        }
        return "";
    }
}