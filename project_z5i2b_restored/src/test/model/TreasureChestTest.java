package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TreasureChestTest {
    TreasureChest treasureChest;
    Treasure thisTreasure;

    @BeforeEach
    public void runBefore() {
        treasureChest = new TreasureChest();
        thisTreasure = new Treasure("this treasure");
    }

    @Test
    public void testAddTreasure() {
        treasureChest.addTreasure(thisTreasure);
        assertFalse(treasureChest.checkIfChestEmpty());
        assertEquals(thisTreasure
                , treasureChest.getTreasure(0));
        assertEquals("this treasure", treasureChest.getTreasure(0).getTreasureDescription());
    }
}
