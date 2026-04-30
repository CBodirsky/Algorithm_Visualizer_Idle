
//Full screen panel that lists upgrades for each tile and the related
//algorithm swap option into a table layout.
package ui;

import processing.core.PApplet;
import java.util.ArrayList;
import java.util.List;

import systems.TileGrid;
import systems.TileStats;
import systems.UpgradeType;

public class PrestigePanel extends Panel {

    private Button closeButton;
    private List<UpgradeButton> upgradeButtons = new ArrayList<>();
    private List<Integer> tileLabelY = new ArrayList<>();
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

    public PrestigePanel(UIManager ui) {
        this.ui = ui;

        TileGrid grid = ui.getGame().getGrid();

        closeButton = new Button(
                100, 60, 120, 40,
                "Close",
                ui::closeCurrentPanel
        );
    }

    //Draws the buttons themselves and related factors.
    @Override
    public void draw(PApplet app) {
        app.pushStyle();
        app.fill(255);
        app.textSize(30);
        app.textAlign(PApplet.LEFT, PApplet.TOP);

        app.fill(30, 200);
        app.noStroke();
        app.rect(0, 40, app.width, app.height - 40);

        closeButton.draw(app);

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
