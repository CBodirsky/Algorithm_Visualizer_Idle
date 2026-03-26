
//Manages upgrades, which at the moment is purposefully unbalanced.
//Initializes all upgrade types with their max levels, costs, and effects.
package systems;

import core.Game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UpgradeSystem {

    private Map<UpgradeType, Upgrade> upgrades = new HashMap<>();
    int maxSortSpeedLevels = Game.STARTING_STEP_DELAY - 1;


    public UpgradeSystem() {
        upgrades.put(UpgradeType.SORT_SPEED,
                new Upgrade(
                        UpgradeType.SORT_SPEED,
                        "Sort Speed",
                        "Reduces delay between operations.",
                        maxSortSpeedLevels, 5, 1.15, 1
                )
        );

        upgrades.put(UpgradeType.ARRAY_SIZE,
                new Upgrade(
                        UpgradeType.ARRAY_SIZE,
                        "Array Size",
                        "Increases number of elements.",
                        100, 10, 1.2, 1
                )
        );

        upgrades.put(UpgradeType.PAYOUT_MULTIPLIER,
                new Upgrade(
                        UpgradeType.PAYOUT_MULTIPLIER,
                        "Payout Multiplier",
                        "Increases money earned per sort.",
                        100, 10, 1.25, 0.1
                )
        );

        upgrades.put(UpgradeType.AUTO_SORT,
                new Upgrade(
                        UpgradeType.AUTO_SORT,
                        "Auto Sort",
                        "Automatically starts a new sort after each finish.",
                        1, 50, 1.0, 0
                )
        );
    }

    public Upgrade get(UpgradeType type) {
        return upgrades.get(type);
    }

    public Collection<Upgrade> getAll() {
        return upgrades.values();
    }
}
