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
}
