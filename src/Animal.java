public abstract class Animal extends Item {
    public boolean isFed = false;
    public double productionProgress = 0.0;
    public boolean isReady = false;

    public Animal(String name, int buyPrice, int sellPrice) {
        super(name, buyPrice, sellPrice);
    }

    public abstract String getRequiredFood();
    public abstract double getProductionSpeed();

    public void feed(MainController game) {

    }

    public void incrementProduction() {

    }

    public void collectProduct(MainController game) {

    }

    // Getters
    public double getProductionProgress() { return productionProgress; }
    public boolean isReady() { return isReady; }
}

class Cow extends Animal {
    public Cow() {
        super("Vache", 150, 100);
    }
    @Override public String getRequiredFood() { return "wheat"; }
    @Override public double getProductionSpeed() { return 0.02; }
}