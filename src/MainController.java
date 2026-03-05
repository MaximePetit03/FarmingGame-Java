import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainController {

    @FXML private Label moneyLabel;
    @FXML private Button selectWheatBtn;
    @FXML private Button selectWaterMelonBtn;
    @FXML private Button wheatSeedsBtn;
    @FXML private Button waterMelonSeedsBtn;
    @FXML private TabPane mainTabPane;

    @FXML private VBox cultivableField;
    @FXML private VBox marketArea;

    @FXML private MarketController marketAreaController;

    private Button[] fieldButtons = new Button[5];
    private ProgressBar[] fieldProgressBars = new ProgressBar[5];

    public int money = 25;
    public int wheatStock = 0;
    public int wheatSeeds = 10;
    public int waterMelonStock = 0;
    public int waterMelonSeeds = 2;

    public String selectedSeed = "wheat";

    public List<CultivableField> fields = new ArrayList<>();
    private Music musicManager = new Music();

    @FXML
    public void initialize() {
        fields.clear();
        for (int i = 0; i < 5; i++) {
            fields.add(new CultivableField());
        }

        for (int i = 0; i < 5; i++) {
            if (cultivableField != null) {
                fieldButtons[i] = (Button) cultivableField.lookup("#btn" + i);
                fieldProgressBars[i] = (ProgressBar) cultivableField.lookup("#bar" + i);
            }
        }

        for (int i = 0; i < 5; i++) {
            final int index = i;
            if (fieldButtons[index] != null) {
                fieldButtons[index].setOnAction(e -> {
                    fields.get(index).harvestSystem(this);
                    update();
                });
            }
        }

        if (marketAreaController != null) {
            marketAreaController.setMainController(this);
        }

        if (mainTabPane != null) {
            mainTabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
                if (newTab != null) {
                    if (newTab.getText().trim().equalsIgnoreCase("Marché")) {
                        musicManager.play("WiiParty.mp3", 0.1);
                    }
                    else {
                        musicManager.stop();
                    }
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
        selectedSeed = "watermelon";
        applyStyle();
    }

    private void applyStyle() {
        if (selectWheatBtn == null || selectWaterMelonBtn == null) return;
        selectWheatBtn.setStyle(selectedSeed.equals("wheat") ? "-fx-border-color: #2ecc71; -fx-border-width: 3;" : "");
        selectWaterMelonBtn.setStyle(selectedSeed.equals("watermelon") ? "-fx-border-color: #2ecc71; -fx-border-width: 3;" : "");
    }

    private void updateGameLogic() {
        for (CultivableField f : fields) {
            f.updateProgress();
        }
    }

    public void update() {
        if (moneyLabel != null) moneyLabel.setText("Richesse: " + money + " émeraudes");
        if (wheatSeedsBtn != null) wheatSeedsBtn.setText("Graines Blé: " + wheatSeeds);
        if (selectWheatBtn != null) selectWheatBtn.setText("Blé: " + wheatStock);
        if (waterMelonSeedsBtn != null) waterMelonSeedsBtn.setText("Graines Pastèque: " + waterMelonSeeds);
        if (selectWaterMelonBtn != null) selectWaterMelonBtn.setText("Pastèque: " + waterMelonStock);

        for (int i = 0; i < 5; i++) {
            if (fieldButtons[i] != null && fieldProgressBars[i] != null) {
                fieldButtons[i].setText(fields.get(i).getStateText());
                fieldProgressBars[i].setProgress(fields.get(i).getProgress());
            }
        }
    }

    public void saveGame() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("save.txt"))) {
            writer.println(money);
            writer.println(wheatStock);
            writer.println(wheatSeeds);
            writer.println(waterMelonStock);
            writer.println(waterMelonSeeds);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        File file = new File("save.txt");
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextInt()) money = scanner.nextInt();
            if (scanner.hasNextInt()) wheatStock = scanner.nextInt();
            if (scanner.hasNextInt()) wheatSeeds = scanner.nextInt();
            if (scanner.hasNextInt()) waterMelonStock = scanner.nextInt();
            if (scanner.hasNextInt()) waterMelonSeeds = scanner.nextInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}