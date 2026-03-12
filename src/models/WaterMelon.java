package models;

public class WaterMelon extends Plant {
    public WaterMelon() {
        super("waterMelon", 15, 40);
    }

    @Override
    public double getGrowthSpeed() { return 0.05; }
}
