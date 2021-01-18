package model;

import exception.NegativeEnergyException;
import exception.OutOfMapException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;

// represents the Water Bear that the user will play as, with an energy level, treasure chest, a place they
// are at on the map, and a status of alive or not alive.
// structure of one method from JsonSerializationDemo Project
public class WaterBear implements Writable {
    //constants
    public static final int INITIAL_ENERGY = 50;

    //fields
    private int energy; //the amount of energy
    private TreasureChest treasureChest; // the water bear's treasure chest
    private boolean alive; //whether bear is alive or not
    private Place place; //place in map

    // constructor
    // creates a water bear with
    // energy = initial energy, an empty treasure chest,
    // alive and at place = 0
    public WaterBear() {
        energy = INITIAL_ENERGY;
        treasureChest = new TreasureChest();
        alive = true;
        place = new Place(0,0);
    }

    //MODIFIES: this
    //EFFECTS: adds the energy the food has to this energy.
    public void eatFood(Food food) {
        energy += food.getEnergy();
    }

    //MODIFIES: this
    //EFFECTS: if there is enough energy to run away from predator, subtracts that amount of energy, runs down one step,
    // and returns true, otherwise returns false;
    // if energy is less than zero, throws NegativeEnergyException;
    // if the y coordinate of the water bear is not 1 or 2, goDown() throws OutOfMapException
    public void runAway(Predator predator) throws NegativeEnergyException, OutOfMapException {
        if (energy < 0) {
            throw new NegativeEnergyException("Negative amount of energy");
        }

        if (energy >= predator.getRunAwayEnergy()) {
            energy -= predator.getRunAwayEnergy();
            goDown();
        } else {
            alive = false;
        }
    }

    //EFFECTS: returns the clue in the clue item.
    public String checkClue(ClueItem clueItem) {
        return clueItem.getClue();
    }

    //EFFECTS: returns the description of the clue item
    public String checkItem(ClueItem clueItem) {
        return  clueItem.getDescription();
    }

    //MODIFIES: this
    //EFFECTS: adds a treasure to the treasure chest
    public void collectTreasure(Treasure treasure) {
        treasureChest.addTreasure(treasure);
    }

    //MODIFIES: this
    //EFFECTS: increases y coordinate of water bear by one;
    // if the y coordinate of the water bear is not 0 or 1, throws OutOfMapException
    public void goUp() throws OutOfMapException {
        if (place.getyCoord() != 0 && place.getyCoord() != 1) {
            throw new OutOfMapException("Cannot go up any further");
        }
        place.setyCoord(place.getyCoord() + 1);
    }

    //MODIFIES: this
    //EFFECTS: decreases y coordinate of water bear by one;
    // if the y coordinate of the water bear is not 1 or 2, throws OutOfMapException
    public void goDown() throws OutOfMapException {
        if (place.getyCoord() != 1 && place.getyCoord() != 2) {
            throw new OutOfMapException("Cannot go down any further");
        }
        place.setyCoord(place.getyCoord() - 1);
    }

    //MODIFIES: this
    //EFFECTS: increases x coordinate of water bear by one;
    // if the x coordinate of the water bear is not 0 or 1, throws OutOfMapException
    public void goRight() throws OutOfMapException {
        if (place.getxCoord() != 0 && place.getxCoord() != 1) {
            throw new OutOfMapException("Cannot go right any further");
        }
        place.setxCoord(place.getxCoord() + 1);
    }

    //MODIFIES: this
    //EFFECTS: decreases x coordinate of water bear by one
    // if the x coordinate of the water bear is not 1 or 2, throws OutOfMapException
    public void goLeft() throws OutOfMapException {
        if (place.getxCoord() != 1 && place.getxCoord() != 2) {
            throw new OutOfMapException("Cannot go left any further");
        }
        place.setxCoord(place.getxCoord() - 1);
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int e) {
        energy = e;
    }

    public TreasureChest getTreasureChest() {
        return treasureChest;
    }

    /*public void setTreasureChest(TreasureChest treasureChest) {
        this.treasureChest = treasureChest;
    }*/

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean status) {
        alive = status;
    }

    public Place getPlace() {
        return place;
    }

    public boolean coordMatch(int x, int y) {
        if (place.getxCoord() == x && place.getyCoord() == y) {
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: converts place coordinates to JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("place x", place.getxCoord());
        json.put("place y", place.getyCoord());
        json.put("energy", energy);
        json.put("treasure chest", treasureChestToJson());
        return json;
    }

    public JSONArray treasureChestToJson() {
        JSONArray jsonArray = new JSONArray();

        List<Treasure> treasureList = treasureChest.getListOfTreasures();
        for (Treasure t: treasureList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

}
