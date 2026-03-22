package visuals;

public class SwapAnimation {
    public boolean active;
    public int a, b;
    public int timer;
    public int duration = 15;

    public void start(int a, int b) {
        this.a = a;
        this.b = b;
        timer = 0;
        active = true;
    }

    public void update() {
        timer++;
        if (timer >= duration) active = false;
    }
}
