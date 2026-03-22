package ui;

import processing.core.PApplet;

public abstract class Panel {
    public abstract void draw(PApplet app);
    public abstract void handleClick(PApplet app);
}
