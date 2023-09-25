package persistence;

import model.Food;
import model.Treasure;
import model.TreasureChest;
import model.WaterBear;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads waterbear from JSON data stored in file
// code structure partly from JsonSerializationDemo project, JsonReader class
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads water bear from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WaterBear read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWaterBear(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    /// EFFECTS: parses water bear place coordinates from JSON object and returns it
    private WaterBear parseWaterBear(JSONObject jsonObject) {
        WaterBear wb = new WaterBear();
        wb.getPlace().setxCoord(jsonObject.getInt("place x"));
        wb.getPlace().setyCoord(jsonObject.getInt("place y"));
        wb.setEnergy(jsonObject.getInt("energy"));
        addTreasures(wb, jsonObject);

        return wb;
    }

    //  MODIFIES: wb
    // EFFECTS: parses treasures from JSON object and
    // adds them to water bear's treasure chest
    private void addTreasures(WaterBear wb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("treasure chest");
        for (Object json: jsonArray) {
            JSONObject nextTreasure = (JSONObject) json;
            addTreasure(wb, nextTreasure);
        }
    }

    // MODIFIES: wb
    // EFFECTS: parses treasure from JSON object and adds it to treasure chest
    private  void addTreasure(WaterBear wb, JSONObject jsonObject) {
        String treasureDescription = jsonObject.getString("treasure description");
        Treasure treasure = new Treasure(treasureDescription);
        wb.getTreasureChest().addTreasure(treasure);
    }
}
