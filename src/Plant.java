public abstract class Plant extends Item{
    public int seedPrice;
    public int plantValue;

    public Plant(String name, int buyPrice, int sellPrice) {
        super(name, buyPrice, sellPrice);
    }

    public abstract double getGrowthSpeed();
}

class Wheat extends Plant {
    public Wheat() {
        super("wheat", 5, 12);
    }

    @Override
    public double getGrowthSpeed() { return 0.1; }
}

class WaterMelon extends Plant {
    public WaterMelon() {
        super("waterMelon", 15, 40);
    }

    @Override
    public double getGrowthSpeed() { return 0.05; }
}