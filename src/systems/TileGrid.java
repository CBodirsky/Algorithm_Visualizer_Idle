
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
                stats[r][c].arraySize = 5 + (int)(Math.random() * 80);
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
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                SortingController tile = tiles[r][c];

                if (tile.isAnimating()) {
                    tile.updateAnimation();
                    continue;
                }

                if (tile.stepCooldown > 0 ) {
                    tile.stepCooldown--;
                    continue;
                }

                boolean swapped = tile.step();

                tile.stepCooldown = tile.getSortSpeed();

                if (swapped) {
                    tile.triggerSwapAnimation();
                }

                if (tile.isFinished()) {
                    // Award money based on tile payout multiplier
                    game.getCurrency().addMoney(stats[r][c].payoutMultiplier);

                    // Reset tile with updated stats
                    tiles[r][c] = createTile(stats[r][c]);
                }
            }
        }
    }

    //Draw a given tile at a time, padding inner area.
    private void drawTile(PApplet app, SortingController tile, float w, float h) {

        boolean hover = app.mouseX >= 0 && app.mouseX <= w &&
                app.mouseY >= 0 && app.mouseY <= h;

        if (hover) {
            app.fill(255, 255, 255, 20); // subtle hover tint
            app.noStroke();
            app.rect(0, 0, w, h);
        }

        // Border
        app.stroke(40);
        app.strokeWeight(1);
        app.noFill();
        app.rect(0, 0, w, h);

        // Padding
        float pad = 4;
        app.translate(pad, pad);
        float innerW = w - pad * 2;
        float innerH = h - pad * 2;

        tile.draw(app, innerW, innerH);
    }

    public void resetTile(int r, int c) {
        tiles[r][c] = createTile(stats[r][c]);
    }

    public void drawAll(PApplet app) {
        float w = tileW;
        float h = tileH;


        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                app.pushMatrix();
                app.translate(c * w, r * h);
                drawTile(app, tiles[r][c], w, h);
                app.popMatrix();
            }
        }
    }
}
