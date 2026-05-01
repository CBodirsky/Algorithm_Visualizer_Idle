
//Manages the grid of sorting tiles. Creation, stepping, drawing, resets.
package systems;

import algorithms.*;
import core.Game;
import visuals.SwapAnimation;
import processing.core.PApplet;


public class TileGrid {

    public final int rows;
    public final int cols;

    private final Game game;

    private final float tileW = 1770;
    private final float tileH = 1000;

    public final SortingController[][] tiles;
    public final TileStats[][] stats;

    public TileGrid(int rows, int cols, Game game) {
        this.rows = rows;
        this.cols = cols;
        this.game = game;

        tiles = new SortingController[rows][cols];
        stats = new TileStats[rows][cols];

        // Initialize tiles + stats
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                stats[r][c] = new TileStats();
                //Temporary display of different tiles
                stats[r][c].algorithmType = (int)(Math.random() * game.getAvailableAlgorithms());
                stats[r][c].arraySize = 8;
                //end temp setup
                tiles[r][c] = createTile(stats[r][c]);
            }
        }
    }

    //Creates the individual tiles for the sorting
    private SortingController createTile(TileStats s) {

        int[] arr = game.generateArray(s.arraySize);
        SortAlgorithm algo = switch(s.algorithmType) {
            case 0 -> new BubbleSort(arr);
            case 1 -> new CocktailShakerSort(arr);
            case 2 -> new InsertionSort(arr);
            case 3 -> new SelectionSort(arr);
            default -> new BubbleSort(arr);
        };

        SwapAnimation anim = new SwapAnimation();
        return new SortingController(algo, anim, game, s);
    }

    public void stepAll() {
        int size = game.getGridSize();
        int count = 0;

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (count >= game.unlockedTiles) return;
                SortingController tile = tiles[r][c];
                if (tile.waitingForRestart) {
                    count++;
                    continue;
                }
                if (tile.isAnimating()) {
                    tile.updateAnimation();
                    count++;
                    continue;
                }
                if (tile.stepCooldown > 0) {
                    tile.stepCooldown--;
                    count++;
                    continue;
                }
                boolean swapped = tile.step();
                tile.stepCooldown = tile.getSortSpeed();
                if (swapped) tile.triggerSwapAnimation();
                if (tile.isFinished()) {
                    boolean dp = game.roundUpgrades.doublePayout.purchased;
                    double reward = game.getCurrency().calculateReward(stats[r][c].arraySize, stats[r][c].payoutMultiplier, dp);
                    game.getCurrency().addMoney(reward);
                    if (game.roundUpgrades.autoSort.purchased) {
                        tiles[r][c] = createTile(stats[r][c]);
                    } else {
                        tile.waitingForRestart = true;
                    }
                }
                count++;
            }
        }
    }


    private void drawTile(PApplet app, SortingController tile, float w, float h) {

        // Border
        app.stroke(40);
        app.strokeWeight(1);
        app.noFill();
        app.rect(0, 0, w, h);

        // Padding
        float pad = 4;
        app.pushMatrix();
        app.translate(pad, pad);
        float innerW = w - pad * 2;
        float innerH = h - pad * 2;

        // Draw bars
        tile.draw(app, innerW, innerH);
        app.popMatrix();

        // --- Draw overlay ON TOP, still in tile space ---
        if (tile.waitingForRestart && !game.roundUpgrades.autoSort.purchased) {
            app.pushStyle();
            app.fill(0, 0, 0, 120);
            app.noStroke();
            app.rect(0, 0, w, h);

            app.fill(255);
            app.textAlign(PApplet.CENTER, PApplet.CENTER);
            app.textSize(32);
            app.text("Click to restart", w / 2, h / 2);
            app.popStyle();
        }
    }



    public void resetTile(int r, int c) {
        tiles[r][c] = createTile(stats[r][c]);
    }

    public void restartWaitingTiles() {
        int size = game.getGridSize();
        int count = 0;

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {

                if (count >= game.unlockedTiles) return;

                SortingController tile = tiles[r][c];

                if (tile.waitingForRestart) {
                    tile.waitingForRestart = false;
                    tiles[r][c] = createTile(stats[r][c]);
                }

                count++;
            }
        }
    }

    public void resetAllTiles() {
        int size = game.getGridSize();

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                tiles[r][c] = createTile(stats[r][c]);
            }
        }
    }




    public void drawAll(PApplet app) {
        int size = game.getGridSize();
        int count = 0;

        float w = tileW;
        float h = tileH;

        app.pushMatrix();
        app.translate(game.getCameraX(), game.getCameraY());

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (count >= game.unlockedTiles) {
                    app.popMatrix();
                    return;
                }
                app.pushMatrix();
                app.translate(c * w, r * h);
                drawTile(app, tiles[r][c], w, h);
                app.popMatrix();

                count++;
            }
        }
        app.popMatrix();
    }

    public boolean handleClick(PApplet app) {
        int size = game.getGridSize();
        int count = 0;

        float w = tileW;
        float h = tileH;

        // Convert mouse to world space
        float worldX = game.screenToWorldX(app.mouseX);
        float worldY = game.screenToWorldY(app.mouseY);

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {

                if (count >= game.unlockedTiles) return false;

                float x = c * w;
                float y = r * h;

                if (worldX >= x && worldX <= x + w &&
                        worldY >= y && worldY <= y + h) {

                    SortingController tile = tiles[r][c];

                    if (tile.waitingForRestart && !game.roundUpgrades.autoSort.purchased) {
                        tile.waitingForRestart = false;
                        tiles[r][c] = createTile(stats[r][c]);
                        return true;
                    }
                }

                count++;
            }
        }

        return false;
    }




}
