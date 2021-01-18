package model;

//represents a place on the map for the adventure, with an x coordinate and
// a y coordinate
public class Place {

    //fields
    private int xcoord;
    private int ycoord;
    // add another field for place description - "TREASURE", "FOOD" etc? enum?

    //constructor
    public Place(int x, int y) {
        xcoord = x;
        ycoord = y;
    }

    //methods
    public int getxCoord() {
        return xcoord;
    }

    public int getyCoord() {
        return ycoord;
    }

    public void setxCoord(int x) {
        xcoord = x;
    }

    public void setyCoord(int y) {
        ycoord = y;
    }


}
