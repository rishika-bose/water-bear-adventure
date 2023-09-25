package model;

//Represents a predator with a description and amount of energy
// required to run away from it
public class Predator {
    //fields
    private String description;
    private int runAwayEnergy;

    //constructor
    public Predator(String description, int runAwayEnergy) {
        this.description = description;
        this.runAwayEnergy = runAwayEnergy;
    }

    //methods
/*    public String getDescription() {
        return description;
    }*/

    public int getRunAwayEnergy() {
        return runAwayEnergy;
    }
}
