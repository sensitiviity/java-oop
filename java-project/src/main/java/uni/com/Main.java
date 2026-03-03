package uni.com;

import java.util.Scanner;

/**
 * Application driver with console menu.
 * Handles invalid input without terminating program.
 */
public class Main {
    /**
     * Application entry point.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Wardrobe wardrobe = null;

        while (true) {
            System.out.println("\n1 - Create new object\n2 - Show all\n3 - Exit");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    while (true) {
                        try {
                            System.out.println("Enter name: ");
                            String name = sc.nextLine();
                            System.out.println("Enter color: ");
                            String color = sc.nextLine();
                            System.out.println("Enter size (XXS, XS, S, M, L, XL, XXL): ");
                            Size size;
                            try{
                                size = Size.valueOf(sc.nextLine().toUpperCase());
                            }catch(IllegalArgumentException e){
                                System.out.println("Invalid size");
                                continue;
                            }
                            System.out.println("Enter price: ");
                            double price = Double.parseDouble(sc.nextLine());;
                            System.out.println("Enter brand: ");
                            String brand = sc.nextLine();
                            System.out.println("Enter material: ");
                            String material = sc.nextLine();

                            Clothes newClothes = new Clothes(name, color, size, price, brand, material);
                            if (wardrobe == null) {
                                wardrobe = new Wardrobe(new Clothes[]{newClothes});
                            } else {
                                wardrobe.addClothes(newClothes);
                            }
                            System.out.println("Objects created: " + Clothes.getObjectCount());

                            break;
                        } catch (NumberFormatException   e) {
                            System.out.println("Invalid number");
                            sc.nextLine();
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                case "2":
                    if(wardrobe == null) {
                        System.out.println("No objects created");
                        break;
                    }

                    System.out.println("\n=== All elements ===");
                    wardrobe.display();
                    break;
                case "3":
                    sc.close();
                    return;
                default:
                    System.out.println("Wrong menu number\n");
            }
        }
    }
}
