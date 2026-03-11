import java.util.*;
public class week2 {
    static int SIZE = 500;
    static String[] spots = new String[SIZE];
    static long[] entryTime = new long[SIZE];
    public static int hash(String plate) {
        return Math.abs(plate.hashCode()) % SIZE;
    }
    public static void parkVehicle(String plate) {
        int index = hash(plate);
        int probes = 0;
        while(spots[index] != null) {
            index = (index + 1) % SIZE;
            probes++;
        }
        spots[index] = plate;
        entryTime[index] = System.currentTimeMillis();
        System.out.println("Assigned spot #" + index + " (" + probes + " probes)");
    }
    public static void exitVehicle(String plate) {
        for(int i=0;i<SIZE;i++) {
            if(plate.equals(spots[i])) {
                long duration = (System.currentTimeMillis() - entryTime[i]) / 1000;
                double fee = duration * 0.05;
                spots[i] = null;
                System.out.println("Spot #" + i + " freed");
                System.out.println("Duration: " + duration + " seconds");
                System.out.println("Fee: $" + fee);
                return;
            }
        }
        System.out.println("Vehicle not found");
    }
    public static void getStatistics() {
        int occupied = 0;
        for(String s : spots) {
            if(s != null) occupied++;
        }
        double occupancy = (occupied * 100.0) / SIZE;
        System.out.println("Occupancy: " + occupancy + "%");
        System.out.println("Occupied spots: " + occupied);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("\n1.Park Vehicle");
            System.out.println("2.Exit Vehicle");
            System.out.println("3.Get Statistics");
            System.out.println("4.Exit");
            System.out.print("Choice: ");
            int ch = sc.nextInt();
            sc.nextLine();
            if(ch == 1) {
                System.out.print("Enter License Plate: ");
                String plate = sc.nextLine();
                parkVehicle(plate);
            }
            else if(ch == 2) {
                System.out.print("Enter License Plate: ");
                String plate = sc.nextLine();
                exitVehicle(plate);
            }
            else if(ch == 3) {
                getStatistics();
            }
            else {
                break;
            }
        }
    }
}