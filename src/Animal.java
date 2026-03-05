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
        if (!this.isFed) {

            // Récupère le type de nourriture
            String foodNeeded = this.getRequiredFood();

            // Nourrir
            if (foodNeeded.equals("wheat") && game.wheatStock > 0) {
                game.wheatStock -= 1;
                this.isFed = true;
                System.out.println(this.name + " a été nourri avec du blé.");
            }
        }
    }

    public void incrementProduction() {
        if (this.isFed && !this.isReady) {
            this.productionProgress += getProductionSpeed();
            if (this.productionProgress >= 1.0) {
                this.productionProgress = 1.0;
                this.isReady = true;
            }
        }
    }

    /*public void collectProduct(MainController game) {
        if (this.isReady) {
            if (this instanceof Cow) {
                game.milkStock += 1;
            }

            this.isFed = false;
            this.isReady = false;
            this.productionProgress = 0.0;
        }
    } */

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