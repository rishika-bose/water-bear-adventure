package model;

//Represents an item with a clue for finding the treasure
public class ClueItem {
    //fields
    private String description;
    private String clue;

    //constructor
    public ClueItem(String description, String clue) {
        this.description = description;
        this.clue = clue;
    }

    //methods
    public String getDescription() {
        return description;
    }

    public String getClue() {
        return clue;
    }
}
