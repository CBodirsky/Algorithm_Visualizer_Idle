
//Full screen panel that lists upgrades for each tile and the related
//algorithm swap option into a table layout.
package ui;

import processing.core.PApplet;
import java.util.ArrayList;
import java.util.List;

import systems.TileGrid;
import systems.TileStats;
import systems.UpgradeType;

public class RoundUpgradePanel extends Panel {

    private Button closeButton;
    private Button autoSortButton;
    private Button doublePayoutButton;
    private Button prestigeButton;
    private List<UpgradeButton> upgradeButtons = new ArrayList<>();
    private List<Integer> tileLabelY = new ArrayList<>();
    private UIManager ui;

    public RoundUpgradePanel(UIManager ui) {
        this.ui = ui;

        autoSortButton = new Button(
                100, 150, 400, 40,
                "Auto Sort",
                () -> {
                    var upgrades = ui.getGame().roundUpgrades;
                    var currency = ui.getGame().getCurrency();
                    if (upgrades.tryPurchase(upgrades.autoSort, currency)) {
                        ui.getGame().getGrid().restartWaitingTiles();
                        ui.closeCurrentPanel();
                    }
                }
        );

        doublePayoutButton = new Button(
                100, 220, 400, 40,
                "Double Payout",
                () -> {
                    var upgrades = ui.getGame().roundUpgrades;
                    var currency = ui.getGame().getCurrency();
                    if (upgrades.tryPurchase(upgrades.doublePayout, currency)) {
                        ui.closeCurrentPanel();
                    }
                }
        );

        prestigeButton = new Button(
                100, 290, 400, 40,
                "Prestige",
                () -> {
                    var upgrades = ui.getGame().roundUpgrades;
                    var currency = ui.getGame().getCurrency();
                    if (upgrades.tryPurchase(upgrades.prestige, currency)) {
                        currency.applyPrestigeBonus();
                        upgrades.reset(); // reset round upgrades
                        ui.getGame().getGrid().resetAllTiles();
                        ui.getGame().unlockedTiles++;
                        ui.closeCurrentPanel();
                    }
                }
        );


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

        if (ui.getGame().roundUpgrades.autoSort.purchased) {
            autoSortButton.drawGreyedOut(app, "Purchased");
        } else {
            autoSortButton.draw(app);
        }

        if (!ui.getGame().roundUpgrades.autoSort.purchased) {
            doublePayoutButton.drawGreyedOut(app, "Buy above");
        } else if (ui.getGame().roundUpgrades.doublePayout.purchased) {
            doublePayoutButton.drawGreyedOut(app, "Purchased");
        } else {
            doublePayoutButton.draw(app);
        }

        var ups = ui.getGame().roundUpgrades;

        if (!ups.doublePayout.purchased) {
            prestigeButton.drawGreyedOut(app, "Buy above");
        } else {
            prestigeButton.draw(app);
        }


        for (UpgradeButton b : upgradeButtons) {
            b.draw(app);
        }
        app.popStyle();
    }



    //Click handling logic.
    @Override
    public void handleClick(PApplet app) {

        if (!ui.getGame().roundUpgrades.autoSort.purchased && autoSortButton.isMouseOver(app)) {
            autoSortButton.handleClick(app);
            return;
        }

        if (ui.getGame().roundUpgrades.autoSort.purchased && !ui.getGame().roundUpgrades.doublePayout.purchased &&
            doublePayoutButton.isMouseOver(app)) {
            doublePayoutButton.handleClick(app);
            return;
        }

        if (!ui.getGame().roundUpgrades.prestige.purchased && prestigeButton.isMouseOver(app)) {
            prestigeButton.handleClick(app);
        }


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
