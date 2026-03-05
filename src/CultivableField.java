public class CultivableField {
    private Plant currentPlant;
    private boolean isOccupied = false;
    private double growthProgress = 0.0;
    private boolean isReady = false;

    // --- MÉTHODES UTILITAIRES POUR L'AFFICHAGE ---

    // Utilisé par le MainController pour rafraîchir le texte du bouton
    public String getStateText() {
        if (!isOccupied) return "Vide (Planter)";
        if (isReady) return getPlantName() + " (Récolter !)";
        return getPlantName() + " (Pousse...)";
    }

    // Utilisé par la boucle de jeu (Timeline) du MainController
    public void updateProgress() {
        if (isOccupied && !isReady && currentPlant != null) {
            this.growthProgress += currentPlant.getGrowthSpeed();
            if (this.growthProgress >= 1.0) {
                this.growthProgress = 1.0;
                this.isReady = true;
            }
        }
    }

    // Retourne le progrès (0.0 à 1.0) pour la ProgressBar
    public double getProgress() {
        return growthProgress;
    }

    // --- LOGIQUE MÉTIER ---

    public String getPlantName() {
        if (currentPlant != null) {
            String name = currentPlant.name;
            if (name.equalsIgnoreCase("wheat")) return "Blé";
            if (name.equalsIgnoreCase("waterMelon")) return "Pastèque";
            return name;
        }
        return "";
    }

    public void harvestSystem(MainController game) {
        if (!this.isOccupied) {
            // On récupère la graine sélectionnée dans l'interface
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

    // Getters
    public boolean isOccupied() { return isOccupied; }
    public boolean isReady() { return isReady; }
}