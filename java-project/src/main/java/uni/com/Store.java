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
}
