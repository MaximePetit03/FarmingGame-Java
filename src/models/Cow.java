package models;

import controllers.MainController;

public class Cow extends Animal {
    public Cow() {
        super("Vache", 150, 75);
    }

    @Override
    public String getRequiredFood() { return "wheat"; }

    @Override
    public double getProductionSpeed() { return 0.05; }

    @Override
    public void collectProduct(MainController game) {
        if (this.isReady) {
            int randomMilk = (int)(Math.random() * 3 + 1);

            game.inventory.milkStock += randomMilk;
            this.productionProgress = 0.0;
            this.isReady = false;
            this.isFed = false;
            game.update();
        }
    }
}
