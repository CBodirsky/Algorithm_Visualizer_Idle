package ui;

import processing.core.PApplet;

public class SideMenu {
    PApplet app;

    Button upgradesButton;
    Button tileButton;
    Button settingsButton;
    Button exitButton;

    public SideMenu(UIManager ui, PApplet app) {
        this.app = app;
        upgradesButton = new Button(20, 80, 150, 40, "Upgrades", ui::openUpgradePanel);
        tileButton     = new Button(20, 130, 150, 40, "Tiles", () -> { /* open tile panel */ });
        settingsButton = new Button(20, 180, 150, 40, "Settings", () -> { /* open settings */ });
        exitButton = new Button(20, 230, 150, 40, "Exit", () -> app.exit());
    }

    public void draw(PApplet app) {
        app.pushStyle();
        app.fill(30, 200);
        app.rect(0, 40, 200, app.height - 40);

        upgradesButton.draw(app);
        tileButton.draw(app);
        settingsButton.draw(app);
        exitButton.draw(app);

        app.popStyle();
    }

    public void handleClick(PApplet app) {
        upgradesButton.handleClick(app);
        tileButton.handleClick(app);
        settingsButton.handleClick(app);
        exitButton.handleClick(app);
    }
}

