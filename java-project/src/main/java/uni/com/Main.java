package uni.com;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Application driver with console menu.
 * Handles invalid input without terminating program.
 */
public class Main {
    /**
     * Starts the console application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Clothes> clothesList = new ArrayList<>();

        while (true) {
            System.out.println("\n1 - Add Pants\n2 - Add Shirts\n3 - Show all\n4 - Exit");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    clothesList.add(createPants(sc));
                    break;
                case "2":
                    clothesList.add(createShirts(sc));
                    break;
                case "3":
                    if (clothesList.isEmpty()) {
                        System.out.println("No objects created.");
                        break;
                    }

                    System.out.println("\n=== All objects ===");

                    for (Clothes c : clothesList) {
                        System.out.println(c);
                    }
                    break;
                case "4":
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Creates a Pants object using console input.
     *
     * @param sc Scanner object for reading user input
     * @return a valid Pants object
     */
    private static Pants createPants(Scanner sc) {
        while (true) {
            try {
                System.out.println("Enter name:");
                String name = sc.nextLine();

                System.out.println("Enter color:");
                String color = sc.nextLine();

                System.out.println("Enter size:");
                Size size;
                try{
                    size = Size.valueOf(sc.nextLine().toUpperCase());
                }catch(IllegalArgumentException e){
                    System.out.println("Invalid size");
                    continue;
                }

                System.out.println("Enter price:");
                double price = Double.parseDouble(sc.nextLine());

                System.out.println("Enter brand:");
                String brand = sc.nextLine();

                System.out.println("Enter material:");
                String material = sc.nextLine();

                System.out.println("Has pockets (true/false):");
                String input = sc.nextLine().toLowerCase();
                if (!input.equals("true") && !input.equals("false")) {
                    System.out.println("Enter true or false");
                    continue;
                }
                boolean hasPockets = Boolean.parseBoolean(input);

                return new Pants(name, color, size, price, brand, material, hasPockets);
            }catch (NumberFormatException   e) {
                System.out.println("Invalid number");
                sc.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Creates a Shirts object using console input.
     *
     * @param sc Scanner object for reading user input
     * @return a valid Shirts object
     */
    private static Shirts createShirts(Scanner sc) {
        while (true) {
            try {
                System.out.println("Enter name:");
                String name = sc.nextLine();

                System.out.println("Enter color:");
                String color = sc.nextLine();

                System.out.println("Enter size:");
                Size size;
                try{
                    size = Size.valueOf(sc.nextLine().toUpperCase());
                }catch(IllegalArgumentException e){
                    System.out.println("Invalid size");
                    continue;
                }

                System.out.println("Enter price:");
                double price = Double.parseDouble(sc.nextLine());

                System.out.println("Enter brand:");
                String brand = sc.nextLine();

                System.out.println("Enter material:");
                String material = sc.nextLine();

                System.out.println("Has long sleeves (true/false):");
                String input = sc.nextLine().toLowerCase();
                if (!input.equals("true") && !input.equals("false")) {
                    System.out.println("Enter true or false");
                    continue;
                }
                boolean longSleeve = Boolean.parseBoolean(sc.nextLine());

                return new Shirts(name, color, size, price, brand, material, longSleeve);
            }catch (NumberFormatException   e) {
                System.out.println("Invalid number");
                sc.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}