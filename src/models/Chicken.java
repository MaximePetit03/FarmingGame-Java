package models;

import controllers.MainController;

public class Chicken extends Animal {
    public Chicken() {
        super("Poulet", 80, 40);
    }

    @Override
    public String getRequiredFood() { return "wheatSeeds"; }

    @Override
    public double getProductionSpeed() { return 0.1; }

    @Override
    public void collectProduct(MainController game) {
        if (this.isReady) {
            game.inventory.eggStock += 1;
            resetAnimal();
            game.update();
        }
    }

    private void resetAnimal() {
        this.productionProgress = 0.0;
        this.isReady = false;
        this.isFed = false;
    }
}
