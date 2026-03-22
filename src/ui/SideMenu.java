package ui;

import processing.core.PApplet;

public class SideMenu {
    Button upgradesButton;
    Button tileButton;
    Button settingsButton;

    public SideMenu(UIManager ui) {
        upgradesButton = new Button(20, 80, 150, 40, "Upgrades", ui::openUpgradePanel);
        tileButton     = new Button(20, 130, 150, 40, "Tiles", () -> { /* open tile panel */ });
        settingsButton = new Button(20, 180, 150, 40, "Settings", () -> { /* open settings */ });
    }

    public void draw(PApplet app) {
        app.pushStyle();
        app.fill(30, 200);
        app.rect(0, 40, 200, app.height - 40);

        upgradesButton.draw(app);
        tileButton.draw(app);
        settingsButton.draw(app);
        app.popStyle();
    }

    public void handleClick(PApplet app) {
        upgradesButton.handleClick(app);
        tileButton.handleClick(app);
        settingsButton.handleClick(app);
    }
}

