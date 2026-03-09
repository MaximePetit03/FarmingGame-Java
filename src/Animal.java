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

        if (food.equals("watermelonSeeds") && game.waterMelonSeeds > 0) {
            game.waterMelonSeeds -= 1;
            this.isFed = true;
        }
        else if (food.equals("wheatSeeds") && game.wheatSeeds > 0) {
            game.wheatSeeds -= 1;
            this.isFed = true;
        }
        else if (food.equals("wheat") && game.wheatStock > 0) {
            game.wheatStock -= 1;
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
            game.money += 50;
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

class Cow extends Animal {
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

            game.milkStock += randomMilk;
            this.productionProgress = 0.0;
            this.isReady = false;
            this.isFed = false;
            game.update();
        }
    }
}

class Chicken extends Animal {
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
            game.eggStock += 1;
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

class GoldenChicken extends Animal {
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
                game.goldenEggStock += 1;
            } else {
                game.eggStock += 1;
            }
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

