
//Code for the side menu allowing access to other panels.
package ui;

import processing.core.PApplet;

public class SideMenu {
    PApplet app;

    Button upgradesButton;
    Button tileButton;
    Button prestigeButton;
    Button settingsButton;
    Button exitButton;

    public SideMenu(UIManager ui, PApplet app) {
        this.app = app;
        upgradesButton = new Button(20, 80, 150, 40, "Upgrades", ui::openRoundUpgradePanel );
        tileButton     = new Button(20, 130, 150, 40, "Tiles", ui::openTileUpgradePanel );
        prestigeButton     = new Button(20, 180, 150, 40, "Prestige", ui::openPrestigePanel );
        settingsButton = new Button(20, 230, 150, 40, "Settings", ui::openSettingsPanel);
        exitButton = new Button(20, 280, 150, 40, "Exit", () -> app.exit());
    }

    public void draw(PApplet app) {
        app.pushStyle();
        app.fill(30, 200);
        app.rect(0, 40, 200, app.height - 40);

        upgradesButton.draw(app);
        tileButton.draw(app);
        prestigeButton.draw(app);
        settingsButton.draw(app);
        exitButton.draw(app);

        app.popStyle();
    }

    public void handleClick(PApplet app) {
        upgradesButton.handleClick(app);
        tileButton.handleClick(app);
        prestigeButton.handleClick(app);
        settingsButton.handleClick(app);
        exitButton.handleClick(app);
    }
}

