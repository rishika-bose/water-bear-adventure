package model;

import org.json.JSONObject;
import persistence.Writable;

//Represents a treasure that can be added to the treasure chest
public class Treasure implements Writable {
    private String treasureDescription;

    //Constructor
    public Treasure(String description) {
        treasureDescription = description;
    }

    //getter
    public  String getTreasureDescription() {
        return treasureDescription;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("treasure description", treasureDescription);
        return json;
    }
}
