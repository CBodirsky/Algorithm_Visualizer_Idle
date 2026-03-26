
//The top bar with stats and basic information in gameplay.
package ui;

import processing.core.PApplet;

public class TopBar {
    public float height = 40;
    private Button menuButton;


    public TopBar(UIManager ui) {
        menuButton = new Button(10, 5, 60, 30, "Menu", ui::toggleMenu);
    }

    public void drawBackground(PApplet app) {
        app.pushStyle();
        app.fill(25);
        app.noStroke();
        app.rect(0, 0, app.width, height);
        app.popStyle();
    }

    public void drawContent(PApplet app, double money, int sorts, int prestige) {
        String moneyStr = String.format("%.2f", money);
        app.pushStyle();
        menuButton.draw(app);
        app.fill(255);
        app.textAlign(PApplet.LEFT, PApplet.CENTER);
        app.textSize(16);

        String text = "$" + moneyStr + "   |  Sorts: " + sorts + "  |  Prestige: " + prestige;
        app.text(text, 80, height / 2);
        app.popStyle();
    }

    public boolean isMenuButtonClicked(PApplet app) {
        return menuButton.isMouseOver(app);
    }

    public boolean handleClick(PApplet app) {
        menuButton.handleClick(app);
        return true;
    }
}

