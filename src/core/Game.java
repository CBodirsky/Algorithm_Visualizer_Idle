
//Main game file. Initializes systems, manages camera controls, and runs the main
//draw loop.
package core;

import processing.core.PApplet;
import processing.event.MouseEvent;

import algorithms.*;
import systems.*;
import ui.*;
import visuals.*;


public class Game extends PApplet {

    CurrencySystem currency;
    UpgradeSystem upgrades;
    TileGrid grid;
    private float cameraX = 50;
    private float cameraY = 50;
    private float cameraZoom = 1.0f;

    UIManager ui;

    public static final int STARTING_STEP_DELAY = 50;
    public GameState state = GameState.MENU;
    int totalSorts = 0;
    int prestige = 0;
    public int unlockedTiles = 1;



    //Setters and getters
    public UpgradeSystem getUpgrades() {
        return upgrades;
    }

    public RoundUpgradeSystem roundUpgrades;
    public PrestigeSystem prestiges;

    public int getAvailableAlgorithms() {
        return 4; // Bubble, cocktail, insertion, selection for now
    }
    public int getGridSize() {
        return (int)Math.ceil(Math.sqrt(unlockedTiles));
    }
    public CurrencySystem getCurrency() {
        return currency;
    }
    public TileGrid getGrid() { return grid; }
    public float getCameraX() { return cameraX; }
    public float getCameraY() { return cameraY; }
    public float screenToWorldX(float sx) { return (sx - cameraX) / cameraZoom; }
    public float screenToWorldY(float sy) { return (sy - cameraY) / cameraZoom; }


    //Creates a shuffled array with values within the array range to
    //give consistent bar height easily.
    public int[] generateArray(int size) {
        int[] arr = new int[size];
        //Fill with number range
        for (int i = 0; i < size; i++) {
            arr[i] = i + 1;
        }
        //Mix the array using Fisher-Yates shuffle
        for(int i = size - 1; i > 0; i--) {
            int j = (int) random(i + 1);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        return arr;
    }

    public static void main(String[] args) {
        PApplet.main("core.Game");
    }

    public void settings() {
      size(1920, 1080);
//      size(800, 600);
//        fullScreen();
    } //Add , P2D after height when ready

    public void setup() {
        currency = new CurrencySystem();
        upgrades = new UpgradeSystem();
        roundUpgrades = new RoundUpgradeSystem();
        prestiges = new PrestigeSystem();
        //Temp set grid layout
        grid = new TileGrid(4, 4, this);

        ui = new UIManager(this, this);
        //Nothing like having to call (this, this).
        //One is PApplet and the other is Game
        ui.openPanel(new StartMenuPanel(ui));
        state = GameState.MENU;
    }

    //Because laptop, just doing left click to drag
    public void mouseDragged() {
        if (state == GameState.MENU) return;

        if (!ui.handleClick(this)) {
            cameraX += (mouseX - pmouseX);
            cameraY += (mouseY - pmouseY);
        }
    }

    public void mouseWheel(MouseEvent event) {
        float e = event.getCount();
        if (state == GameState.MENU) return;
        cameraZoom *= (1 - e * 0.05f);
        cameraZoom = constrain(cameraZoom, 0.2f, 5.0f);
    }

    public void mousePressed() {
        if (ui.handleClick(this)) {
            return;
        }
    }


    //Main visualization loop: updates the simulation, draws grids, and
    //then draws the UI on top.
    public void draw() {
        background(40);

        if (state != GameState.MENU) {
            pushMatrix();
            translate(cameraX, cameraY);
            scale(cameraZoom);
            grid.stepAll();
            grid.drawAll(this);
            popMatrix();
        }

        //UI
        ui.draw(this, currency.getMoney(), totalSorts, prestige);

    }
}
