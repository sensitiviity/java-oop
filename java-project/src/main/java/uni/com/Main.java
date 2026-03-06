package uni.com;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * Application driver with console menu.
 * Handles invalid input without terminating program.
 */
public class Main {
    private static final String inputFile = "./java-project/./input.txt";
    /**
     * Starts the console application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Clothes> clothesList = new ArrayList<>();
        loadFromFile(clothesList);

        while (true) {
            System.out.println("\n1 - Search object\n2 - Create new object\n3 - Show all\n4 - Exit");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    searchMenu(clothesList);
                    break;
                case "2":
                    createObjectMenu(sc, clothesList);
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
                    saveToFile(clothesList);
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    public static void searchMenu(ArrayList<Clothes> clothesList) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nSearch by: ");
        System.out.println("1 - Size");
        System.out.println("2 - Max price");
        System.out.println("3 - Brand");
        System.out.println("4 - Back");

        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1:
                System.out.println("Enter size: ");
                Size size = Size.valueOf(sc.nextLine().toUpperCase());
                break;
            case 2:
                System.out.println("Enter max price: ");
                double maxPrice = sc.nextDouble();
                break;
            case 3:
                System.out.println("Enter min price");
                double minPrise = sc.nextDouble();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid option.");
        }
    }

    /**
     * Loads objects from the input file into the collection.
     * Each line of the file is parsed and converted into a corresponding object.
     *
     * @param clothesList collection where created objects will be stored
     */
    public static void loadFromFile(ArrayList<Clothes> clothesList) {
        File file = new File(inputFile);
        System.out.println(file.getAbsolutePath());
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine()) != null) {
                try{
                    Clothes c = parseLine(line);
                    if(c != null) {
                        clothesList.add(c);
                    }
                }catch (Exception e){
                    System.out.println("Invalid line: " + line) ;
                }
            }
            String printAfterReading = (clothesList.size() == 1) ? ("In input file was found 1 object.") : ("In input file were found " + clothesList.size() + " objects.");
            System.out.println(printAfterReading);
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    /**
     * Parses a line from the input file and creates the corresponding object.
     * Expected format:
     * type;name;color;size;price;brand;material;[specific attributes]
     *
     * @param line one line from the input file
     * @return created Clothes object or null if type is unknown
     */
    private static Clothes parseLine(String line) {
        String[] parts = line.split(";");

        String type = parts[0].toLowerCase();
        String name = parts[1];
        String color = parts[2];
        Size size = Size.valueOf(parts[3]);
        double price = Double.parseDouble(parts[4]);
        String brand = parts[5];
        String material = parts[6];

        switch (type) {
            case "pants":
                boolean pockets = Boolean.parseBoolean(parts[7]);
                return new Pants(name, color, size, price, brand, material, pockets);
            case "jeans":
                boolean pocketsJ = Boolean.parseBoolean(parts[7]);
                boolean ripped = Boolean.parseBoolean(parts[8]);
                return new Jeans(name, color, size, price, brand, material, pocketsJ, ripped);
            case "shirts":
                boolean sleeve = Boolean.parseBoolean(parts[7]);
                return new Shirts(name, color, size, price, brand, material, sleeve);
            case "tshirts":
                boolean sleeveT = Boolean.parseBoolean(parts[7]);
                boolean print = Boolean.parseBoolean(parts[8]);
                return new TShirts(name, color, size, price, brand, material, sleeveT, print);
        }
        return null;
    }

    /**
     * Saves all objects from the collection to the input file.
     *
     * @param clothesList collection of clothes objects
     */
    private static void saveToFile(ArrayList<Clothes> clothesList) {
        try(PrintWriter writer = new PrintWriter(new FileWriter(inputFile))){
            for (Clothes c : clothesList) {
                writer.println(objectsToString(c));
            }
            System.out.println("Objects saved to " + inputFile);
        }catch(IOException e){
            System.out.println("Error writing file.");
        }
    }

    /**
     * Converts an object into a string representation for file storage.
     * The format depends on the specific object type.
     *
     * @param c clothes object
     * @return formatted string for writing into the file
     */
    private static String objectsToString(Clothes c) {
        if (c instanceof Jeans) {
            Jeans j = (Jeans) c;
            return "Jeans;" + j.getName() + ";" + j.getColor() + ";" + j.getSize() + ";" + j.getPrice() + ";" + j.getBrand() + ";" + j.getMaterial() + ";" + j.getHasPockets() + ";" + j.isRipped();
        } else if (c instanceof Pants) {
            Pants p = (Pants) c;
            return "Pants;" + p.getName() + ";" + p.getColor() + ";" + p.getSize() + ";" + p.getPrice() + ";" + p.getBrand() + ";" + p.getMaterial() + ";" + p.getHasPockets();
        } else if (c instanceof TShirts) {
            TShirts ts = (TShirts) c;
            return "TShirts;" + ts.getName() + ";" + ts.getColor() + ";" + ts.getSize() + ";" + ts.getPrice() + ";" + ts.getBrand() + ";" + ts.getMaterial() + ";" + ts.getLongSleeve() + ";" + ts.getHasPrints();
        } else if (c instanceof Shirts) {
            Shirts s = (Shirts) c;
            return "Shirts;" + s.getName() + ";" + s.getColor() + ";" + s.getSize() + ";" + s.getPrice() + ";" + s.getBrand() + ";" + s.getMaterial() + ";" + s.getLongSleeve();
        }
        return "";
    }

    /**
     * Displays a menu for creating different types of objects.
     * The created object is added to the collection.
     *
     * @param sc scanner used for user input
     * @param clothesList collection where objects are stored
     */
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

    /**
     * Creates a TShirts object using console input.
     *
     * @param sc Scanner object for reading user input
     * @return a valid TShirts object
     */
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

    /**
     * Creates a Jeans object using console input.
     *
     * @param sc Scanner object for reading user input
     * @return a valid Jeans object
     */
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

    /**
     * Reads a boolean value from the console.
     *
     * @param sc scanner used for input
     * @return boolean value entered by the user
     */
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