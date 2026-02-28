package uni.com;

import java.util.Scanner;

/**
 * Драйвер програми.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of elements: ");
        int n = sc.nextInt();

        Clothes[] clothes = new Clothes[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Element " + (i + 1) + "\n");

            System.out.println("Enter name: ");
            String name = sc.next();
            System.out.println("Enter color: ");
            String color = sc.next();
            System.out.println("Enter size: ");
            String size = sc.next();
            System.out.println("Enter price: ");
            double price = sc.nextDouble();

            clothes[i] = new Clothes(name, color, size, price);
        }

        sc.close();

        System.out.println("=== All elements ===");
        for(Clothes c : clothes){
            System.out.println(c.toString());
        }
    }
}
