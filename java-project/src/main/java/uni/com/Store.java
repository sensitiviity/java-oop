package uni.com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Manages clothing inventory. Stores unique Clothes instances with quantities.
 * Supports adding (merges duplicates), searching by criteria, printing all.
 */
public class Store {
    private ArrayList<Clothes> clothesList = new ArrayList<>();
    private ArrayList<Integer> quantities = new ArrayList<>();

    public Store() {
    }

    /** @return Unmodifiable list of clothes */
    public ArrayList<Clothes> getClothesList() {
        return clothesList;
    }

    /** @return List of quantities (parallel to clothesList) */
    public ArrayList<Integer> getQuantities() {
        return quantities;
    }

    //adding

    /**
     * Adds or updates clothes quantity. Uses equals() to detect duplicates.
     *
     * @param cl Clothes to add
     * @param quantity Amount (>0)
     * @throws IllegalArgumentException if invalid input
     */
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

    /**
     * Searches clothes matching exact Size.
     *
     * @param size Target size
     * @return Matching clothes (empty list if none)
     */
    public ArrayList<Clothes> searchBySize(Size size) {
        ArrayList<Clothes> result = new ArrayList<>();
        for (Clothes c : clothesList) {
            if (c.getSize() == size) {
                result.add(c);
            }
        }
        return result;
    }

    /**
     * Searches clothes with price <= maxPrice.
     *
     * @param maxPrice Upper price limit
     * @return Matching clothes
     */
    public ArrayList<Clothes> searchByMaxPrice(double maxPrice) {
        ArrayList<Clothes> result = new ArrayList<>();
        for (Clothes c : clothesList) {
            if (c.getPrice() <= maxPrice) {
                result.add(c);
            }
        }
        return result;
    }

    /**
     * Searches clothes with price >= minPrice.
     *
     * @param minPrice Lower price limit
     * @return Matching clothes
     */
    public ArrayList<Clothes> searchByMinPrice(double minPrice) {
        ArrayList<Clothes> result = new ArrayList<>();
        for (Clothes c : clothesList) {
            if (c.getPrice() >= minPrice) {
                result.add(c);
            }
        }
        return result;
    }

    /**
     * Prints all clothes with quantities or "No objects" message.
     */
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

    /**
     * Prints all clothes sorted by name
     */
    public void printSorted() {
        if (clothesList.isEmpty()) {
            System.out.println("No objects to sort.");
            return;
        }

        ArrayList<Clothes> sortedList = new ArrayList<>(clothesList);
        Collections.sort(sortedList);

        System.out.println("\n=== Sorted objects by name ===");
        for (int i = 0; i < sortedList.size(); i++) {
            int qtyIndex = clothesList.indexOf(sortedList.get(i));
            int quantity = (qtyIndex >= 0) ? quantities.get(qtyIndex) : 0;
            System.out.println(sortedList.get(i) + ", quantity=" + quantity);
        }
    }

    /**
     * Sort objects by name
     */
    public void sortByName() {
        if (clothesList.isEmpty()) {
            System.out.println("No objects to sort.");
            return;
        }

        ArrayList<Clothes> sorted = new ArrayList<>(clothesList);

        Comparator<Clothes> cmp = (o1, o2) -> o1.getName().compareTo(o2.getName());

        Collections.sort(sorted, cmp);

        System.out.println("\n=== Sorted by name ===");

        for (Clothes c : sorted) {
            int index = clothesList.indexOf(c);
            int quantity = quantities.get(index);
            System.out.println(c + ", quantity=" + quantity);
        }
    }

    /**
     * Sort objects by price
     */
    public void sortByPrice() {
        if (clothesList.isEmpty()) {
            System.out.println("No objects to sort.");
            return;
        }

        ArrayList<Clothes> sorted = new ArrayList<>(clothesList);

        Comparator<Clothes> cmp = (o1, o2) -> Double.compare(o1.getPrice(), o2.getPrice());

        Collections.sort(sorted, cmp);

        System.out.println("\n=== Sorted by price ===");

        for (Clothes c : sorted) {
            int index = clothesList.indexOf(c);
            int quantity = quantities.get(index);
            System.out.println(c + ", quantity=" + quantity);
        }
    }

    /**
     * Sort objects by size
     */
    public void sortBySize() {

        if (clothesList.isEmpty()) {
            System.out.println("No objects to sort.");
            return;
        }

        ArrayList<Clothes> sorted = new ArrayList<>(clothesList);

        Comparator<Clothes> cmp = (o1, o2) -> o1.getSize().compareTo(o2.getSize());

        Collections.sort(sorted, cmp);

        System.out.println("\n=== Sorted by size ===");

        for (Clothes c : sorted) {
            int index = clothesList.indexOf(c);
            int quantity = quantities.get(index);
            System.out.println(c + ", quantity=" + quantity);
        }
    }

    /**
     * Searches first Clothes by exact UUID match or UUID prefix (first 8 chars).
     * @param uuidStr UUID string or prefix
     * @return Matching Clothes or null
     */
    public Clothes searchByUuid(String uuidStr) {
        if (uuidStr == null || uuidStr.isEmpty()) {
            return null;
        }

        uuidStr = uuidStr.toLowerCase();
        boolean isFullUuid = uuidStr.length() == 36;

        for (Clothes c : clothesList) {
            String cUuid = c.getUuid().toString().toLowerCase();
            if (isFullUuid) {
                if (cUuid.equals(uuidStr)) {
                    return c;
                }
            } else if (cUuid.startsWith(uuidStr)) {
                return c;
            }
        }
        return null;
    }

    //update
    public boolean update(Clothes existingObject, Clothes newObject) {
        if (existingObject == null || newObject == null) {
            return false;
        }

        for (int i = 0; i < clothesList.size(); i++) {
            if (clothesList.get(i).equals(existingObject)) {
                clothesList.set(i, newObject);
                return true;
            }
        }
        return false;
    }

    //delete
    public boolean delete(Clothes existingObject) {
        if (existingObject == null) {
            return false;
        }

        int index = -1;
        for (int i = 0; i < clothesList.size(); i++) {
            if (clothesList.get(i).equals(existingObject)) {
                index = i;
                break;
            }
        }

        if (index >= 0) {
            clothesList.remove(index);
            quantities.remove(index);
            return true;
        }
        return false;
    }
}
