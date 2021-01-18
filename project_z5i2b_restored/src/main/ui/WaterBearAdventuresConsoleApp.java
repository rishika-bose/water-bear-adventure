package ui;

import exception.NegativeEnergyException;
import exception.OutOfMapException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Water Bear Adventures console-based adventure game application
// code structure partly from Teller application
public class WaterBearAdventuresConsoleApp {
    private static final String JSON_STORE = "./data/waterBear.json";

    private Scanner input;
    private WaterBear waterBear;
    private Predator predatorOne;
    private Food foodOne;
    private ClueItem clueItemOne;
    private Treasure treasureOne;
    // add a list of place field? (or several, for several maps?)
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: runs the Water Bear Adventures application
    public WaterBearAdventuresConsoleApp() throws FileNotFoundException {
        runAdventure();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runAdventure() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing && (waterBear.isAlive())) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye! Hope you had fun in the water bear world.");
    }

   // MODIFIES: this
   // EFFECTS: initialises items on the map
    private void init() {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        waterBear = new WaterBear();
        predatorOne = new Predator("Big tardigrade", 20);
        foodOne = new Food("small tardigrade", 30);
        clueItemOne = new ClueItem("Glowing shell", "Keep rising");
        treasureOne = new Treasure("a sparkling star");
    }

    // EFFECTS: displays the menu of commands available for each place on the map
    private void displayMenu() {
        if (waterBear.getPlace().getxCoord() == 0 && waterBear.getPlace().getyCoord() == 0) {
            zeroZeroCommands();
        } else if (waterBear.getPlace().getxCoord() == 0 && waterBear.getPlace().getyCoord() == 1) {
            zeroOneCommands();
        } else if (waterBear.getPlace().getxCoord() == 0 && waterBear.getPlace().getyCoord() == 2) {
            zeroTwoCommands();
        } else if (waterBear.getPlace().getxCoord() == 1 && waterBear.getPlace().getyCoord() == 0) {
            oneZeroCommands();
        } else if (waterBear.getPlace().getxCoord() == 1 && waterBear.getPlace().getyCoord() == 1) {
            oneOneCommands();
        } else if (waterBear.getPlace().getxCoord() == 1 && waterBear.getPlace().getyCoord() == 2) {
            oneTwoCommands();
        } else if (waterBear.getPlace().getxCoord() == 2 && waterBear.getPlace().getyCoord() == 0) {
            twoZeroCommands();
        } else if (waterBear.getPlace().getxCoord() == 2 && waterBear.getPlace().getyCoord() == 1) {
            twoOneCommands();
        } else if (waterBear.getPlace().getxCoord() == 2 && waterBear.getPlace().getyCoord() == 2) {
            twoTwoCommands();
        }
    }

    //EFFECTS: displays options available at place 0,0
    private void zeroZeroCommands() {
        System.out.println("\nSelect from:");
        System.out.println("\tu -> go up");
        System.out.println("\tr -> go right");
        System.out.println("\tt -> show treasure chest");
        System.out.println("\tq -> quit");
        System.out.println("\ts -> save");
        System.out.println("\to -> load");
    }

    //EFFECTS: displays options available at place 0,1
    private void oneZeroCommands() {
        System.out.println("there is a treasure here! add?");
        System.out.println("\nSelect from:");
        System.out.println("\tu -> go up");
        System.out.println("\tr -> go right");
        System.out.println("\tl -> go left");
        System.out.println("\ta -> add treasure");
        System.out.println("\tt -> show treasure chest");
        System.out.println("\tq -> quit");
        System.out.println("\ts -> save");
        System.out.println("\to -> load");
    }

    //EFFECTS: displays options available at place 0,2
    private void twoZeroCommands() {
        System.out.println("there is food here! eat?");
        System.out.println("\nSelect from:");
        System.out.println("\tu -> go up");
        System.out.println("\tl -> go left");
        System.out.println("\te -> eat");
        System.out.println("\tt -> show treasure chest");
        System.out.println("\tq -> quit");
        System.out.println("\ts -> save");
        System.out.println("\to -> load");
    }

    //EFFECTS: displays options available at place 1,0
    private void zeroOneCommands() {
        System.out.println("there is a strange item here! check?");
        System.out.println("\nSelect from:");
        System.out.println("\tu -> go up");
        System.out.println("\tr -> go right");
        System.out.println("\td -> go down");
        System.out.println("\tc -> check");
        System.out.println("\tt -> show treasure chest");
        System.out.println("\tq -> quit");
        System.out.println("\ts -> save");
        System.out.println("\to -> load");
    }

    //EFFECTS: displays options available at place 1,1
    private void oneOneCommands() {
        System.out.println("there is food here! eat?");
        System.out.println("\nSelect from:");
        System.out.println("\tu -> go up");
        System.out.println("\tr -> go right");
        System.out.println("\tl -> go left");
        System.out.println("\td -> go down");
        System.out.println("\te -> eat");
        System.out.println("\tt -> show treasure chest");
        System.out.println("\tq -> quit");
        System.out.println("\ts -> save");
        System.out.println("\to -> load");
    }

    //EFFECTS: displays options available at place 1,2
    private void twoOneCommands() {
        System.out.println("there is something coming! run?");
        System.out.println("\nSelect from:");
        System.out.println("\tu -> go up");
        /*System.out.println("\tr -> go right");*/
        System.out.println("\tl -> go left");
        System.out.println("\td -> go down");
        System.out.println("\tx -> run");
        System.out.println("\tt -> show treasure chest");
        System.out.println("\tq -> quit");
        System.out.println("\ts -> save");
        System.out.println("\to -> load");
    }

    //EFFECTS: displays options available at place 2,0
    private void zeroTwoCommands() {
        System.out.println("there is something coming! run?");
        System.out.println("\nSelect from:");
        System.out.println("\tr -> go right");
        System.out.println("\td -> go down");
        System.out.println("\tx -> run");
        System.out.println("\tt -> show treasure chest");
        System.out.println("\tq -> quit");
        System.out.println("\ts -> save");
        System.out.println("\to -> load");
    }

    //EFFECTS: displays options available at place 2,1
    private void oneTwoCommands() {
        System.out.println("there is a strange item here! check?");
        System.out.println("\nSelect from:");
        System.out.println("\tr -> go right");
        System.out.println("\tl -> go left");
        System.out.println("\td -> go down");
        System.out.println("\tc -> check");
        System.out.println("\tt -> show treasure chest");
        System.out.println("\tq -> quit");
        System.out.println("\ts -> save");
        System.out.println("\to -> load");
    }

    //EFFECTS: displays options available at place 2,2
    private void twoTwoCommands() {
        System.out.println("there is a treasure here! add?");
        System.out.println("\nSelect from:");
        System.out.println("\tl -> go left");
        System.out.println("\td -> go down");
        System.out.println("\ta -> add treasure");
        System.out.println("\tt -> show treasure chest");
        System.out.println("\tq -> quit");
        System.out.println("\ts -> save");
        System.out.println("\to -> load");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (waterBear.coordMatch(0,2) || waterBear.coordMatch(2,1)) {
            processCommandPredator(command);
        } else if (command.equals("u") || command.equals("d")) {
            goUpOrDown(command);
        } else if (command.equals("r") || command.equals("l")) {
            goLeftOrRight(command);
        } else if (command.equals("a")) {
            collectTreasure(treasureOne);
        } else if (command.equals("e")) {
            eatFood(foodOne);
        } else if (command.equals("c")) {
            checkItem();
        } else if (command.equals("t")) {
            showTreasureChest();
        } else if (command.equals("s") || command.equals("o")) {
            saveOrLoad(command);
        } else {
            System.out.println("Sorry, that's not one of the options!");
        }
    }

    private void goUpOrDown(String command) {
        if (command.equals("u")) {
            try {
                waterBear.goUp();
            } catch (OutOfMapException e) {
                e.printStackTrace();
            }
        } else if (command.equals("d")) {
            try {
                waterBear.goDown();
            } catch (OutOfMapException e) {
                e.printStackTrace();
            }
        }
    }

    private void goLeftOrRight(String command) {
        if (command.equals("l")) {
            try {
                waterBear.goLeft();
            } catch (OutOfMapException e) {
                e.printStackTrace();
            }
        } else if (command.equals("r")) {
            try {
                waterBear.goRight();
            } catch (OutOfMapException e) {
                e.printStackTrace();
            }
        }
    }

    private void eatFood(Food food) {
        waterBear.eatFood(food);
        System.out.println("gained " + food.getEnergy() + "energy!");
    }

    private void collectTreasure(Treasure treasure) {
        waterBear.collectTreasure(treasure);
        System.out.println("collected a" + treasure.getTreasureDescription());
    }

    private void saveOrLoad(String command) {
        if (command.equals("s")) {
            saveWaterBear();
        } else if (command.equals("o")) {
            loadWaterBear();
        }
    }

    private void checkItem() {
        System.out.println("It's a " + waterBear.checkItem(clueItemOne) + "!");
        System.out.println("It says..." + waterBear.checkClue(clueItemOne));
    }

    private void runningAway() {
        try {
            waterBear.runAway(predatorOne);
        } catch (NegativeEnergyException e) {
            e.printStackTrace();
        } catch (OutOfMapException e) {
            e.printStackTrace();
        }
        if (waterBear.getEnergy() >= predatorOne.getRunAwayEnergy()) {
            System.out.println("ran away!");
        } else {
            System.out.println("oh no, not enough energy! You hadn't eaten enough food. You are dead.");
        }
    }

    private void processCommandPredator(String command) {
        if (command.equals("s")) {
            saveWaterBear();
        } else if (command.equals("o")) {
            loadWaterBear();
        } else if (!command.equals("x")) {
            waterBear.setAlive(false);
            System.out.println("You did not run away! You died.");
        } else {
            runningAway();
        }
    }

    // EFFECTS: saves the water bear to file
    private void saveWaterBear() {
        try {
            jsonWriter.open();
            jsonWriter.write(waterBear);
            jsonWriter.close();
            System.out.println("Saved water bear to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads water bear from file
    private void loadWaterBear() {
        try {
            waterBear = jsonReader.read();
            System.out.println("Loaded water bear from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: prints out contents of treasure chest
    private void showTreasureChest() {
        if (waterBear.getTreasureChest().checkIfChestEmpty()) {
            System.out.println("Treasure Chest is empty! "
                    + "Add treasure when you find some, to fill up your treasure chest!");
        } else {
            for (int i = 0; i < waterBear.getTreasureChest().size(); i++) {
                String description = waterBear.getTreasureChest().getTreasure(i).getTreasureDescription();
                System.out.println(description);
            }
        }
    }

}
