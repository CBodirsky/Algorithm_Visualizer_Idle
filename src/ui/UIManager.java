package ui;

import core.Game;
import processing.core.PApplet;

public class UIManager {

    private SideMenu sideMenu;
    private UpgradePanel upgradePanel;
    private TopBar topBar;
    private Button newSortButton;
    private Game game;
    private boolean menuOpen = false;
    private boolean upgradeOpen = false;

    public float getTopBarHeight() {
        return topBar.height;
    }

    public UIManager(Game game) {
        this.game = game;
        //UI Components
        topBar = new TopBar(this);
        sideMenu = new SideMenu(this);
        upgradePanel = new UpgradePanel(this);

        //New Sort button
        newSortButton = new Button(
                20, 50, 150, 40, "New Sort",
                () -> game.startNewSort()
        );
    }

    public Game getGame() {
        return game;
    }

    public void draw(PApplet app, double money, int sorts, int prestige) {

        // --- Layer 1: Top bar background ---
        topBar.drawBackground(app);

        if(game.isSortFinished() && !upgradeOpen && !menuOpen) {
            newSortButton.draw(app);
        }

        // --- Layer 2: Fullscreen panels (upgrade, tile mgmt, etc.) ---
        if (upgradeOpen) {
            upgradePanel.draw(app);
        }

        // --- Layer 3: Top bar content (text + menu button) ---
        topBar.drawContent(app, money, sorts, prestige);

        // --- Layer 4: Side menu ---
        if (menuOpen) {
            sideMenu.draw(app);
        }
    }

    public boolean handleClick(PApplet app) {

        if (upgradeOpen) {
            upgradePanel.handleClick(app);
            return true;
        }

        if (menuOpen) {
            sideMenu.handleClick(app);
            return true;
        }

        if (game.isSortFinished() && !upgradeOpen && !menuOpen) {
            if (newSortButton.isMouseOver(app)) {
                newSortButton.handleClick(app);
                return true;
            }
        }
        return topBar.handleClick(app);
    }

    // --- State control ---
    public void toggleMenu() {
        if (upgradeOpen) return;
        menuOpen = !menuOpen;
    }

    public void openUpgradePanel() {
        menuOpen = false;
        upgradeOpen = true;
    }

    public void closeUpgradePanel() {
        upgradeOpen = false;
    }
}

