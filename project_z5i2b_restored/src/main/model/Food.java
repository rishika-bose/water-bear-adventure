package model;

//Represents food for the water bear, with a description
// and an energy level
public class Food {
    //fields
    private String description;
    private int energy;

    //constructor
    public Food(String description,int energy) {
        this.description = description;
        this.energy = energy;
    }

    //methods

    public int getEnergy() {
        return energy;
    }
}
