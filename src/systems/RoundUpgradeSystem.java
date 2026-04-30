package systems;

public class RoundUpgradeSystem {

    public static class UpgradeNode {
        public String name;
        public boolean purchased = false;
        public int cost;
        public UpgradeNode next;

        public UpgradeNode(String name, int cost) {
            this.name = name;
            this.cost = cost;
        }
    }

    public UpgradeNode autoSort;
    public UpgradeNode doublePayout;
    public UpgradeNode prestige;

    public RoundUpgradeSystem() {
        autoSort = new UpgradeNode("Auto Sort", 50);
        doublePayout = new UpgradeNode("Double Payout", 200);
        prestige = new UpgradeNode("Prestige", 1000);

        autoSort.next = doublePayout; // linked list progression
        doublePayout.next = prestige;
    }

    public boolean tryPurchase(UpgradeNode node, CurrencySystem currency) {

        // Already bought?
        if (node.purchased) return false;

        // Not enough money?
        if (currency.getMoney() < node.cost) return false;

        // Check prerequisite: if this node has a previous node, it must be purchased
        // (We detect this by scanning from the head)
        UpgradeNode current = autoSort;
        while (current != null && current != node) {
            if (!current.purchased) return false; // prerequisite not met
            current = current.next;
        }

        // Purchase it
        currency.spendMoney(node.cost);
        node.purchased = true;
        return true;
    }

        public void reset() {
        autoSort.purchased = false;
        doublePayout.purchased = false;
        prestige.purchased = false;
    }


}

