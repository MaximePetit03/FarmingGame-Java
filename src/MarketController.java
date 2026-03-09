import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MarketController {

    @FXML private Label itemNameLabel;

    @FXML private Label buyQuantityLabel;
    @FXML private Label sellQuantityLabel;
    @FXML private Label totalBuyPriceLabel;
    @FXML private Label totalSellPriceLabel;

    private Market market = new Market();
    private MainController mainGame;

    public void setMainController(MainController game) {
        this.mainGame = game;
    }

    @FXML private void selectWheatSeed() {
        market.selectProduct("WheatSeed", 5, 2);
        itemNameLabel.setText("Graines de Blé");
        updateUI();
    }
    @FXML private void selectWaterMelonSeed() {
        if (!mainGame.isWaterMelonUnlocked) {
            market.selectProduct("WaterMelonSeed", 250, 0);
            itemNameLabel.setText("Graines de Pastèque (VERROUILLÉES)");
        } else {
            market.selectProduct("WaterMelonSeed", 15, 7);
            itemNameLabel.setText("Graines de Pastèque");
        }
        updateUI();
    }
    @FXML private void selectWheatFood() {
        market.selectProduct("WheatFood", 12, 10);
        itemNameLabel.setText("Blé");
        updateUI();
    }
    @FXML private void selectWaterMelonFood() {
        market.selectProduct("WaterMelonFood", 40, 25);
        itemNameLabel.setText("Pastèque");
        updateUI();
    }
    @FXML private void selectCow() {
        if (!mainGame.isCowUnlocked) {
            market.selectProduct("Cow", 1000, 0);
            itemNameLabel.setText("Vache (VERROUILLÉE)");
        } else {
            market.selectProduct("Cow", 150, 75);
            itemNameLabel.setText("Vache");
        }
        updateUI();
    }
    @FXML private void selectMilk() {
        market.selectProduct("Milk", 40, 20);
        itemNameLabel.setText("Lait");
        updateUI();
    }

    @FXML private void plusBuyQuantity() { market.incrementBuyQuantity(); updateUI(); }
    @FXML private void minusBuyQuantity() { market.decrementBuyQuantity(); updateUI(); }

    @FXML private void plusSellQuantity() { market.incrementSellQuantity(); updateUI(); }
    @FXML private void minusSellQuantity() { market.decrementSellQuantity(); updateUI(); }

    private void handleUnlock(int price, String type) {
        if (mainGame.money >= price) {
            mainGame.money -= price;
            if (type.equals("isCowUnlocked")) {
                mainGame.isCowUnlocked = true;
                selectCow();
            } else {
                mainGame.isWaterMelonUnlocked = true;
                selectWaterMelonSeed();
            }
            mainGame.saveGame();
        } else {
            itemNameLabel.setText("Pas assez d'émeraudes (" + price + ") !");
        }
    }

    @FXML private void confirmPurchase() {
        if (mainGame != null) {
            String currentProduct = market.getSelectedProductName();

            if ("Cow".equals(currentProduct) && !mainGame.isCowUnlocked) {
                handleUnlock(1000, "isCowUnlocked");
            }
            else if ("WaterMelonSeed".equals(currentProduct) && !mainGame.isWaterMelonUnlocked) {
                handleUnlock(250, "isWaterMelonUnlocked");
            }
            else {
                market.confirmPurchase(mainGame);
                mainGame.saveGame();
            }

            updateUI();
            mainGame.update();
        }
    }

    @FXML private void confirmSale() {
        if (mainGame != null) {
            market.confirmSale(mainGame);
            mainGame.saveGame();
            updateUI();
        }
    }

    private void updateUI() {
        buyQuantityLabel.setText(String.valueOf(market.getBuyQuantity()));
        sellQuantityLabel.setText(String.valueOf(market.getSellQuantity()));

        totalBuyPriceLabel.setText(market.getTotalBuyPrice() + " émeraudes");
        totalSellPriceLabel.setText(market.getTotalSellPrice() + " émeraudes");
    }
}