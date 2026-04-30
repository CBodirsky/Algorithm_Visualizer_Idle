package ui;

import processing.core.PApplet;

public class SettingsPanel extends Panel {

    private final UIManager ui;

    private Button closeButton;

    public SettingsPanel(UIManager ui) {
        this.ui = ui;

        closeButton = new Button(
                100, 60, 120, 40,
                "Close",
                ui::closeCurrentPanel
        );
    }

    @Override
    public void draw(PApplet app) {
        app.pushStyle();

        // Background overlay
        app.fill(30, 200);
        app.noStroke();
        app.rect(0, 40, app.width, app.height - 40);

        // Title
        app.fill(255);
        app.textAlign(PApplet.LEFT, PApplet.TOP);
        app.textSize(28);
        app.text("Settings", 100, 150);

        // Close button
        closeButton.draw(app);

        app.popStyle();
    }

    @Override
    public void handleClick(PApplet app) {

        if (closeButton.isMouseOver(app)) {
            closeButton.handleClick(app);
            return;
        }
    }
}
