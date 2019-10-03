//package Efficiency;
import java.util.Scanner;

public class Efficiency {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter vehicle make: ");
        String vehicleMake = in.nextLine();
        System.out.print("Enter vehicle model: ");
        String vehicleModel = in.nextLine();
        System.out.print("Enter kilometres travelled: ");
        Double kiloTravel = in.nextDouble();
        System.out.print("Enter litres of fuel used: ");
        Double fuelUsed = in.nextDouble();
        System.out.printf("Fuel efficiency for a " + vehicleMake + " " + vehicleModel + ": " + "%.2f" +
                " litres/100 km", fuelUsed * 100 / kiloTravel);
    }
}
