package systems;

import algorithms.CocktailShakerSort;
import core.Game;
import visuals.SwapAnimation;
import processing.core.PApplet;
import algorithms.SortAlgorithm;
import algorithms.BubbleSort;

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
                //Tempoary display of different tiles
                stats[r][c].arraySize = 20 + (int)(Math.random() * 80);
//                stats[r][c].sortSpeed = 1 + (int)(Math.random() * 3);
                //end temp setup
                tiles[r][c] = createTile(stats[r][c]);
            }
        }
    }

    private SortingController createTile(TileStats s) {

        int[] arr = game.generateArray(s.arraySize);
        SortAlgorithm algo = new BubbleSort(arr);
        int choice = (int)(Math.random() * 2);
        //temp random algo
        switch (choice) {
            case 0 -> algo = new BubbleSort(arr);
            case 1 -> algo = new CocktailShakerSort(arr);
//            case 2 -> algo = new InsertionSort(arr);

            //end temp algo
        }

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

                boolean swapped = tile.step();

                if (swapped) {
                    tile.triggerSwapAnimation();
                }

//                tiles[r][c].step();

                if (tile.isFinished()) {
                    // Award money based on tile payout multiplier
                    game.getCurrency().addMoney(stats[r][c].payoutMultiplier);

                    // Reset tile with updated stats
                    tiles[r][c] = createTile(stats[r][c]);
                }
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
        app.translate(pad, pad);
        float innerW = w - pad * 2;
        float innerH = h - pad * 2;

        tile.draw(app, innerW, innerH);
    }

    public void drawAll(PApplet app) {
//        float w = app.width / (float) cols;
//        float h = app.height / (float) rows;
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
