public abstract class Plant {
    public int seedPrice;
    public int plantValue;
    public abstract String getName();
    public abstract double getGrowthSpeed();
}

class Corn extends Plant {
    @Override public String getName() { return "Corn"; }
    @Override public double getGrowthSpeed() { return 0.1; }
}

class WaterMelon extends Plant {
    @Override public String getName() { return "waterMelon"; }
    @Override public double getGrowthSpeed() { return 0.05; }
}