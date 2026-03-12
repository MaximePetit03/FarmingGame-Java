import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML private Label moneyLabel;
    @FXML private Button selectWheatBtn, selectWaterMelonBtn, selectEggBtn, selectGoldenEggBtn;
    @FXML private Button wheatSeedsBtn, waterMelonSeedsBtn, milkStockBtn, eggStockBtn, goldenEggBtn;
    @FXML private TabPane mainTabPane;
    @FXML private VBox cultivableField;

    @FXML private MarketController marketAreaController;
    @FXML private AnimalController animalAreaController;
    public WeatherController weatherUI = new WeatherController();

    public int money = 0;
    public String selectedSeed = "wheat";
    private Save saveManager;
    private Music musicManager = new Music();

    public int wheatSeeds = 6, wheatStock = 0;
    public int waterMelonSeeds = 0, waterMelonStock = 0;
    public int cowInventory = 0, milkStock = 0;
    public int chickenInventory = 0, eggStock = 0;
    public int goldenChickenInventory = 0, goldenEggStock = 0;

    public boolean cowUnlocked = false;
    public boolean waterMelonUnlocked = false;
    public boolean chickenUnlocked = false;
    public boolean goldenChickenUnlocked = false;

    private Weather currentWeather = new Weather();
    private int weatherTimer = 0;
    private final int weatherDuration = 20;

    public List<CultivableField> fields = new ArrayList<>();
    private Button[] fieldButtons = new Button[6];
    public Animal[] animals = new Animal[6];

    @FXML
    public void initialize() {
        this.saveManager = new Save(this);

        setupWeatherOverlay();
        setupFields();
        setupControllers();
        setupTabs();
        setupGameLoop();

        loadGame();
        applyStyle();
        update();
    }

    private void setupWeatherOverlay() {
        javafx.application.Platform.runLater(() -> {
            if (mainTabPane != null && mainTabPane.getScene() != null) {
                Parent root = mainTabPane.getScene().getRoot();
                if (!(root instanceof StackPane)) {
                    StackPane stack = new StackPane();
                    mainTabPane.getScene().setRoot(stack);
                    stack.getChildren().add(root);

                    Label uiView = weatherUI.getView();
                    weatherUI.updateView(currentWeather);

                    stack.getChildren().add(uiView);

                    StackPane.setAlignment(uiView, Pos.TOP_RIGHT);

                    StackPane.setMargin(uiView, new Insets(50, 20, 0, 0));
                }
            }
        });
    }

    private void setupFields() {
        fields.clear();
        for (int i = 0; i < 6; i++) {
            CultivableField field = new CultivableField();
            fields.add(field);

            if (cultivableField != null) {
                fieldButtons[i] = (Button) cultivableField.lookup("#btn" + i);
                final int index = i;

                if (fieldButtons[index] != null) {
                    fieldButtons[index].setOnAction(e -> {
                        fields.get(index).harvestSystem(this);
                        update();
                    });

                    field.setupHover(fieldButtons[index], this);
                }
            }
        }
    }

    private void setupControllers() {
        if (animalAreaController != null) {
            animalAreaController.setMainController(this);
        }
        if (marketAreaController != null) {
            marketAreaController.setMainController(this);
        }
    }

    private void setupTabs() {
        if (mainTabPane != null) {
            mainTabPane.setFocusTraversable(false);

            mainTabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
                if (newTab != null) {
                    javafx.application.Platform.runLater(() -> {
                        if (newTab.getText().trim().equalsIgnoreCase("Marché")) {
                            musicManager.play("WiiParty.mp3", 0.1);
                        } else {
                            musicManager.stop();
                        }
                    });
                }
            });
        }
    }

    private void setupGameLoop() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            updateGame();
            update();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void selectWheat() {
        selectedSeed = "wheat";
        applyStyle();
    }

    @FXML
    public void selectWaterMelon() {
        if (waterMelonUnlocked) {
            selectedSeed = "watermelon";
            applyStyle();
        }
    }

    @FXML
    public void selectEgg() {
        selectedSeed = "egg";
        applyStyle();
    }

    @FXML
    public void selectGoldenEgg() {
        selectedSeed = "goldenEgg";
        applyStyle();
    }

    private void applyStyle() {
        if (selectWheatBtn == null || selectWaterMelonBtn == null ||
                selectEggBtn == null || selectGoldenEggBtn == null) return;

        selectWheatBtn.setStyle(selectedSeed.equals("wheat") ? "-fx-border-color: #2ecc71; -fx-border-width: 3; -fx-border-radius: 5;" : "");
        selectEggBtn.setStyle(selectedSeed.equals("egg") ? "-fx-border-color: #2ecc71; -fx-border-width: 3; -fx-border-radius: 5;" : "");
        selectGoldenEggBtn.setStyle(selectedSeed.equals("goldenEgg") ? "-fx-border-color: #2ecc71; -fx-border-width: 3; -fx-border-radius: 5;" : "");

        if (!waterMelonUnlocked) {
            selectWaterMelonBtn.setOpacity(0.5);
            selectWaterMelonBtn.setText("LOCKED");
            selectWaterMelonBtn.setStyle("");
        } else {
            selectWaterMelonBtn.setOpacity(1.0);
            selectWaterMelonBtn.setStyle(selectedSeed.equals("watermelon") ? "-fx-border-color: #2ecc71; -fx-border-width: 3; -fx-border-radius: 5;" : "");
        }
    }

    public void placeAnimal(int index) {
        if (animals[index] == null) {
            if (goldenChickenInventory > 0) {
                animals[index] = new GoldenChicken();
                goldenChickenInventory -= 1;
            } else if (chickenInventory > 0) {
                animals[index] = new Chicken();
                chickenInventory -= 1;
            } else if (cowInventory > 0) {
                animals[index] = new Cow();
                cowInventory -= 1;
            }

            update();
            saveGame();
        }
    }

    private void updateGame() {
        weatherTimer += 1;

        if (weatherTimer >= weatherDuration) {
            currentWeather.generateRandomWeather();
            weatherTimer = 0;

            weatherUI.updateView(currentWeather);
            System.out.println("La météo actuelle : " + currentWeather.getCurrentName());
        }
        for (CultivableField f : fields) {
            f.updateProgress(currentWeather);
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
        if (selectWaterMelonBtn != null && waterMelonUnlocked) selectWaterMelonBtn.setText("Pastèque: " + waterMelonStock);
        if (milkStockBtn != null) milkStockBtn.setText("Lait: " + milkStock);
        if (eggStockBtn != null) eggStockBtn.setText("Oeufs: " + eggStock);
        if (goldenEggBtn != null) goldenEggBtn.setText("Oeufs Dorés: " + goldenEggStock);

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