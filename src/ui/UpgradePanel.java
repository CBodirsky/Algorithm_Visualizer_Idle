package ui;

import processing.core.PApplet;
import java.util.List;
import java.util.ArrayList;

import systems.Upgrade;
import ui.UpgradeButton;

public class UpgradePanel extends Panel {

    private Button closeButton;
    private List<UpgradeButton> upgradeButtons = new ArrayList<>();
    private UIManager ui;

    public UpgradePanel(UIManager ui) {
        this.ui = ui;

        closeButton = new Button(
                20, 60, 120, 40,
                "Close",
                ui::closeUpgradePanel
        );
        int y = 120;

        //Button for each upgrade in system
        for (Upgrade upgrade : ui.getGame().getUpgrades().getAll()) {
            upgradeButtons.add(
                    new UpgradeButton(50, y, 300, 40, upgrade, ui)
            );
            y += 50;
        }
    }

    @Override
    public void draw(PApplet app) {
        app.pushStyle();
        // Semi-transparent full-screen overlay (below top bar)
        app.fill(30, 200);
        app.noStroke();
        app.rect(0, 40, app.width, app.height - 40);

        // Title
        app.fill(255);
        app.textAlign(PApplet.CENTER, PApplet.TOP);
        app.textSize(28);
        app.text("Upgrades", app.width / 2f, 50);

        // Close button
        closeButton.draw(app);
        for (UpgradeButton b : upgradeButtons) {
            b.draw(app);
        }
        app.popStyle();
    }

    @Override
    public void handleClick(PApplet app) {
        //Close button
        if (closeButton.isMouseOver(app)) {
            closeButton.handleClick(app);
            return;
        }

        //Upgrade buttons
        for (UpgradeButton b : upgradeButtons) {
            if (b.isMouseOver(app)) {
                b.handleClick(app);
                return;
            }
        }
    }
}
