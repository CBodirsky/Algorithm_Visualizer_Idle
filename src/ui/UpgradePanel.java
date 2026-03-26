
//Full screen panel that lists upgrades for each tile and the related
//algorithm swap option into a table layout.
package ui;

import processing.core.PApplet;
import java.util.ArrayList;
import java.util.List;

import systems.TileGrid;
import systems.TileStats;
import systems.UpgradeType;

public class UpgradePanel extends Panel {

    private Button closeButton;
    private List<UpgradeButton> upgradeButtons = new ArrayList<>();
    private UIManager ui;

    // Layout constants
    private static final int START_Y = 200;
    private static final int ROW_HEIGHT = 50;

    private static final int COL_X_TILE      = 40;
    private static final int COL_X_ARRAY     = 150;
    private static final int COL_X_SPEED     = 350;
    private static final int COL_X_PAYOUT    = 550;
    private static final int COL_X_ALGO      = 750;

    // Button widths (this is what you care about)
    private static final int BTN_W_ARRAY  = 180;
    private static final int BTN_W_SPEED  = 180;
    private static final int BTN_W_PAYOUT = 180;
    private static final int BTN_W_ALGO = 200;

    public UpgradePanel(UIManager ui) {
        this.ui = ui;

        TileGrid grid = ui.getGame().getGrid();

        closeButton = new Button(
                20, 60, 120, 40,
                "Close",
                ui::closeUpgradePanel
        );

        int y = START_Y;

        //Draws out the buttons in a table.
        for (int r = 0; r < grid.rows; r++) {
            for (int c = 0; c < grid.cols; c++) {

                TileStats stats = grid.stats[r][c];

                // Array Size
                upgradeButtons.add(new UpgradeButton(
                        COL_X_ARRAY, y, BTN_W_ARRAY, 40,
                        UpgradeType.ARRAY_SIZE, stats, r, c, ui
                ));

                // Sort Speed
                upgradeButtons.add(new UpgradeButton(
                        COL_X_SPEED, y, BTN_W_SPEED, 40,
                        UpgradeType.SORT_SPEED, stats, r, c, ui
                ));

                // Payout
                upgradeButtons.add(new UpgradeButton(
                        COL_X_PAYOUT, y, BTN_W_PAYOUT, 40,
                        UpgradeType.PAYOUT_MULTIPLIER, stats, r, c, ui
                ));

                // Algorithm Select
                upgradeButtons.add(new UpgradeButton(
                        COL_X_ALGO, y, 120, 40,
                        UpgradeType.ALGORITHM, stats, r, c, ui
                ));


                y += ROW_HEIGHT;
            }
        }
    }

    //Draws the buttons themselves and related factors.
    @Override
    public void draw(PApplet app) {
        app.pushStyle();

        app.fill(30, 200);
        app.noStroke();
        app.rect(0, 40, app.width, app.height - 40);

        app.fill(255);
        app.textAlign(PApplet.CENTER, PApplet.TOP);
        app.textSize(28);
        app.text("Tile Upgrades", app.width / 2f, 50);

        app.textAlign(PApplet.LEFT, PApplet.TOP);
        app.textSize(18);
        app.text("Tile",      COL_X_TILE,  150);
        app.text("Array Size",COL_X_ARRAY, 150);
        app.text("Sort Speed",COL_X_SPEED, 150);
        app.text("Payout",    COL_X_PAYOUT,150);
        app.text("Algorithm", COL_X_ALGO,  150);

        closeButton.draw(app);

        for (UpgradeButton b : upgradeButtons) {
            b.draw(app);
        }

        app.popStyle();
    }



    //Click handling logic.
    @Override
    public void handleClick(PApplet app) {

        if (closeButton.isMouseOver(app)) {
            closeButton.handleClick(app);
            return;
        }

        for (UpgradeButton b : upgradeButtons) {
            if (b.isMouseOver(app)) {
                b.handleClick(app);
                return;
            }
        }
    }
}
