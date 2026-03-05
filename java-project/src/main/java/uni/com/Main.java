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
            System.out.println("\n1 - Create new object\n2 - Show all\n3 - Exit");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    createObjectMenu(sc, clothesList);
                    break;
                case "2":
                    if (clothesList.isEmpty()) {
                        System.out.println("No objects created.");
                        break;
                    }

                    System.out.println("\n=== All objects ===");
                    for (Clothes c : clothesList) {
                        System.out.println(c);
                    }
                    break;
                case "3":
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void createObjectMenu(Scanner sc, ArrayList<Clothes> clothesList) {
        while (true) {
            System.out.println("\nChoose object type:");
            System.out.println("1 - Pants");
            System.out.println("2 - Shirts");
            System.out.println("3 - TShirt");
            System.out.println("4 - Jeans");
            System.out.println("0 - Back to main menu");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    clothesList.add(createPants(sc));
                    return;
                case "2":
                    clothesList.add(createShirts(sc));
                    return;
                case "3":
                    clothesList.add(createTShirt(sc));
                    return;
                case "4":
                    clothesList.add(createJeans(sc));
                    return;
                case "0":
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
                boolean hasPockets = readBoolean(sc);

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
                boolean longSleeve = readBoolean(sc);

                return new Shirts(name, color, size, price, brand, material, longSleeve);
            }catch (NumberFormatException   e) {
                System.out.println("Invalid number");
                sc.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static TShirts createTShirt(Scanner sc) {
        while (true) {
            try {
                System.out.println("Enter name:");
                String name = sc.nextLine();

                System.out.println("Enter color:");
                String color = sc.nextLine();

                System.out.println("Enter size:");
                Size size = Size.valueOf(sc.nextLine().toUpperCase());

                System.out.println("Enter price:");
                double price = Double.parseDouble(sc.nextLine());

                System.out.println("Enter brand:");
                String brand = sc.nextLine();

                System.out.println("Enter material:");
                String material = sc.nextLine();

                System.out.println("Has long sleeves (true/false):");
                boolean longSleeve = readBoolean(sc);

                System.out.println("Has print (true/false):");
                boolean hasPrint = readBoolean(sc);

                return new TShirts(name, color, size, price, brand, material, longSleeve, hasPrint);

            } catch (Exception e) {
                System.out.println("Invalid input.");
            }
        }
    }

    private static Jeans createJeans(Scanner sc) {
        while (true) {
            try {
                System.out.println("Enter name:");
                String name = sc.nextLine();

                System.out.println("Enter color:");
                String color = sc.nextLine();

                System.out.println("Enter size:");
                Size size = Size.valueOf(sc.nextLine().toUpperCase());

                System.out.println("Enter price:");
                double price = Double.parseDouble(sc.nextLine());

                System.out.println("Enter brand:");
                String brand = sc.nextLine();

                System.out.println("Enter material:");
                String material = sc.nextLine();

                System.out.println("Has pockets (true/false):");
                boolean hasPockets = readBoolean(sc);

                System.out.println("Ripped (true/false):");
                boolean ripped = readBoolean(sc);

                return new Jeans(name, color, size, price, brand, material, hasPockets, ripped);

            } catch (Exception e) {
                System.out.println("Invalid input.");
            }
        }
    }

    private static boolean readBoolean(Scanner sc) {
        while (true) {
            String input = sc.nextLine().toLowerCase();
            if (input.equals("true") || input.equals("false")) {
                return Boolean.parseBoolean(input);
            }
            System.out.println("Enter true or false:");
        }
    }
}