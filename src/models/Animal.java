package models;

import controllers.MainController;

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
        if (this.isFed) return;

        String food = getRequiredFood();

        if (food.equals("watermelonSeeds") && game.inventory.waterMelonSeeds > 0) {
            game.inventory.waterMelonSeeds -= 1;
            this.isFed = true;
        }
        else if (food.equals("wheatSeeds") && game.inventory.wheatSeeds > 0) {
            game.inventory.wheatSeeds -= 1;
            this.isFed = true;
        }
        else if (food.equals("wheat") && game.inventory.wheatStock > 0) {
            game.inventory.wheatStock -= 1;
            this.isFed = true;
        }
    }

    public void incrementProduction() {
        if (isFed && !isReady) {
            this.productionProgress += getProductionSpeed();
            if (this.productionProgress >= 1.0) {
                this.productionProgress = 1.0;
                this.isReady = true;
            }
        }
    }

    public void collectProduct(MainController game) {
        if (isReady) {
            game.inventory.money += 50;
            this.productionProgress = 0.0;
            this.isReady = false;
            this.isFed = false;
        }
    }

    public String getStyle() {
        String base = "-fx-background-radius: 10; -fx-font-weight: bold; -fx-text-alignment: center; ";
        if (!isFed) return base + "-fx-background-color: #e67e22; -fx-text-fill: white;"; // Orange
        if (isReady) return base + "-fx-background-color: #27ae60; -fx-text-fill: white;"; // Vert
        return base + "-fx-background-color: #3498db; -fx-text-fill: white;"; // Bleu
    }

    public String getText() {
        if (!isFed) return name + "\n peut manger";
        if (isReady) return name + "\nterminé";
        return name + "\n" + (int)(productionProgress * 100) + "%";
    }
}

