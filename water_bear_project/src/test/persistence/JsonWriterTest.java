package persistence;

import exception.OutOfMapException;
import model.WaterBear;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// code structure partly from JsonSerializationDemo project
public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            WaterBear wr = new WaterBear();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterStartingBear() {
        WaterBear wb = new WaterBear();
        JsonWriter writer = new JsonWriter("./data/testWriterStartingBear.json");
        try {
            writer.open();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.write(wb);
        writer.close();

        JsonReader reader = new JsonReader("./data/testWriterStartingBear.json");
        try {
            wb = reader.read();
            assertEquals(0, wb.getPlace().getxCoord());
            assertEquals(0, wb.getPlace().getyCoord());
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterSomewhereBear() {
        WaterBear wb = new WaterBear();
        try {
            wb.goUp();
            wb.goRight();
            wb.goRight();
        } catch (OutOfMapException e) {
            fail("Should not have caught exception");
        }

        JsonWriter writer = new JsonWriter("./data/testWriterStartingBear.json");
        try {
            writer.open();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.write(wb);
        writer.close();

        JsonReader reader = new JsonReader("./data/testWriterStartingBear.json");
        try {
            wb = reader.read();
            assertEquals(2, wb.getPlace().getxCoord());
            assertEquals(1, wb.getPlace().getyCoord());
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }
}
