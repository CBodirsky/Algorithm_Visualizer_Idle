package systems;

public class Upgrade {
    public final UpgradeType type;
    public final String name;
    public final String description;

    public int level = 0;
    public int maxLevel;
    public double baseCost;
    public double costMultiplier;

    public double effectPerLevel;

    public Upgrade(UpgradeType type, String name, String description,
                   int maxLevel, double baseCost, double costMultiplier,
                   double effectPerLevel) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.maxLevel = maxLevel;
        this.baseCost = baseCost;
        this.costMultiplier = costMultiplier;
        this.effectPerLevel = effectPerLevel;
    }

    public double getCost() {
        return baseCost * Math.pow(costMultiplier, level);
    }

    public boolean isMaxed() {
        return level >= maxLevel;
    }
}
