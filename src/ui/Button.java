
//Basic button interaction, handles drawing and the click callback.
package ui;

import processing.core.PApplet;

public class Button {
    float x, y, w, h;
    String label;
    Runnable onClick;

    public Button(float x, float y, float w, float h, String label, Runnable onClick) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.label = label;
        this.onClick = onClick;
    }

    public void draw(PApplet app) {
        boolean hover = isMouseOver(app);

        app.stroke(255);
        app.fill(hover ? 80: 50);
        app.rect(x, y, w, h, 8);

        app.fill(255);
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.text(label, x + w / 2, y + h / 2);
    }

    public boolean isMouseOver(PApplet app) {
        return app.mouseX >= x && app.mouseX <= x + w &&
               app.mouseY >= y && app.mouseY <= y + h;
    }

    public void handleClick(PApplet app) {
        if (isMouseOver(app) && onClick != null) {
            onClick.run();
        }
    }

    public void drawGreyedOut(PApplet app, String subText) {
        app.pushStyle();
        app.fill(100);
        app.rect(x, y, w, h);
        app.fill(180);
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.text(label + " (" + subText + ")", x + w / 2f, y + h / 2f);
        app.popStyle();
    }

    public void drawActive(PApplet app, String subText) {
        app.pushStyle();
        app.fill(0, 180, 255);
        app.rect(x, y, w, h);
        app.fill(255);
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.text(label + " - " + subText, x + w / 2f, y + h / 2f);
        app.popStyle();
    }


}
