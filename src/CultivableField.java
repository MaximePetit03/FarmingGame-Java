public class CultivableField {
    private String currentSeed;
    private boolean isOccupied;
    private double growthProgress;
    private boolean isReady;

    public CultivableField() {
        this.isOccupied = false;
        this.growthProgress = 0.0;
        this.isReady = false;
    }

    // MÉTHODE POUR PLANTER
    public void plant(String selectedSeed) {
        if (this.isOccupied) return;

        this.currentSeed = selectedSeed;
        this.isOccupied = true;
        this.growthProgress = 0.0;
        this.isReady = false;
    }

    // MÉTHODE POUR FAIRE POUSSER
    public void incrementGrowth() {
        if (isOccupied && !isReady) {
            this.growthProgress += 0.1;

            if (this.growthProgress >= 1.0) {
                this.growthProgress = 1.0;
                this.isReady = true;
                System.out.println("La récolte est prête");
            }
        }
    }

    // MÉTHODE POUR RÉCOLTER
    public String harvest() {
        if (!isReady) {
            System.out.println("Pas encore");
            return null;
        }

        String product = this.currentSeed;

        // Reset
        this.isOccupied = false;
        this.currentSeed = null;
        this.growthProgress = 0.0;
        this.isReady = false;

        return product;
    }

    // Getter pour la ProgressBar du FXML
    public double getGrowthProgress() { return this.growthProgress; }
    public boolean isOccupied() { return isOccupied; }
    public boolean isReady() { return isReady; }
}