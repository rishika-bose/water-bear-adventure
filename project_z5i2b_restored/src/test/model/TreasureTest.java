package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TreasureTest {
    Treasure treasure;

    @BeforeEach
    public void runBefore() {
        treasure = new Treasure("coin");
    }

    @Test
    public void testToJSon() {
        assertFalse(treasure.toJson().isNull("treasure description"));
        assertEquals("coin", treasure.toJson().get("treasure description"));
    }
}
