package systems;

public class CurrencySystem {

    private double money = 100000;
    private double basePayment = 1.0;
    private double moneyMultiplier = 1.0;

    public void addMoney(double amount) {
        money += amount * moneyMultiplier;
    }

    public double getMoney() {
        return money;
    }

    public void spendMoney(double amount) {
        money -= amount;
    }

    public boolean canAfford(double amount) {
        return money >= amount;
    }

    public void addMultiplier(double amount) {
        moneyMultiplier += amount;
    }

    public double getRewardForSort(int arraySize) {
        // Simple formula for now, scalable later
        return basePayment * arraySize;
    }
}
