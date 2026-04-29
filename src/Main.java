import controller.RentalController;

/**
 * Main entry point for the Vehicle Rental System
 */
public class Main {
    public static void main(String[] args) {
        RentalController controller = new RentalController();
        controller.start();
    }
}