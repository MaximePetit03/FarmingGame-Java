package models;

public abstract class Plant extends Item {
    public int seedPrice;
    public int plantValue;

    public Plant(String name, int buyPrice, int sellPrice) {
        super(name, buyPrice, sellPrice);
    }

    public abstract double getGrowthSpeed();
}