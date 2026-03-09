import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MarketController {

    @FXML private Label itemNameLabel;
    @FXML private Label buyQuantityLabel;
    @FXML private Label sellQuantityLabel;
    @FXML private Label totalBuyPriceLabel;
    @FXML private Label totalSellPriceLabel;

    @FXML private Button selectWaterMelonBtn;
    @FXML private Button selectCowBtn;
    @FXML public Button selectChickenBtn;
    @FXML public Button selectGoldenChickenBtn;
    @FXML private Button confirmPurchaseBtn;

    private Market market = new Market();
    private MainController mainGame;

    public void setMainController(MainController game) {
        this.mainGame = game;
        updateUI();
    }

    @FXML private void selectWheatSeed() {
        market.selectProduct("WheatSeed", 5, 2);
        itemNameLabel.setText("Graines de Blé");
        updateUI();
    }
    @FXML private void selectWaterMelonSeed() {
        if (!mainGame.waterMelonUnlocked) {
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
        market.selectProduct("WaterMelonFood", 40, 35);
        itemNameLabel.setText("Pastèque");
        updateUI();
    }
    @FXML private void selectCow() {
        if (!mainGame.cowUnlocked) {
            market.selectProduct("Cow", 1000, 0);
            itemNameLabel.setText("Vache (VERROUILLÉE)");
        } else {
            market.selectProduct("Cow", 150, 75);
            itemNameLabel.setText("Vache");
        }
        updateUI();
    }

    @FXML private void selectMilk() {
        market.selectProduct("Milk", 60, 55);
        itemNameLabel.setText("Lait");
        updateUI();
    }

    @FXML
    private void selectChicken() {
        if (mainGame != null) {
            if (!mainGame.chickenUnlocked) {
                market.selectProduct("Chicken", 500, 0);
                itemNameLabel.setText("Poulet (VERROUILLÉ)");
            } else {
                market.selectProduct("Chicken", 80, 40);
                itemNameLabel.setText("Poulet");
            }
            updateUI();
        }
    }

    @FXML
    private void selectGoldenChicken() {
        if (mainGame != null) {
            if (!mainGame.goldenChickenUnlocked) {
                market.selectProduct("GoldenChicken", 5000, 0);
                itemNameLabel.setText("Poulet Doré (VERROUILLÉ)");
            } else {
                market.selectProduct("GoldenChicken", 1000, 500);
                itemNameLabel.setText("Poulet Doré");
            }
            updateUI();
        }
    }

    @FXML
    private void selectEgg() {
        market.selectProduct("Egg", 30, 25);
        itemNameLabel.setText("Oeuf");
        updateUI();
    }

    @FXML
    private void selectGoldenEgg() {
        market.selectProduct("GoldenEgg", 1500, 300);
        itemNameLabel.setText("Oeuf Doré");
        updateUI();
    }

    @FXML private void plusBuyQuantity() { market.incrementBuyQuantity(); updateUI(); }
    @FXML private void minusBuyQuantity() { market.decrementBuyQuantity(); updateUI(); }

    @FXML private void plusSellQuantity() { market.incrementSellQuantity(); updateUI(); }
    @FXML private void minusSellQuantity() { market.decrementSellQuantity(); updateUI(); }

    private void handleUnlock(int price, String type) {
        if (mainGame.money >= price) {
            mainGame.money -= price;

            switch (type) {
                case "cowUnlocked":
                    mainGame.cowUnlocked = true;
                    selectCow();
                    break;
                case "waterMelonUnlocked":
                    mainGame.waterMelonUnlocked = true;
                    selectWaterMelonSeed();
                    break;
                case "chickenUnlocked":
                    mainGame.chickenUnlocked = true;
                    selectChicken();
                    break;
                case "goldenChickenUnlocked":
                    mainGame.goldenChickenUnlocked = true;
                    selectGoldenChicken();
                    break;
            }

            mainGame.saveGame();
        } else {
            itemNameLabel.setText("Pas assez d'émeraudes (" + price + ") !");
        }
    }

    @FXML private void confirmPurchase() {
        if (mainGame != null) {
            String currentProduct = market.getSelectedProductName();

            if ("Cow".equals(currentProduct) && !mainGame.cowUnlocked) {
                handleUnlock(1000, "cowUnlocked");
            } else if ("WaterMelonSeed".equals(currentProduct) && !mainGame.waterMelonUnlocked) {
                handleUnlock(250, "waterMelonUnlocked");
            } else if ("Chicken".equals(currentProduct) && !mainGame.chickenUnlocked) {
                handleUnlock(500, "chickenUnlocked");
            } else if ("GoldenChicken".equals(currentProduct) && !mainGame.goldenChickenUnlocked) {
                handleUnlock(5000, "goldenChickenUnlocked");
            } else {
                market.confirmPurchase(mainGame);
                mainGame.saveGame();
            }

            updateUI();
            mainGame.update();
        }
    }

    @FXML
    private void confirmSale() {
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


        if (selectWaterMelonBtn != null && mainGame != null) {
            selectWaterMelonBtn.setOpacity(mainGame.waterMelonUnlocked ? 1.0 : 0.5);
        }

        if (selectCowBtn != null && mainGame != null) {
            selectCowBtn.setOpacity(mainGame.cowUnlocked ? 1.0 : 0.5);
        }

        if (selectChickenBtn != null) {
            selectChickenBtn.setOpacity(mainGame.chickenUnlocked ? 1.0 : 0.5);
        }

        if (selectGoldenChickenBtn != null) {
            selectGoldenChickenBtn.setOpacity(mainGame.goldenChickenUnlocked ? 1.0 : 0.5);
        }

        if (confirmPurchaseBtn != null && mainGame != null) {
            if (market.getTotalBuyPrice() > mainGame.money) {
                confirmPurchaseBtn.setDisable(true);
                confirmPurchaseBtn.setOpacity(0.3);
            } else {
                confirmPurchaseBtn.setDisable(false);
                confirmPurchaseBtn.setOpacity(1.0);
            }
        }
    }
}