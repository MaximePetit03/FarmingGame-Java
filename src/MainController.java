import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    // --- ÉLÉMENTS FXML ---
    @FXML private Label moneyLabel;
    @FXML private Button selectWheatBtn;
    @FXML private Button selectWaterMelonBtn;
    @FXML private Button wheatSeedsBtn;
    @FXML private Button waterMelonSeedsBtn;
    @FXML private Button milkStockBtn;
    @FXML private TabPane mainTabPane;
    @FXML private VBox cultivableField;

    // --- RÉFÉRENCES AUTRES CONTRÔLEURS ---
    @FXML private MarketController marketAreaController;
    @FXML private AnimalController animalAreaController;

    // --- LOGIQUE DU JEU ---
    public int money = 0;
    public int wheatStock = 0;
    public int wheatSeeds = 6;
    public int waterMelonStock = 0;
    public int waterMelonSeeds = 0;
    public int cowInventory = 0;
    public int milkStock = 0;

    // --- ÉTATS DE PROGRESSION ---
    public boolean isCowUnlocked = false;
    public boolean isWaterMelonUnlocked = false;
    public String selectedSeed = "wheat";

    // --- SYSTÈMES ---
    private Button[] fieldButtons = new Button[6];
    public List<CultivableField> fields = new ArrayList<>();
    public Animal[] animals = new Animal[6];
    private Save saveManager;
    private Music musicManager = new Music();

    @FXML
    public void initialize() {
        this.saveManager = new Save(this);

        if (animalAreaController != null) {
            animalAreaController.setMainController(this);
        }

        fields.clear();
        for (int i = 0; i < 6; i++) {
            fields.add(new CultivableField());
        }

        for (int i = 0; i < 6; i++) {
            if (cultivableField != null) {
                fieldButtons[i] = (Button) cultivableField.lookup("#btn" + i);
                final int index = i;
                if (fieldButtons[index] != null) {
                    fieldButtons[index].setOnAction(e -> {
                        fields.get(index).harvestSystem(this);
                        update();
                    });
                }
            }
        }

        if (marketAreaController != null) {
            marketAreaController.setMainController(this);
        }

        if (mainTabPane != null) {
            mainTabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
                if (newTab != null && newTab.getText().trim().equalsIgnoreCase("Marché")) {
                    musicManager.play("WiiParty.mp3", 0.1);
                } else {
                    musicManager.stop();
                }
            });
        }

        loadGame();
        applyStyle();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            updateGameLogic();
            update();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        update();
    }

    @FXML
    public void selectWheat() {
        selectedSeed = "wheat";
        applyStyle();
    }

    @FXML
    public void selectWaterMelon() {
        if (isWaterMelonUnlocked) {
            selectedSeed = "watermelon";
            applyStyle();
        }
    }

    private void applyStyle() {
        if (selectWheatBtn == null || selectWaterMelonBtn == null) return;
        selectWheatBtn.setStyle(selectedSeed.equals("wheat") ? "-fx-border-color: #2ecc71; -fx-border-width: 3;" : "");

        if (!isWaterMelonUnlocked) {
            selectWaterMelonBtn.setOpacity(0.5);
            selectWaterMelonBtn.setText("LOCKED");
            selectWaterMelonBtn.setStyle("");
        } else {
            selectWaterMelonBtn.setOpacity(1.0);
            selectWaterMelonBtn.setStyle(selectedSeed.equals("watermelon") ? "-fx-border-color: #2ecc71; -fx-border-width: 3;" : "");
        }
    }

    public void placeAnimal(int index) {
        if (cowInventory > 0 && animals[index] == null) {
            animals[index] = new Cow();
            cowInventory -= 1;
            update();
            saveGame();
        }
    }

    private void updateGameLogic() {
        for (CultivableField f : fields) {
            f.updateProgress();
        }
        for (Animal a : animals) {
            if (a != null) a.incrementProduction();
        }
    }

    public void update() {
        if (moneyLabel != null) moneyLabel.setText("Richesse: " + money + " émeraudes");
        if (wheatSeedsBtn != null) wheatSeedsBtn.setText("Graines Blé: " + wheatSeeds);
        if (selectWheatBtn != null) selectWheatBtn.setText("Blé: " + wheatStock);
        if (waterMelonSeedsBtn != null) waterMelonSeedsBtn.setText("Graines Pastèque: " + waterMelonSeeds);
        if (selectWaterMelonBtn != null && isWaterMelonUnlocked) selectWaterMelonBtn.setText("Pastèque: " + waterMelonStock);
        if (milkStockBtn != null) milkStockBtn.setText("Lait: " + milkStock);

        if (animalAreaController != null) animalAreaController.updateUI();

        for (int i = 0; i < 6; i++) {
            if (fieldButtons[i] != null) {
                CultivableField f = fields.get(i);

                fieldButtons[i].setMinSize(125, 125);
                fieldButtons[i].setMaxSize(125, 125);

                fieldButtons[i].setStyle(f.getStyle());
                fieldButtons[i].setText(f.getText());

                fieldButtons[i].setGraphic(null);
            }
        }
    }

    public void saveGame() {
        if (saveManager != null) saveManager.saveGame();
    }

    public void loadGame() {
        if (saveManager != null) saveManager.loadGame();
    }
}