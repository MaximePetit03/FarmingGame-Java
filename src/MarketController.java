import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MarketController {

    @FXML private Label itemNameLabel;
    @FXML private Label quantityLabel;
    @FXML private Label totalPriceLabel;

    private Market market = new Market();
    private MainController mainGame;

    public void setMainController(MainController game) {
        this.mainGame = game;
    }

    // --- SÉLECTION DES PRODUITS ---

    @FXML private void selectWheatSeed() {
        market.selectProduct("WheatSeed", 5);
        itemNameLabel.setText("Graines de Blé");
        updateUI();
    }

    @FXML private void selectWaterMelonSeed() {
        market.selectProduct("WaterMelonSeed", 15);
        itemNameLabel.setText("Graines de Pastèque");
        updateUI();
    }

    @FXML private void selectWheatFood() {
        market.selectProduct("WheatFood", 10);
        itemNameLabel.setText("Blé (Récolte)");
        updateUI();
    }

    @FXML private void selectWaterMelonFood() {
        market.selectProduct("WaterMelonFood", 25);
        itemNameLabel.setText("Pastèque (Récolte)");
        updateUI();
    }

    @FXML private void selectCow() {
        market.selectProduct("Cow", 150);
        itemNameLabel.setText("Vache");
        updateUI();
    }

    @FXML private void selectMilk() {
        market.selectProduct("Milk", 40);
        itemNameLabel.setText("Lait (Seau)");
        updateUI();
    }

    // --- BOUTONS D'ACTION ---

    @FXML private void plusQuantity() {
        market.incrementQuantity();
        updateUI();
    }

    @FXML private void minusQuantity() {
        market.decrementQuantity();
        updateUI();
    }

    @FXML
    private void confirmPurchase() {
        if (mainGame != null) {
            // C'est ici que la classe Market appellera mainGame.addAnimalToEnclosure(new Cow())
            market.confirmPurchase(mainGame);
            mainGame.saveGame();
            updateUI();
        }
    }

    @FXML private void confirmSale() {
        if (mainGame != null) {
            market.confirmSale(mainGame);
            updateUI();
        }
    }

    private void updateUI() {
        quantityLabel.setText(String.valueOf(market.getQuantity()));
        totalPriceLabel.setText(market.getTotalPrice() + " émeraudes");
    }
}