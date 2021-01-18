package persistence;

import model.Treasure;
import model.WaterBear;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// code structure partly from JsonSerializationDemo project
public class JsonReaderTest {

    @Test
    void testNoFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WaterBear wb = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testStartingPointBear() {
        JsonReader reader = new JsonReader("./data/testStartingPointBear.json");
        try {
            WaterBear wb = reader.read();
            assertEquals(0, wb.getPlace().getxCoord());
            assertEquals(0, wb.getPlace().getyCoord());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testSomewhereInMapBear() {
        JsonReader reader = new JsonReader("./data/testSomewhereInMapBear.json");
        try {
            WaterBear wb = reader.read();
            assertEquals(1, wb.getPlace().getxCoord());
            assertEquals(2, wb.getPlace().getyCoord());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testAddTreasuresEmptyChest() {
        JsonReader reader = new JsonReader("./data/testAddTreasures.json");
        try {
            WaterBear wb = reader.read();
            assertEquals(0, wb.getTreasureChest().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testAddTreasuresWithSomeTreasures() {
        JsonReader reader = new JsonReader("./data/testAddTreasuresSomeTreasures.json");
        try {
            WaterBear wb = reader.read();
            assertEquals(2, wb.getTreasureChest().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
