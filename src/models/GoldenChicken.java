package models;

import controllers.MainController;

public class GoldenChicken extends Animal {
    public GoldenChicken() {
        super("Poulet Doré", 1000, 500);
    }

    @Override
    public String getRequiredFood() { return "watermelonSeeds"; }

    @Override
    public double getProductionSpeed() { return 0.05; }

    @Override
    public void collectProduct(MainController game) {
        if (this.isReady) {
            if ((int)(Math.random() * 20) == 0) {
                game.inventory.goldenEggStock += 1;
            } else {
                game.inventory.eggStock += 1;
            }
            resetAnimal();
            game.update();
        }
    }

    public void resetAnimal() {
        this.productionProgress = 0.0;
        this.isReady = false;
        this.isFed = false;
    }
}
