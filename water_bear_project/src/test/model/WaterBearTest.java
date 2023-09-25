package model;

import exception.NegativeEnergyException;
import exception.OutOfMapException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WaterBearTest {
    WaterBear waterBear;

    @BeforeEach
    public void runBefore() {
        waterBear = new WaterBear();
    }

    @Test
    public  void testEatFood() {
        Food amoeba = new Food("amoeba", 20);
        waterBear.eatFood(amoeba);
        assertTrue(waterBear.getEnergy() > WaterBear.INITIAL_ENERGY);
        assertEquals((WaterBear.INITIAL_ENERGY + 20)
                , waterBear.getEnergy());
    }

    @Test
    public void testRunAwayFromOneOne() {
        try {
            waterBear.goRight();
        } catch (OutOfMapException e) {
            fail("Should not have caught exception.");
        }
        try {
            waterBear.goUp();
        } catch (OutOfMapException e) {
            fail("Should not have caught exception.");
        }
        assertTrue(waterBear.coordMatch(1, 1));
        Predator predator = new Predator("big tardigrade"
                , 20);
        try {
            waterBear.runAway(predator);
        } catch (NegativeEnergyException | OutOfMapException e) {
            fail("Should not have caught exception.");
        }
        assertTrue(waterBear.getEnergy() < WaterBear.INITIAL_ENERGY);
        assertEquals((WaterBear.INITIAL_ENERGY  - 20)
                , waterBear.getEnergy());
    }

    @Test
    public void testRunAwayFromZeroZero() {
        Predator predator = new Predator("big tardigrade"
                , 20);
        try {
            waterBear.runAway(predator);
            fail("Should have caught exception.");
        } catch (NegativeEnergyException e) {
            fail("Should not have caught exception.");
        } catch (OutOfMapException e) {
            // expected
        }
        assertTrue(waterBear.getEnergy() < WaterBear.INITIAL_ENERGY);
        assertEquals((WaterBear.INITIAL_ENERGY  - 20)
                , waterBear.getEnergy());
    }

    @Test
    public void testRunAwayFromZeroTwo() {
        try {
            waterBear.goUp();
            waterBear.goUp();
            waterBear.goRight();
        } catch (Exception e) {
            fail("Should not have caught exception.");
        }
        Predator predator = new Predator("big tardigrade"
                , 20);
        try {
            waterBear.runAway(predator);
        } catch (NegativeEnergyException e) {
            fail("Should not have caught exception.");
        } catch (OutOfMapException e) {
            // expected
        }
        assertTrue(waterBear.getEnergy() < WaterBear.INITIAL_ENERGY);
        assertEquals((WaterBear.INITIAL_ENERGY  - 20)
                , waterBear.getEnergy());

        Predator bigPredator = new Predator("human", 100);
        try {
            waterBear.runAway(bigPredator);
        } catch (NegativeEnergyException | OutOfMapException e) {
            fail("Should not have caught exception.");
        }
        assertFalse(waterBear.isAlive());
    }

    @Test
    public void testRunAwayNegativeEnergy() {
        try {
            waterBear.goUp();
        } catch (OutOfMapException e) {
            fail("Should not have caught exception.");
        }
        Predator bigPredator = new Predator("human", 50);
        waterBear.setEnergy(-5);
        try {
            waterBear.runAway(bigPredator);
            fail("Should have caught exception.");
        } catch (NegativeEnergyException e) {
            //expected
        } catch (OutOfMapException e) {
            fail("Should not have caught exception.");
        }

    }

    @Test
    public void testCheckClue() {
        ClueItem clueItem = new ClueItem("asteroid", "Move right");
        assertEquals("Move right", waterBear.checkClue(clueItem));
    }

    @Test
    public void testCheckItem() {
        ClueItem clueItem = new ClueItem("asteroid", "Move right");
        assertEquals("asteroid", waterBear.checkItem(clueItem));
    }

    @Test
    public void testCollectTreasure() {
        Treasure treasure = new Treasure("treasure");
        waterBear.collectTreasure(treasure);
        assertFalse(waterBear.getTreasureChest().checkIfChestEmpty());
        assertEquals(treasure, waterBear.getTreasureChest().getTreasure(0));
    }

    @Test
    public void testGoUpFromZeroZero() {
        int initialYPosition = waterBear.getPlace().getyCoord();
        try {
            waterBear.goUp();
        } catch (OutOfMapException e) {
            fail("Should not have caught exception.");
        }
        int nextYPosition = waterBear.getPlace().getyCoord();
        assertTrue(nextYPosition > initialYPosition);
        assertEquals(initialYPosition + 1, nextYPosition);
    }

    @Test
    public void testGoUpFromTwoZero() {
        try {
            waterBear.goUp();
            waterBear.goUp();
        } catch (OutOfMapException e) {
            fail("Should not have caught exception.");
        }
        try {
            waterBear.goUp();
            fail("Should not have caught exception.");
        } catch (OutOfMapException e) {
            // expected
        }
    }

    @Test
    public void testGoDown() {
        try {
            waterBear.goUp();
            int initialYPosition = waterBear.getPlace().getyCoord();
            waterBear.goDown();
            int nextYPosition = waterBear.getPlace().getyCoord();
            assertTrue(nextYPosition < initialYPosition);
            assertEquals(initialYPosition - 1, nextYPosition);
        } catch (Exception e) {
            fail("Should not have caught exception.");
        }
    }

    @Test
    public void testGoDOwnOutOfMap() {
        try {
            waterBear.goDown();
            fail("Should have caught exception.");
        } catch (OutOfMapException e) {
            //expected
        }
    }

    @Test
    public void testGoRight() {
        int initialXPosition = waterBear.getPlace().getxCoord();
        try {
            waterBear.goRight();
        } catch (OutOfMapException e) {
            fail("Should not have caught exception.");
        }
        int nextXPosition = waterBear.getPlace().getxCoord();
        assertTrue(nextXPosition > initialXPosition);
        assertEquals(initialXPosition + 1, nextXPosition);
    }

    @Test
    public void testGoRightOutOfMap() {
        try {
            waterBear.goRight();
            waterBear.goRight();
        } catch (OutOfMapException e) {
            fail("Should not have caught exception.");
        }
        try {
            waterBear.goRight();
            fail("Should have caught exception.");
        } catch (OutOfMapException e) {
            //expected
        }
    }

    @Test
    public void testGoLeft() {
        try {
            waterBear.goRight();
        } catch (OutOfMapException e) {
            fail("Should not have caught exception.");
        }
        int initialXPosition = waterBear.getPlace().getxCoord();
        try {
            waterBear.goLeft();
        } catch (OutOfMapException e) {
            fail("Should not have caught exception.");
        }
        int nextXPosition = waterBear.getPlace().getxCoord();
        assertTrue(nextXPosition < initialXPosition);
        assertEquals(initialXPosition - 1, nextXPosition);
    }

    @Test
    public void testGoLeftOutOfMapZeroZero() {
        try {
            waterBear.goLeft();
            fail("Should have caught exception.");
        } catch (OutOfMapException e) {
            //expected
        }
    }

    @Test
    public void testGoLeftOutOfMap() {
        try {
            waterBear.goRight();
            waterBear.goUp();
            waterBear.goLeft();
        } catch (OutOfMapException e) {
            fail("Should not have caught exception.");
        }

        try {
            waterBear.goLeft();
            fail("Should have caught exception.");
        } catch (OutOfMapException e) {
            //expected
        }
    }

    @Test
    public void testCoordMatch() {
        assertTrue(waterBear.coordMatch(0,0));
        assertFalse(waterBear.coordMatch(1,2));
        try {
            waterBear.goRight();
        } catch (OutOfMapException e) {
            fail("Should not have caught exception.");
        }
        assertTrue(waterBear.coordMatch(1,0));
        assertFalse(waterBear.coordMatch(2,0));
        assertFalse(waterBear.coordMatch(1,2));
    }

    @Test
    public void testSetAlive() {
        assertTrue(waterBear.isAlive());
        waterBear.setAlive(false);
        assertFalse(waterBear.isAlive());
    }

    @Test
    public void testTreasureChestToJson() {
        assertEquals(0, waterBear.treasureChestToJson().length());
        Treasure treasure = new Treasure("Star");
        waterBear.collectTreasure(treasure);
        assertEquals(1, waterBear.treasureChestToJson().length());
    }
}