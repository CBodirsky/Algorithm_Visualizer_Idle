package ui;

import processing.core.PApplet;

public class StartMenuPanel extends Panel {

    private UIManager ui;
    private Button startButton;
    private Button settingsButton;
    private Button exitButton;

    public StartMenuPanel(UIManager ui) {
        this.ui = ui;

        startButton = new Button(
                ui.app.width / 2f - 125, ui.app.height / 2f + 100, 250, 50,
                "Start Game",
                ui::startGame
        );

//        settingsButton = new Button(
//                100, 270, 250, 50,
//                "Settings",
//                () -> ui.openPanel(new SettingsPanel(ui))
//        );

        exitButton = new Button(
                ui.app.width / 2f - 125, ui.app.height / 2f + 200, 250, 50,
                "Exit",
                () -> ui.app.exit()
        );
    }

    @Override
    public void draw(PApplet app) {
        app.pushStyle();

        // Background
        app.background(20);

        // --- Animated Sorting Bars ---
        float baseX = app.width / 2f - 150;
        float baseY = 400;
        float barWidth = 40;

        int[] colors = {
                app.color(70, 130, 180),  // blue-ish
                app.color(200, 80, 80),   // red-ish
                app.color(120, 200, 120), // green-ish
                app.color(180, 180, 80),  // yellow-ish
                app.color(160, 100, 200), // purple-ish
                app.color(100, 180, 200)  // teal-ish
        };

        for (int i = 0; i < 6; i++) {
            float h = 80 + 40 * PApplet.sin(app.frameCount * 0.03f + i);
            float x = baseX + i * (barWidth + 10);
            float y = baseY - h;

            app.fill(colors[i]);
            app.rect(x, y, barWidth, h, 5);
        }


        // --- Title ---
        app.fill(255);
        app.textAlign(PApplet.CENTER, PApplet.TOP);
        app.textSize(64);
        app.text("Sorting Algorithm Idle", app.width / 2f, app.height / 2f - 200);

        // --- Subtitle ---
        app.fill(180);
        app.textSize(20);
        app.text("A visualization & progression project", app.width / 2,  app.height / 2f - 100);

        // --- Buttons ---
        startButton.draw(app);
        exitButton.draw(app);

        app.popStyle();
    }


    @Override
    public void handleClick(PApplet app) {
        if (startButton.isMouseOver(app)) startButton.handleClick(app);
//        if (settingsButton.isMouseOver(app)) settingsButton.handleClick(app);
        if (exitButton.isMouseOver(app)) exitButton.handleClick(app);
    }
}
