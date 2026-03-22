package core;

import processing.core.PApplet;

import algorithms.*;
import systems.*;
import ui.*;
import visuals.*;
import visuals.BarRenderer;


public class Game extends PApplet {

    CurrencySystem currency;
    UpgradeSystem upgrades;

    BarRenderer barRenderer;
    SortingController sorting;
    SwapAnimation swapAnim;
    SortAlgorithm algorithm;
    UIManager ui;

    public static final int STARTING_STEP_DELAY = 10;
    int stepDelay = 10;
    int stepCounter = 0;
    int stepsPerFrame = 1;
    int arraySize = 8;
    int totalSorts = 0;
    int prestige = 0;
    boolean sortFinished = false;
    private boolean autoSortEnabled = false;
    private int autoSortTimer = 0;
    private int autoSortDelay = 60;

    //Setters and getters
    public UpgradeSystem getUpgrades() {
        return upgrades;
    }

    public CurrencySystem getCurrency() {
        return currency;
    }

    public int getStepDelay() {
        return stepDelay;
    }

    public void setStepDelay(int value) {
        stepDelay = value;
    }

    public int getArraySize() {
        return arraySize;
    }

    public void increaseArraySize(int amount) {
        arraySize += amount;
    }

    public void enableAutoSort() {
        autoSortEnabled = true;
    }


    private void renderBars() {
        barRenderer.drawBars(
                this,
                sorting.getArray(),
                algorithm.getActiveIndices(),
                sorting.getSwapIndices(),
                swapAnim.active ? (float)swapAnim.timer / swapAnim.duration : 1.0f,
                ui.getTopBarHeight()
        );
    }

    //Makes arrays
    int[] generateArray(int size) {
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
//        size(1920, 1080);
        size(800, 600);
    } //Add , P2D after height when ready

    public void setup() {
        currency = new CurrencySystem();
        upgrades = new UpgradeSystem();
        barRenderer = new BarRenderer();
        swapAnim = new SwapAnimation();
        sorting = new SortingController(algorithm, swapAnim, this);
        algorithm = new BubbleSort(generateArray(arraySize));
        sorting = new SortingController(algorithm, swapAnim, this);
        ui = new UIManager(this);
    }

    public void mousePressed() {
        if (ui.handleClick(this)) {
            return;
        }
    }

    public void startNewSort() {
        algorithm = new BubbleSort(generateArray(arraySize));
        sorting = new SortingController(algorithm, swapAnim, this);
        sortFinished = false;
    }

    public boolean isSortFinished() {
        return sorting.isFinished();
    }

    public void draw() {
        background(0);

        // If swap animation is active
        if(sorting.isAnimating()) {
            sorting.updateAnimation();
        }

        // Step logic
        else if (!sorting.isFinished()) {
            for (int s = 0; s < stepsPerFrame; s++) {
                //Delay system
                if(stepCounter < stepDelay) {
                    stepCounter++;
                    break; //So not step yet
                }
                stepCounter = 0;

                //Perform one step
                boolean swapped = sorting.step();

                if (swapped) {
                    sorting.triggerSwapAnimation();
                    break;
                }

                if (sorting.isFinished()) {
                    if (!sortFinished) {
                        sortFinished = true;
                        totalSorts++;
                        currency.addMoney(currency.getRewardForSort(arraySize));
                    }
                    break;
                }
            }

        }
        //Render bars
        renderBars();

        // Finished state
        if(sorting.isFinished()) {
            if (autoSortEnabled) {
                autoSortTimer++;
                if (autoSortTimer >= autoSortDelay) {
                    autoSortTimer = 0;
                    startNewSort();
                }
            }
            float t = (frameCount % 60) / 60.0f;
            int c = lerpColor(color(50, 100, 255), color(0, 255, 150), t);
            fill(c);
        }

        //UI
        ui.draw(this, currency.getMoney(), totalSorts, prestige);

    }
}
