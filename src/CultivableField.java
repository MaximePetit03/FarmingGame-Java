public class CultivableField {
    private Plant currentPlant;
    private boolean isOccupied = false;
    private double growthProgress = 0.0;
    private boolean isReady = false;

    public String getPlantName() {
        if (currentPlant != null) {
            String name = currentPlant.getName();
            if (name.equals("Corn")) return "Maïs";
            if (name.equals("waterMelon")) return "Pastèque";
            return name;
        }
        return "";
    }

    public void harvestSystem(MainController game) {
        if (!this.isOccupied) {
            if (game.getSelectedSeed().equals("Corn") && game.cornSeeds > 0) {
                game.cornSeeds -= 1;
                plant(new Corn());
            } else if (game.getSelectedSeed().equals("waterMelon") && game.waterMelonSeeds > 0) {
                game.waterMelonSeeds -= 1;
                plant(new WaterMelon());
            }
        } else if (this.isReady) {
            if (currentPlant instanceof Corn) {
                game.cornStock += 2;
                game.cornSeeds += 1;
            } else if (currentPlant instanceof WaterMelon) {
                game.waterMelonStock += 5;
            }
            resetField();
        }
        game.update();
    }

    private void plant(Plant plant) {
        this.currentPlant = plant;
        this.isOccupied = true;
        this.growthProgress = 0.0;
        this.isReady = false;
    }

    public void incrementGrowth() {
        if (isOccupied && !isReady) {
            this.growthProgress += currentPlant.getGrowthSpeed();
            if (this.growthProgress >= 1.0) {
                this.growthProgress = 1.0;
                this.isReady = true;
            }
        }
    }

    private void resetField() {
        this.isOccupied = false;
        this.currentPlant = null;
        this.growthProgress = 0.0;
        this.isReady = false;
    }

    public double getGrowthProgress() { return growthProgress; }
    public boolean isOccupied() { return isOccupied; }
    public boolean isReady() { return isReady; }
}