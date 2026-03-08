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
    private static final Scanner sc = new Scanner(System.in);
    /**
     * Starts the console application. Loads data from file and runs main menu loop.
     *
     * @param args args [0] = path to db.properties file
     */
    public static void main(String[] args) {
        DatabaseManager db = null;
        if (args.length > 0) {
            try {
                db = new DatabaseManager(args[0]);
                db.connect();
            } catch (Exception e) {
                System.out.println("DB unavailable: " + e.getMessage());
            }
        }

        Store store = new Store();
        loadFromFile(store);

        while (true) {
            System.out.println("\n1 - Search object\n2 - Create new object\n3 - Show all\n4 - Exit");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    searchMenu(store);
                    break;
                case "2":
                    createObjectMenu(store, db);
                    break;
                case "3":
                    store.printAll();
                    break;
                case "4":
                    saveToFile(store);
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    //search

    /**
     * Displays search submenu. Supports search by size, max price, min price.
     *
     * @param store Store instance containing clothes collection
     */
    public static void searchMenu(Store store) {
        System.out.println("\nSearch by: ");
        System.out.println("1 - Size");
        System.out.println("2 - Max price");
        System.out.println("3 - Min price");
        System.out.println("0 - Back");

        String choice = sc.nextLine();
        ArrayList<Clothes> result;
        switch (choice) {
            case "1":
                Size size = readSize();
                result = store.searchBySize(size);
                printSearchResults(result);
                break;
            case "2":
                double maxPrice = readDouble("Enter max price:");
                result = store.searchByMaxPrice(maxPrice);
                printSearchResults(result);
                break;
            case "3":
                double minPrice = readDouble("Enter min price:");
                result = store.searchByMinPrice(minPrice);
                printSearchResults(result);
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid option.");
        }
    }

    /**
     * Prints search results or "No objects found" message.
     *
     * @param result List of matching Clothes objects
     */
    private static void printSearchResults(ArrayList<Clothes> result) {
        if (result.isEmpty()) {
            System.out.println("No objects found.");
            return;
        }

        for (Clothes c : result) {
            System.out.println(c);
        }
    }

    //files

    /**
     * Loads objects from the input file into the collection.
     * Each line of the file is parsed and converted into a corresponding object.
     *
     * @param store collection where created objects will be stored
     */
    public static void loadFromFile(Store store) {
        File file = new File(inputFile);
        System.out.println(file.getAbsolutePath());
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            int count = 0;
            while((line = reader.readLine()) != null) {
                try{
                    Clothes c = parseLine(line);
                    if (c != null) {
                        int quantity = parseQuantity(line);
                        store.addNewClothes(c, quantity);
                        count++;
                    }
                }catch (Exception e){
                    System.out.println("Invalid line: " + line) ;
                }
            }
            String printAfterReading = (count == 1) ? ("In input file was found 1 object.") : ("In input file were found " + count + " objects.");
            System.out.println(printAfterReading);
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    /**
     * Parses single line from input.txt into Clothes subclass instance.
     * Format: type;name;color;size;price;brand;material;[type-specific];quantity (quantity ignored here).
     * Validates field count per type, trims strings.
     *
     * @param line Raw line from file
     * @return Parsed Clothes or null on parse failure
     */
    private static Clothes parseLine(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        String[] parts = line.split(";");

        try {
            String type = parts[0].toLowerCase();
            String name = parts[1];
            String color = parts[2];
            Size size = Size.valueOf(parts[3]);
            double price = Double.parseDouble(parts[4]);
            String brand = parts[5];
            String material = parts[6];

            switch (type) {
                case "pants":
                    if (parts.length < 8) throw new IllegalArgumentException("Pants needs 8 fields");
                    boolean pockets = Boolean.parseBoolean(parts[7]);
                    return new Pants(name, color, size, price, brand, material, pockets);
                case "jeans":
                    if (parts.length < 9) throw new IllegalArgumentException("Jeans needs 9 fields");
                    boolean pocketsJ = Boolean.parseBoolean(parts[7]);
                    boolean ripped = Boolean.parseBoolean(parts[8]);
                    return new Jeans(name, color, size, price, brand, material, pocketsJ, ripped);
                case "shirts":
                    if (parts.length < 8) throw new IllegalArgumentException("Shirts needs 8 fields");
                    boolean sleeve = Boolean.parseBoolean(parts[7]);
                    return new Shirts(name, color, size, price, brand, material, sleeve);
                case "tshirts":
                    if (parts.length < 9) throw new IllegalArgumentException("TShirts needs 9 fields");
                    boolean sleeveT = Boolean.parseBoolean(parts[7]);
                    boolean print = Boolean.parseBoolean(parts[8]);
                    return new TShirts(name, color, size, price, brand, material, sleeveT, print);
                default:
                    throw new IllegalArgumentException("Unknown type: " + type);
            }
        }catch(Exception e){
            System.out.println("Error parsing line: " + line);
        }
        return null;
    }

    /**
     * Extracts quantity from last field of line.
     *
     * @param line Full line from file
     * @return Parsed integer quantity (>0 expected)
     * @throws IllegalArgumentException on parse failure
     */
    private static int parseQuantity(String line) {
        String[] parts = line.split(";");
        try {
            return Integer.parseInt(parts[parts.length - 1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid quantity: " + parts[parts.length - 1]);
        }
    }

    /**
     * Saves all Store contents back to input.txt in parseable format.
     *
     * @param store Source Store with clothes and quantities
     */
    private static void saveToFile(Store store) {
        try(PrintWriter writer = new PrintWriter(new FileWriter(inputFile))){
            for (int i = 0; i < store.getClothesList().size(); i++) {
                Clothes c = store.getClothesList().get(i);
                int qty = store.getQuantities().get(i);
                writer.println(objectsToString(c) + ";" + qty);
            }
            System.out.println("Objects saved to " + inputFile);
        }catch(IOException e){
            System.out.println("Error writing file.");
        }
    }

    /**
     * Converts Clothes to semicolon-separated string for file storage (without quantity).
     *
     * @param c Clothes instance
     * @return Type-prefixed string with all fields
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

    //create (in console)

    /**
     * Interactive menu for creating new clothes instances and adding to Store.
     *
     * @param store Target Store
     */
    private static void createObjectMenu(Store store, DatabaseManager dbManager) {
        while (true) {
            System.out.println("\nChoose object type:");
            System.out.println("1 - Pants");
            System.out.println("2 - Shirts");
            System.out.println("3 - TShirt");
            System.out.println("4 - Jeans");
            System.out.println("0 - Back to main menu");

            String choice = sc.nextLine();

            Clothes created;
            try {
                switch (choice) {
                    case "1":
                        created = createPants();
                        break;
                    case "2":
                        created = createShirts();
                        break;
                    case "3":
                        created = createTShirt();
                        break;
                    case "4":
                        created = createJeans();
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Invalid option.");
                        continue;
                }
                int quantity = readInt("Enter quantity (integer > 0):");
                store.addNewClothes(created, quantity);
                System.out.println("Object added successfully!");

                if (dbManager != null) {
                    try {
                        dbManager.saveClothes(created, quantity);
                    } catch (Exception e) {
                        System.out.println("DB save failed: " + e.getMessage());
                    }
                }
            }catch(Exception e){
                System.out.println("Error creating object.");
            }
        }
    }

    /**
     * Creates a Pants object using console input.
     *
     * @return a valid Pants object
     */
    private static Pants createPants() {
        Clothes d = readData();
        boolean pockets = readBoolean("Has pockets (true/false):");

        return new Pants(d.name, d.color, d.size, d.price, d.brand, d.material, pockets);

    }

    /**
     * Creates a Shirts object using console input.
     *
     * @return a valid Shirts object
     */
    private static Shirts createShirts() {
        Clothes d = readData();
        boolean sleeve = readBoolean("Has long sleeves (true/false):");

        return new Shirts(d.name, d.color, d.size, d.price, d.brand, d.material, sleeve);
    }

    /**
     * Creates a TShirts object using console input.
     *
     * @return a valid TShirts object
     */
    private static TShirts createTShirt() {
        Clothes d = readData();
        boolean sleeve = readBoolean("Has long sleeves:");
        boolean print = readBoolean("Has print:");

        return new TShirts(d.name, d.color, d.size, d.price, d.brand, d.material, sleeve, print);
    }

    /**
     * Creates a Jeans object using console input.
     *
     * @return a valid Jeans object
     */
    private static Jeans createJeans() {
        Clothes d = readData();
        boolean pockets = readBoolean("Has pockets:");
        boolean ripped = readBoolean("Ripped:");

        return new Jeans(d.name, d.color, d.size, d.price, d.brand, d.material, pockets, ripped);
    }

    //additional

    /**
     * Reads a boolean value from the console.
     *
     * @param message question to be shown
     * @return boolean value entered by the user
     */
    private static boolean readBoolean(String message) {
        while (true) {
            System.out.println(message);
            String input = sc.nextLine().toLowerCase();
            if (input.equals("true") || input.equals("false")) {
                return Boolean.parseBoolean(input);
            }
            System.out.println("Enter true or false.");
        }
    }

    /**
     * Reads a double value from the console.
     * Repeats input until a valid number is entered.
     *
     * @param message prompt message for the user
     * @return valid double value
     */
    private static double readDouble(String message) {
        while (true) {
            System.out.println(message);
            try {
                double value = Double.parseDouble(sc.nextLine().trim());
                if (value > 0) return value;
                System.out.println("This field must be > 0.");
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid number.");
            }
        }
    }

    /**
     * Reads a valid Size enum value from the console.
     * Repeats input until a correct size is entered.
     *
     * @return selected size value
     */
    private static Size readSize() {
        while (true) {
            System.out.println("Enter size (XXS XS S M L XL XXL):");
            try {
                return Size.valueOf(sc.nextLine().toUpperCase());
            }
            catch (IllegalArgumentException e) {
                System.out.println("Invalid size.");
            }
        }
    }

    /**
     * Loops until valid positive integer.
     *
     * @param message prompt message for the user
     * @return Positive int
     */
    private static int readInt(String message) {
        while (true) {
            System.out.println(message);
            try {
                int value = Integer.parseInt(sc.nextLine().trim());
                if (value > 0) return value;
                System.out.println("This field must be > 0.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer.");
            }
        }
    }

    /**
     * Reads a string value from the console.
     *
     * @param message prompt message
     * @return entered string
     */
    private static String readString(String message) {
        System.out.println(message);
        return sc.nextLine();
    }

    /**
     * Reads basic Clothes data from the console.
     * Used when creating any type of clothes object.
     *
     * @return Clothes object containing base attributes
     */
    private static Clothes readData() {
        String name = readString("Enter name:");
        String color = readString("Enter color:");
        Size size = readSize();
        double price = readDouble("Enter price:");
        String brand = readString("Enter brand:");
        String material = readString("Enter material:");

        return new Clothes(name, color, size, price, brand, material);
    }
}