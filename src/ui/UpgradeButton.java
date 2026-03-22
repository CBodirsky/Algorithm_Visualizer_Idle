package ui;

import processing.core.PApplet;
import systems.Upgrade;
import core.Game;

public class UpgradeButton extends Button {

    private Upgrade upgrade;
    private UIManager ui;

    public UpgradeButton(int x, int y, int w, int h, Upgrade upgrade, UIManager ui) {
        super(x, y, w, h, upgrade.name, null);
        this.upgrade = upgrade;
        this.ui = ui;

        this.onClick = () -> {
            if (canPurchase()) {
                purchase();
            }
        };
    }

    private boolean canPurchase() {
        return !upgrade.isMaxed() &&
                ui.getGame().getCurrency().getMoney() >= upgrade.getCost();
    }

    private void purchase() {
        Game game = ui. getGame();

        game.getCurrency().spendMoney(upgrade.getCost());
        upgrade.level++;

        switch (upgrade.type) {
            case SORT_SPEED -> game.setStepDelay(Math.max(1, game.getStepDelay() - 1));
            case ARRAY_SIZE -> game.increaseArraySize(1);
            case PAYOUT_MULTIPLIER -> game.getCurrency().addMultiplier(0.1);
            case AUTO_SORT -> game.enableAutoSort();
        }
    }

    @Override
    public void draw(PApplet app) {
        if (upgrade.isMaxed()) {
            drawGreyedOut(app, "MAX");
        } else if (!canPurchase()) {
            drawGreyedOut(app, "$" + (int)upgrade.getCost());
        } else {
            drawActive(app, "$" + (int)upgrade.getCost());
        }
    }
}
