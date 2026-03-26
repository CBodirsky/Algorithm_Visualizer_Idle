
//I keep forgetting this is down in visuals! Makes it fun figuring out where the routing goes.
//Only file at the moment in visuals, but handles logic related to the swap animations.
package visuals;

public class SwapAnimation {
    public boolean active;
    public int a, b;
    public int timer;
    public int duration = 15;

    public void start(int a, int b) {
        this.a = a;
        this.b = b;
        this.timer = 0;
        this.active = true;
    }

    public void update() {
        timer++;
        if (timer >= duration) active = false;
    }

    public float progress() {
        return active ? (float)timer / duration : 1.0f;
    }

}

