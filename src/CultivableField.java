import javafx.scene.control.Button;

public class CultivableField {
    private Plant currentPlant;
    private boolean isOccupied = false;
    private double growthProgress = 0.0;
    private boolean isReady = false;


    public String getStyle() {
        String baseStyle = "-fx-background-radius: 5; -fx-text-fill: white; -fx-font-weight: bold; ";

        if (!isOccupied) {
            // Marron
            return baseStyle + "-fx-background-color: #8B4513;";
        } else if (!isReady) {
            // Jaune
            return baseStyle + "-fx-background-color: #F1C40F; -fx-text-fill: black;";
        } else {
            // Vert
            return baseStyle + "-fx-background-color: #27ae60;";
        }
    }

    public void setupHover(Button btn, MainController game) {
        btn.setOnMouseEntered(e -> {
            btn.setOpacity(0.8);
            btn.setCursor(javafx.scene.Cursor.HAND);
        });

        btn.setOnMouseExited(e -> {
            btn.setOpacity(1.0);
            btn.setTranslateY(0);
            game.update();
        });
    }

    public String getText() {
        String name = (currentPlant instanceof Wheat) ? "Blé" : "Pastèque";
        int percent = (int)(growthProgress * 100);

        if (!isOccupied) return "VIDE";
        if (isReady) return name + " prêt";

        return name + " pousse" + "\n" + percent + "%";
    }


    public void updateProgress(Weather weather) {
        if (isOccupied && !isReady && currentPlant != null) {
            double speedWeather = currentPlant.getGrowthSpeed() * weather.getGrowthMultiplier();
            this.growthProgress += speedWeather;

            if (this.growthProgress >= 1.0) {
                this.growthProgress = 1.0;
                this.isReady = true;
            }
        }
    }

    public void harvestSystem(MainController game) {
        if (!this.isOccupied) {
            String selected = game.selectedSeed;
            if (selected.equals("wheat") && game.wheatSeeds > 0) {
                game.wheatSeeds -= 1;
                plant(new Wheat());
            } else if (selected.equals("watermelon") && game.waterMelonSeeds > 0) {
                game.waterMelonSeeds -= 1;
                plant(new WaterMelon());
            }
        } else if (this.isReady) {
            if (currentPlant instanceof Wheat) {
                game.wheatStock += 2;
                game.wheatSeeds += 1;
            } else if (currentPlant instanceof WaterMelon) {
                game.waterMelonStock += 1;
            }
            resetField();
        }
    }

    private void plant(Plant plant) {
        this.currentPlant = plant;
        this.isOccupied = true;
        this.growthProgress = 0.0;
        this.isReady = false;
    }

    private void resetField() {
        this.isOccupied = false;
        this.currentPlant = null;
        this.growthProgress = 0.0;
        this.isReady = false;
    }

    public boolean isOccupied() { return isOccupied; }
    public boolean isReady() { return isReady; }
}