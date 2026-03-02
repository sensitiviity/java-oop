package uni.com;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Driver class of the application.
 * Provides console menu for: creating Clothes objects, displaying stored objects and terminating program.
 * All user input errors are handled without terminating the program.
 */
public class Main {
    /**
     * Application entry point.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Clothes[] clothes = null;
        Wardrobe wardrobe = null;
        int count = 0;

        while (true) {
            System.out.println("\n1 - Create new object\n2 - Show all\n3 - Exit");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    int n;

                    while (true) {
                        try {
                            System.out.println("Enter number of elements: ");
                            n = sc.nextInt();

                            if (n <= 0) throw new IllegalArgumentException("Number must be bigger than 0");
                            sc.nextLine();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid number");
                            sc.nextLine();
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    clothes = new Clothes[n];
                    count = n;

                    for (int i = 0; i < n; i++) {
                        while (true) {
                            try {
                                System.out.println("\nElement " + (i + 1));

                                System.out.println("Enter name: ");
                                String name = sc.next();
                                System.out.println("Enter color: ");
                                String color = sc.next();
                                System.out.println("Enter size (XXS, XS, S, M, L, XL, XXL): ");
                                Size size;
                                try{
                                    size = Size.valueOf(sc.next().toUpperCase());
                                }catch(IllegalArgumentException e){
                                    System.out.println("Invalid size");
                                    continue;
                                }
                                System.out.println("Enter price: ");
                                double price = sc.nextDouble();
                                System.out.println("Enter brand: ");
                                String brand = sc.next();
                                System.out.println("Enter material: ");
                                String material = sc.next();

                                sc.nextLine();
                                clothes[i] = new Clothes(name, color, size, price, brand, material);
                                System.out.println("Objects created: " + Clothes.getObjectCount());
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input");
                                sc.nextLine();
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    wardrobe = new Wardrobe(clothes);
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
