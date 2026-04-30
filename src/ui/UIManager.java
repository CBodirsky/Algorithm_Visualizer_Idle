
// Handles the UI overall.
package ui;

import core.Game;
import core.GameState;
import processing.core.PApplet;

public class UIManager {
    PApplet app;

    private SideMenu sideMenu;
    private TopBar topBar;
    private Game game;
    private boolean menuOpen = false;
    private Panel currentPanel = null;

    public float getTopBarHeight() {
        return topBar.height;
    }

    //Receives both the Game and PApplet, which handles the state and the drawing respectively.
    public UIManager(Game game, PApplet app) {
        this.game = game;
        this.app = app;
        //UI Components
        topBar = new TopBar(this);
        sideMenu = new SideMenu(this, app);
    }

    public Game getGame() {
        return game;
    }

    //Draws the UI in layers.
    public void draw(PApplet app, double money, int sorts, int prestige) {

        if (game.state == GameState.MENU) {
            if (currentPanel != null) {
                currentPanel.draw(app);
            }
            return;
        }
        // --- Layer 1: Top bar background ---
        topBar.drawBackground(app);

        // --- Layer 1.5: Button on initial tile to manual start a new sort.

        // --- Layer 2: Fullscreen panels (upgrade, tile mgmt, etc.) ---
        if (currentPanel != null) {
            currentPanel.draw(app);
        }

            // --- Layer 3: Top bar content (text + menu button) ---
            topBar.drawContent(app, money, sorts, prestige);

            // --- Layer 4: Side menu ---
            if (menuOpen) {
                sideMenu.draw(app);
            }
        }


    public boolean handleClick(PApplet app) {

        // Always let the top bar check the menu button FIRST
        if (topBar.isMenuButtonClicked(app)) {
            if (!menuOpen) {
                toggleMenu();
            } else {
                toggleMenu();
            }
            return true;
        }

        if (currentPanel != null) {
            currentPanel.handleClick(app);
            return true;
        }

        // Manual new sort button
// Allow clicking tiles when no panel or menu is open
        if (currentPanel == null && !menuOpen && game.state != GameState.MENU) {
            if (game.getGrid().handleClick(app)) {
                return true;
            }
        }


        if(game.state == GameState.MENU) {
            return false;
        }

        if (menuOpen) {
            sideMenu.handleClick(app);
            return true;
        }

        return false;
    }


    // --- State control ---
    public void openPanel(Panel panel) {
        menuOpen = false;
        currentPanel = panel;
    }

    public void toggleMenu() {
        if (currentPanel != null) return;
        menuOpen = !menuOpen;
    }

    public void startGame() {
        game.state = GameState.SORTING;
        closeCurrentPanel();
    }

    public void openTileUpgradePanel() {
        menuOpen = false;
        currentPanel = new UpgradePanel(this);
    }

    public void openRoundUpgradePanel() {
        menuOpen = false;
        currentPanel = new RoundUpgradePanel(this);
    }

    public void openPrestigePanel() {
        menuOpen = false;
        currentPanel = new PrestigePanel(this);
    }

    public void openMainSettingsPanel() {
        game.state = GameState.MENU;
        currentPanel = new SettingsPanel(this);
    }

    public void openSettingsPanel() {
        menuOpen = false;
        currentPanel = new SettingsPanel(this);
    }

    public void closeCurrentPanel() {
        currentPanel = null;
    }
}


