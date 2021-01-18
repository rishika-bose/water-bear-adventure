package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

//represents a Treasure Chest with a list of strings
// describing treasures
public class TreasureChest  {

    //fields
    private List<Treasure> treasureChest;

    //constructor
    public TreasureChest() {
        treasureChest = new ArrayList<>();
    }

    //methods
    public void addTreasure(Treasure treasure) {
        treasureChest.add(treasure);
    }

    public Treasure getTreasure(int index) {
        return treasureChest.get(index);
    }

    public List<Treasure> getListOfTreasures() {
        return treasureChest;
    }

    // EFFECTS: returns size of treasure chest
    public int size() {
        return treasureChest.size();
    }

    public boolean checkIfChestEmpty() {
        return treasureChest.isEmpty();
    }

}
