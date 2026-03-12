package models;

public class Wheat extends Plant {
    public Wheat() {
        super("wheat", 5, 12);
    }

    @Override
    public double getGrowthSpeed() { return 0.1; }
}
