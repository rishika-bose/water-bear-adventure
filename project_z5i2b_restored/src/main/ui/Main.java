package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        // main method body for console-based application:
        try {
            new WaterBearAdventuresConsoleApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application : file not found");
        }
        // main method body for GUI application:
        //new WaterBearAdventuresGraphicApp();
    }
}
