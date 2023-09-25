package ui;

import exception.NegativeEnergyException;
import exception.OutOfMapException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

// Referenced:
// https://introcs.cs.princeton.edu/java/15inout/GUI.java.html,
// CustomIconDemo project

// Water Bear Adventures adventure game application with a graphical user interface
public class WaterBearAdventuresGraphicApp extends JOptionPane implements ActionListener {
    private JFrame frame;
    private JPanel treasureChestPanel;
    private JLabel treasureLabel;
    private JLabel waterBearImageLabel;
    private List<JLabel> starLabels;
    private List<JLabel> coinLabels;
    private List<JLabel> stars;
    private JPanel saveLoadPanel;
    private JButton treasureButton;
    private JButton showStarsButton;
    private JButton saveButton;
    private JButton loadButton;
    private JPanel waterBearPanel;
    private JButton runAway;
    private JButton up;
    private JButton right;
    private JButton left;
    private JButton down;
    private JButton eat;
    private JButton check;

    private WaterBear waterBear;
    private Predator predator;
    private Food food;
    private ClueItem clueItem;
    private Treasure treasureOne;
    private Treasure treasureTwo;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/waterBear.json";

    private String frameTitle = "Water Bear Adventures";

    //Constructor
    public WaterBearAdventuresGraphicApp() {
        runAdventure();
    }

    // MODIFIES: this
    // EFFECTS: adds panels to the frame
    public void runAdventure() {
        /*boolean keepGoing = true;*/

        init();

        while (waterBear.isAlive()) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            displayMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: initialises all fields
    public void init() {
        initWaterBearFields();
        initFrame();
        initJSon();
        initTreasurePanel();
        initSaveLoadPanel();

        frame.add(treasureChestPanel, BorderLayout.EAST);
        frame.add(saveLoadPanel, BorderLayout.WEST);

        initWaterBearPanel();
        frame.add(waterBearPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initialises the objects on the game map
    public void initWaterBearFields() {
        waterBear = new WaterBear();
        predator = new Predator("Big tardigrade", 20);
        food = new Food("small tardigrade", 30);
        clueItem = new ClueItem("Glowing shell", "Keep rising");
        treasureOne = new Treasure("a sparkling star");
        treasureTwo = new Treasure("a shiny coin");
    }

    // MODIFIES: this
    // EFFECTS: initialises the fields related to the JSon reader and writer
    public void initJSon() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: initialises the frame
    public void initFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(frameTitle);
        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initialises the panel from which users can play the Water Bear Adventure game
    public void initWaterBearPanel() {
        waterBearPanel = new JPanel();
        waterBearPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        waterBearPanel.setLayout(new GridBagLayout());

        runAway = new JButton("Run away!");
        up = new JButton("Go up");
        right = new JButton("Go right");
        left = new JButton("Go left");
        down = new JButton("Go down");
        eat = new JButton("Eat food!");
        check = new JButton("Check item!");
        treasureButton = new JButton("Add treasure!");
        setUpWaterBearPanelButtons();

        Icon waterBearIcon = new ImageIcon("./data/smalltardigrade.png");
        waterBearImageLabel = new JLabel(waterBearIcon);
    }

    // MODIFIES: this
    // EFFECTS: handles displaying the correct buttons, given the waterbear's place
    public void displayMenu() {
        if (waterBear.getPlace().getxCoord() == 0 && waterBear.getPlace().getyCoord() == 0) {
            zeroZeroButtons();
        } else if (waterBear.getPlace().getxCoord() == 0 && waterBear.getPlace().getyCoord() == 1) {
            zeroOneButtons();
        } else if (waterBear.getPlace().getxCoord() == 0 && waterBear.getPlace().getyCoord() == 2) {
            zeroTwoButtons();
        } else if (waterBear.getPlace().getxCoord() == 1 && waterBear.getPlace().getyCoord() == 0) {
            oneZeroButtons();
        } else if (waterBear.getPlace().getxCoord() == 1 && waterBear.getPlace().getyCoord() == 1) {
            oneOneButtons();
        } else if (waterBear.getPlace().getxCoord() == 1 && waterBear.getPlace().getyCoord() == 2) {
            oneTwoButtons();
        } else if (waterBear.getPlace().getxCoord() == 2 && waterBear.getPlace().getyCoord() == 0) {
            twoZeroButtons();
        } else if (waterBear.getPlace().getxCoord() == 2 && waterBear.getPlace().getyCoord() == 1) {
            twoOneButtons();
        } else if (waterBear.getPlace().getxCoord() == 2 && waterBear.getPlace().getyCoord() == 2) {
            twoTwoButtons();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up the commands and action listener for buttons for the water bear panel,
    // which handle the actions the water bear can take (eat, go up and down, etc.)
    public void setUpWaterBearPanelButtons() {
        runAway.setActionCommand("run away");
        runAway.addActionListener(this);
        up.setActionCommand("up");
        up.addActionListener(this);
        right.setActionCommand("right");
        right.addActionListener(this);
        left.setActionCommand("left");
        left.addActionListener(this);
        down.setActionCommand("down");
        down.addActionListener(this);
        eat.setActionCommand("eat");
        eat.addActionListener(this);
        check.setActionCommand("check");
        check.addActionListener(this);
        treasureButton.setActionCommand("add treasure");
        treasureButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: initialises the panel with saving and loading functionality
    public void initSaveLoadPanel() {
        saveLoadPanel = new JPanel();
        saveLoadPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        saveLoadPanel.setLayout(new FlowLayout());

        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        saveButton.setActionCommand("save");
        loadButton.setActionCommand("load");
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);

        saveLoadPanel.add(saveButton);
        saveLoadPanel.add(loadButton);
    }

    // MODIFIES: this
    // EFFECTS: initialises the panel displaying the treasure chest (displays treasure with images of stars and coins,
    // with a label saying how many treasures there are); has a button that can show the stars collected only
    public void initTreasurePanel() {
        treasureChestPanel = new JPanel();
        treasureChestPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,10));
        treasureChestPanel.setLayout(new FlowLayout());
        treasureLabel = new JLabel("Number of treasures: 0");
        treasureChestPanel.add(treasureLabel);
        showStarsButton = new JButton("show Star Treasures collected");
        showStarsButton.addActionListener(this);
        showStarsButton.setActionCommand("show stars");
        treasureChestPanel.add(showStarsButton);
        starLabels = new ArrayList<>();
        coinLabels = new ArrayList<>();
        stars = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: handles the actions taken, depending on the action command
    @Override
    public void actionPerformed(ActionEvent e) {
        if (waterBear.coordMatch(0,2) || waterBear.coordMatch(2,1)) {
            processPredator(e);
        } else if (e.getActionCommand().equals("up") || e.getActionCommand().equals("right")
                || e.getActionCommand().equals("left") || e.getActionCommand().equals("down")) {
            directionActions(e);
        } else if (e.getActionCommand().equals("add treasure")) {
            collectTreasureForPlace();
        } else if (e.getActionCommand().equals("eat")) {
            eatFood(food);
        } else if (e.getActionCommand().equals("check")) {
            checkItem();
        } else if (e.getActionCommand().equals("save")) {
            saveWaterBear();
        } else if (e.getActionCommand().equals("load")) {
            loadWaterBear();
        } else if (e.getActionCommand().equals("show stars")) {
            showStars();
        }
    }

    // MODIFIES: this
    // EFFECTS: makes the water bear go up, down, left or right depending on the command
    public void directionActions(ActionEvent e) {
        if (e.getActionCommand().equals("up") || e.getActionCommand().equals("down")) {
            goUpOrDown(e);
        } else if (e.getActionCommand().equals("left") || e.getActionCommand().equals("right")) {
            goLeftOrRight(e);
        }
    }

    // MODIFIES: this
    // EFFECTS: makes the water bear go up or down,
    public void goUpOrDown(ActionEvent e) {
        if (e.getActionCommand().equals("up")) {
            try {
                waterBear.goUp();
            } catch (OutOfMapException outOfMapException) {
                outOfMapException.printStackTrace();
            }
        } else if (e.getActionCommand().equals("down")) {
            try {
                waterBear.goDown();
            } catch (OutOfMapException outOfMapException) {
                outOfMapException.printStackTrace();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: makes the water bear go left or right
    public void goLeftOrRight(ActionEvent e) {
        if (e.getActionCommand().equals("right")) {
            try {
                waterBear.goRight();
            } catch (OutOfMapException outOfMapException) {
                outOfMapException.printStackTrace();
            }
        } else if (e.getActionCommand().equals("left")) {
            try {
                waterBear.goLeft();
            } catch (OutOfMapException outOfMapException) {
                outOfMapException.printStackTrace();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: handles which treasure the water bear will collect
    private void collectTreasureForPlace() {
        if (waterBear.coordMatch(1,0)) {
            collectTreasure(treasureTwo);
        } else {
            collectTreasure(treasureOne);
        }
    }

    // MODIFIES: this
    // EFFECTS: handles the actions taken when the water bear is faced with a predator;
    // unlike at other places, in this case all actions except save, load or run away leads to death
    private void processPredator(ActionEvent e) {
        if (e.getActionCommand().equals("save")) {
            saveWaterBear();
        } else if (e.getActionCommand().equals("load")) {
            loadWaterBear();
        } else if (e.getActionCommand().equals("run away")) {
            try {
                waterBear.runAway(predator);
            } catch (NegativeEnergyException negativeEnergyException) {
                negativeEnergyException.printStackTrace();
            } catch (OutOfMapException outOfMapException) {
                outOfMapException.printStackTrace();
            }
            if (waterBear.getEnergy() >= predator.getRunAwayEnergy()) {
                showMessageDialog(waterBearPanel, "Ran away!");
            } else {
                showMessageDialog(waterBearPanel,
                        "Oh no, not enough energy! You hadn't eaten enough food. You are dead.");
            }
        } else {
            waterBear.setAlive(false);
            showMessageDialog(waterBearPanel, "Oops! You did not run away. You died.");
        }
    }

    // MODIFIES: this
    // EFFECTS: makes the water bear eat food and gain energy
    public void eatFood(Food food) {
        waterBear.eatFood(food);
        showMessageDialog(waterBearPanel, "Gained " + food.getEnergy() + " energy!");
    }

    // MODIFIES: this
    // EFFECTS: lets the water bear check a clue item and see a dialog box with the clue message
    public void checkItem() {
        showMessageDialog(waterBearPanel, "It's a " + waterBear.checkItem(clueItem) + "!"
                + "It says..." + waterBear.checkClue(clueItem));
    }

    // MODIFIES: this
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
            Icon treasureStar = new ImageIcon("./data/verysmallstar.png");
            Icon separateTreasureStar = new ImageIcon("./data/verysmallstar.png");
            Icon treasureCoin = new ImageIcon("./data/smallcoin.png");
            JLabel starLabel = new JLabel(treasureStar);
            JLabel separateStarLabel = new JLabel(separateTreasureStar);
            JLabel coinLabel = new JLabel(treasureCoin);
            for (int i = 0; i < waterBear.getTreasureChest().size(); i++) {
                if (waterBear.getTreasureChest().getListOfTreasures().get(i)
                        .getTreasureDescription().equals("a sparkling star")) {
                    starLabels.add(starLabel);
                    stars.add(separateStarLabel);
                } else {
                    coinLabels.add(coinLabel);
                }
            }
            addTreasureLabels();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: shows only the star treasures collected in a separate panel, when button is pressed
    public void showStars() {
        JPanel starsPanel = new JPanel();
        starsPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        starsPanel.setLayout(new FlowLayout());
        //Icon treasureStar = new ImageIcon("./data/verysmallstar.png");
       // JLabel separateStarLabel = new JLabel(treasureStar);
        /*for (int i = 0; i <= starLabels.size(); i++) {
            stars.add(separateStarLabel);
            System.out.println(starLabels.size());
        }
        for (JLabel s: stars) {
            starsPanel.add(s);
        }*/
        for (JLabel s: stars) {
            starsPanel.add(s);
        }
        frame.add(starsPanel, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: collects the treasure depending on the water bear's place, and adds images of the treasures
    // to the panels that display them
    public void collectTreasure(Treasure treasure) {
        waterBear.collectTreasure(treasure);

        treasureLabel.setText("Number of treasures: " + waterBear.getTreasureChest().size());

        Icon treasureStar = new ImageIcon("./data/verysmallstar.png");
        Icon separateTreasureStar = new ImageIcon("./data/verysmallstar.png");
        Icon treasureCoin = new ImageIcon("./data/smallcoin.png");
        JLabel starLabel = new JLabel(treasureStar);
        JLabel separateStarLabel = new JLabel(separateTreasureStar);
        JLabel coinLabel = new JLabel(treasureCoin);
        if (waterBear.coordMatch(1,0)) {
            coinLabels.add(coinLabel);
        } else {
            starLabels.add(starLabel);
            stars.add(separateStarLabel);
        }
        System.out.println(starLabels);
        System.out.println(coinLabels);
        addTreasureLabels();
    }

    // MODIFIES: this
    // EFFECTS : adds a label with a star icon to the treasureLabel
    public void addTreasureLabels() {
        List<JLabel> treasureLabels = new ArrayList<>();
        treasureLabels.addAll(coinLabels);
        treasureLabels.addAll(starLabels);
        for (JLabel s: treasureLabels) {
            treasureChestPanel.add(s);
        }
    }

    // MODIFIES: THIS
    // EFFECTS: displays options available at place 0,0
    private void zeroZeroButtons() {
        waterBearPanel.removeAll();
        waterBearPanel.revalidate();
        waterBearPanel.repaint();
        waterBearPanel.add(waterBearImageLabel);
        waterBearPanel.add(up);
        waterBearPanel.add(right);
    }

    // MODIFIES: THIS
    // EFFECTS: displays options available at place 1,0
    private void oneZeroButtons() {
        waterBearPanel.removeAll();
        waterBearPanel.revalidate();
        waterBearPanel.repaint();
        waterBearPanel.add(waterBearImageLabel);
        JLabel treasureMessage = new JLabel("There is a treasure here!");
        treasureMessage.setOpaque(true);
        waterBearPanel.add(treasureMessage);
        waterBearPanel.add(up);
        waterBearPanel.add(right);
        waterBearPanel.add(left);
        waterBearPanel.add(treasureButton);
    }

    // MODIFIES: THIS
    // EFFECTS: displays options available at place 2,0
    private void twoZeroButtons() {
        waterBearPanel.removeAll();
        waterBearPanel.revalidate();
        waterBearPanel.repaint();
        waterBearPanel.add(waterBearImageLabel);
        JLabel foodMessage = new JLabel("There is food here!");
        foodMessage.setOpaque(true);
        waterBearPanel.add(foodMessage);
        waterBearPanel.add(up);
        waterBearPanel.add(left);
        waterBearPanel.add(eat);
    }

    // MODIFIES: THIS
    // EFFECTS: displays options available at place 0,1
    private void zeroOneButtons() {
        waterBearPanel.removeAll();
        waterBearPanel.revalidate();
        waterBearPanel.repaint();
        waterBearPanel.add(waterBearImageLabel);
        JLabel clueMessage = new JLabel("There is a strange item here!");
        clueMessage.setOpaque(true);
        waterBearPanel.add(clueMessage);
        waterBearPanel.add(up);
        waterBearPanel.add(right);
        waterBearPanel.add(down);
        waterBearPanel.add(check);
    }

    // MODIFIES: THIS
    // EFFECTS: displays options available at place 1,1
    private void oneOneButtons() {
        waterBearPanel.removeAll();
        waterBearPanel.revalidate();
        waterBearPanel.repaint();
        waterBearPanel.add(waterBearImageLabel);
        JLabel foodMessage = new JLabel("There is food here!");
        foodMessage.setOpaque(true);
        waterBearPanel.add(foodMessage);
        waterBearPanel.add(up);
        waterBearPanel.add(right);
        waterBearPanel.add(left);
        waterBearPanel.add(down);
        waterBearPanel.add(eat);
    }

    // MODIFIES: THIS
    // EFFECTS: displays options available at place 2,1
    private void twoOneButtons() {
        waterBearPanel.removeAll();
        waterBearPanel.revalidate();
        waterBearPanel.repaint();
        waterBearPanel.add(waterBearImageLabel);
        JLabel predatorMessage = new JLabel("There is something coming! It sounds dangerous!");
        predatorMessage.setOpaque(true);
        waterBearPanel.add(predatorMessage);
        waterBearPanel.add(up);
        waterBearPanel.add(left);
        waterBearPanel.add(down);
        waterBearPanel.add(runAway);
    }

    // MODIFIES: THIS
    // EFFECTS: displays options available at place 2,0
    private void zeroTwoButtons() {
        waterBearPanel.removeAll();
        waterBearPanel.revalidate();
        waterBearPanel.repaint();
        waterBearPanel.add(waterBearImageLabel);
        JLabel predatorMessage = new JLabel("There is something coming! It sounds dangerous!");
        predatorMessage.setOpaque(true);
        waterBearPanel.add(predatorMessage);
        waterBearPanel.add(right);
        waterBearPanel.add(down);
        waterBearPanel.add(runAway);
    }

    // MODIFIES: THIS
    // EFFECTS: displays options available at place 1,2
    private void oneTwoButtons() {
        waterBearPanel.removeAll();
        waterBearPanel.revalidate();
        waterBearPanel.repaint();
        waterBearPanel.add(waterBearImageLabel);
        JLabel clueMessage = new JLabel("There is a strange item here!");
        clueMessage.setOpaque(true);
        waterBearPanel.add(clueMessage);
        waterBearPanel.add(right);
        waterBearPanel.add(left);
        waterBearPanel.add(down);
        waterBearPanel.add(check);
    }

    // MODIFIES: THIS
    // EFFECTS: displays options available at place 2,2
    private void twoTwoButtons() {
        waterBearPanel.removeAll();
        waterBearPanel.revalidate();
        waterBearPanel.repaint();
        waterBearPanel.add(waterBearImageLabel);
        JLabel treasureMessage = new JLabel("There is a treasure here!");
        treasureMessage.setOpaque(true);
        waterBearPanel.add(treasureMessage);
        waterBearPanel.add(left);
        waterBearPanel.add(down);
        waterBearPanel.add(treasureButton);
    }
}
