package models;

public class Inventory {
    public int money = 0;
    public int wheatSeeds = 6, wheatStock = 0;
    public int waterMelonSeeds = 0, waterMelonStock = 0;
    public int cowInventory = 0, milkStock = 0;
    public int chickenInventory = 0, eggStock = 0;
    public int goldenChickenInventory = 0, goldenEggStock = 0;

    public boolean canAfford(int amount) {
        return money >= amount;
    }
}