
//The upgrade buttons for each tile. Handles progression system that's not completed yet
//and also handles related algorithm switching.
package ui;

import processing.core.PApplet;
import systems.TileStats;
import systems.UpgradeType;

public class UpgradeButton extends Button {

    private final UpgradeType type;
    private final TileStats stats;
    private final UIManager ui;

    private final int tileR;
    private final int tileC;


    public UpgradeButton(int x, int y, int w, int h,
                         UpgradeType type, TileStats stats,
                         int tileR, int tileC, UIManager ui) {

        super(x, y, w, h, "", null);

        this.type = type;
        this.stats = stats;
        this.tileR = tileR;
        this.tileC = tileC;
        this.ui = ui;

        this.onClick = () -> {
            if (type == UpgradeType.ALGORITHM) {
                handleAlgorithmChange();
            } else if (canPurchase()) {
                purchase();
            }
        };
    }

    private void handleAlgorithmChange() {
        stats.algorithmType =
                (stats.algorithmType + 1) % ui.getGame().getAvailableAlgorithms();

        ui.getGame().getGrid().resetTile(tileR, tileC);
    }


    private boolean canPurchase() {
        double cost = getCost();
        return ui.getGame().getCurrency().canAfford(cost);
    }

    private double getCost() {
        return switch (type) {
            case ARRAY_SIZE -> 10 + stats.levelArraySize * 5;
            case SORT_SPEED -> 20 + stats.levelSortSpeed * 10;
            case PAYOUT_MULTIPLIER -> 30 + stats.levelPayout * 15;
            //Algorithm is a swap of what is being sorted, not an upgrade.
            //So no cost applied.
            case ALGORITHM -> 0;
            //Auto sort was initially planned as an upgrade, but in this
            //version, the progression system related to it is not implemented yet.
            case AUTO_SORT -> Double.POSITIVE_INFINITY; // not used in tile panel
        };
    }

    private boolean isMaxed() {
        return switch (type) {
            case ARRAY_SIZE -> stats.levelArraySize >= 100;
            case SORT_SPEED -> stats.levelSortSpeed >= 50;
            case PAYOUT_MULTIPLIER -> stats.levelPayout >= 100;
            case AUTO_SORT -> true; // always treated as maxed here
            case ALGORITHM -> false;
        };
    }

    private void purchase() {
        double cost = getCost();
        ui.getGame().getCurrency().spendMoney(cost);

        switch (type) {
            case ARRAY_SIZE -> {
                stats.arraySize++;
                stats.levelArraySize++;
            }
            case SORT_SPEED -> {
                stats.sortSpeed++;
                stats.levelSortSpeed++;
            }
            case PAYOUT_MULTIPLIER -> {
                stats.payoutMultiplier += 0.1;
                stats.levelPayout++;
            }
            case AUTO_SORT -> {
                // no-op in this panel; one-time upgrades will live elsewhere
            }
            case ALGORITHM -> {
                stats.algorithmType = (stats.algorithmType + 1) % ui.getGame().getAvailableAlgorithms();
                ui.getGame().getGrid().resetTile(tileR, tileC);
            }

        }
    }

    //Buttons are greyed out if not available for purchase.
    @Override
    public void draw(PApplet app) {
        if (type == UpgradeType.ALGORITHM) {
            drawAlgorithmButton(app);
            return;
        }
        if (isMaxed()) {
            drawGreyedOut(app, "MAX");
        } else if (!canPurchase()) {
            drawGreyedOut(app, "$" + (int)getCost());
        } else {
            drawActive(app, "$" + (int)getCost());
        }
    }

    private void drawAlgorithmButton(PApplet app) {
        app.pushStyle();

        app.fill(0, 180, 255);
        app.rect(x, y, w, h, 8);

        app.fill(255);
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.text("Change", x + w / 2f, y + h / 2f);

        app.popStyle();
    }

}
