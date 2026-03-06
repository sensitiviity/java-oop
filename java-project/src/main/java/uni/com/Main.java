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
     * Starts the console application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
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
                    createObjectMenu(clothesList);
                    break;
                case "3":
                    showAll(clothesList);
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

    //search

    public static void searchMenu(ArrayList<Clothes> clothesList) {
        System.out.println("\nSearch by: ");
        System.out.println("1 - Size");
        System.out.println("2 - Max price");
        System.out.println("3 - Min price");
        System.out.println("0 - Back");

        String choice = sc.nextLine();
        switch (choice) {
            case "1":
                searchBySize(clothesList);
                break;
            case "2":
                searchByMaxPrice(clothesList);
                break;
            case "3":
                searchByMinPrice(clothesList);
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void printSearchResults(ArrayList<Clothes> result) {
        if (result.isEmpty()) {
            System.out.println("No objects found.");
            return;
        }

        for (Clothes c : result) {
            System.out.println(c);
        }
    }

    private static void searchBySize(ArrayList<Clothes> list){
        Size size = readSize();

        ArrayList<Clothes> result = new ArrayList<>();
        for (Clothes c : list) {
            if (c.getSize() == size) {
                result.add(c);
            }
        }
        printSearchResults(result);
    }

    private static void searchByMaxPrice(ArrayList<Clothes> list) {
        double maxPrice = readDouble("Enter max price:");

        ArrayList<Clothes> result = new ArrayList<>();
        for (Clothes c : list) {
            if (c.getPrice() <= maxPrice) {
                result.add(c);
            }
        }
        printSearchResults(result);
    }

    private static void searchByMinPrice(ArrayList<Clothes> list) {
        double minPrice = readDouble("Enter min price:");

        ArrayList<Clothes> result = new ArrayList<>();
        for (Clothes c : list) {
            if (c.getPrice() >= minPrice) {
                result.add(c);
            }
        }
        printSearchResults(result);
    }

    //show

    private static void showAll(ArrayList<Clothes> list){
        if (list.isEmpty()) {
            System.out.println("No objects created.");
            return;
        }

        System.out.println("\n=== All objects ===");
        for (Clothes c : list) {
            System.out.println(c);
        }
    }

    //files

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

    //create (in console)

    /**
     * Displays a menu for creating different types of objects.
     * The created object is added to the collection.
     *
     * @param clothesList collection where objects are stored
     */
    private static void createObjectMenu(ArrayList<Clothes> clothesList) {
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
                    clothesList.add(createPants());
                    return;
                case "2":
                    clothesList.add(createShirts());
                    return;
                case "3":
                    clothesList.add(createTShirt());
                    return;
                case "4":
                    clothesList.add(createJeans());
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

    private static double readDouble(String message) {
        while (true) {
            System.out.println(message);
            try {
                return Double.parseDouble(sc.nextLine());
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid number.");
            }
        }
    }

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

    private static String readString(String message) {
        System.out.println(message);
        return sc.nextLine();
    }

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