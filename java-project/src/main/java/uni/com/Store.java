package uni.com;

import java.util.ArrayList;

public class Store {
    private ArrayList<Clothes> clothesList = new ArrayList<>();
    private ArrayList<Integer> quantities = new ArrayList<>();

    public Store() {
    }

    public ArrayList<Clothes> getClothesList() {
        return clothesList;
    }

    public ArrayList<Integer> getQuantities() {
        return quantities;
    }

    //adding

    public void addNewClothes(Clothes cl, int quantity) {
        if (cl == null || quantity <= 0) {
            throw new IllegalArgumentException("Clothes is null or quantity <= 0");
        }

        int index = -1;
        for (int i = 0; i < clothesList.size(); i++) {
            if (clothesList.get(i).equals(cl)) {
                index = i;
                break;
            }
        }

        if (index >= 0) {
            int oldQ = quantities.get(index);
            quantities.set(index, oldQ + quantity);
        } else {
            clothesList.add(cl);
            quantities.add(quantity);
        }
    }

    //search

    public ArrayList<Clothes> searchBySize(Size size) {
        ArrayList<Clothes> result = new ArrayList<>();
        for (Clothes c : clothesList) {
            if (c.getSize() == size) {
                result.add(c);
            }
        }
        return result;
    }

    public ArrayList<Clothes> searchByMaxPrice(double maxPrice) {
        ArrayList<Clothes> result = new ArrayList<>();
        for (Clothes c : clothesList) {
            if (c.getPrice() <= maxPrice) {
                result.add(c);
            }
        }
        return result;
    }

    public ArrayList<Clothes> searchByMinPrice(double minPrice) {
        ArrayList<Clothes> result = new ArrayList<>();
        for (Clothes c : clothesList) {
            if (c.getPrice() >= minPrice) {
                result.add(c);
            }
        }
        return result;
    }

    public void printAll() {
        if (clothesList.isEmpty()) {
            System.out.println("No objects created.");
            return;
        }
        System.out.println("\n=== All objects in store ===");
        for (int i = 0; i < clothesList.size(); i++) {
            System.out.println(clothesList.get(i) + ", quantity=" + quantities.get(i));
        }
    }
}
