import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainController {

    @FXML private Label moneyLabel;

    @FXML private Button selectCornBtn;
    @FXML private Button cornSeedsBtn;
    @FXML private Button selectWaterMelonBtn;
    @FXML private Button waterMelonSeedsBtn;

    // Éléments des parcelles
    @FXML private ProgressBar bar0, bar1, bar2, bar3, bar4;
    @FXML private Button btn0, btn1, btn2, btn3, btn4;

    private ProgressBar[] progressBars;
    private Button[] buttons;
    private List<CultivableField> fields = new ArrayList<>();

    public int money = 25;
    public int cornSeeds = 10;
    public int cornStock = 0;
    public int waterMelonSeeds = 2;
    public int waterMelonStock = 0;

    private String selectedSeed = "Corn";

    @FXML
    public void initialize() {
        loadGame();

        progressBars = new ProgressBar[]{bar0, bar1, bar2, bar3, bar4};
        buttons = new Button[]{btn0, btn1, btn2, btn3, btn4};

        for (int i = 0; i < 5; i++) {
            fields.add(new CultivableField());
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            for (int i = 0; i < 5; i++) {
                fields.get(i).incrementGrowth();
                progressBars[i].setProgress(fields.get(i).getGrowthProgress());
                updateFieldButtons(i);
            }
            update();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        applyStyle();
    }

    // Affiche l'état de la plante
    private void updateFieldButtons(int i) {
        CultivableField field = fields.get(i);
        if (!field.isOccupied()) {
            buttons[i].setText("Vide");
        } else if (field.isReady()) {
            buttons[i].setText(field.getPlantName() + " a fini de pousser");
        } else {
            int percent = (int) (field.getGrowthProgress() * 100);
            buttons[i].setText(field.getPlantName() + " pousse");
        }
    }

    @FXML public void selectCorn() {
        this.selectedSeed = "Corn";
        applyStyle();
    }

    @FXML private void selectWaterMelon() {
        this.selectedSeed = "waterMelon";
        applyStyle();
    }

    @FXML private void handleField0() { fields.get(0).harvestSystem(this); }
    @FXML private void handleField1() { fields.get(1).harvestSystem(this); }
    @FXML private void handleField2() { fields.get(2).harvestSystem(this); }
    @FXML private void handleField3() { fields.get(3).harvestSystem(this); }
    @FXML private void handleField4() { fields.get(4).harvestSystem(this); }

    private void applyStyle() {
        String cornStyle = selectedSeed.equals("Corn")
                ? "-fx-background-color: #3498db; -fx-text-fill: white; -fx-alignment: CENTER_LEFT;"
                : "-fx-background-color: white; -fx-text-fill: black; -fx-border-color: #bdc3c7; -fx-alignment: CENTER_LEFT;";

        String melonStyle = selectedSeed.equals("waterMelon")
                ? "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-alignment: CENTER_LEFT;"
                : "-fx-background-color: white; -fx-text-fill: black; -fx-border-color: #bdc3c7; -fx-alignment: CENTER_LEFT;";

        selectCornBtn.setStyle(cornStyle);
        cornSeedsBtn.setStyle(cornStyle);
        selectWaterMelonBtn.setStyle(melonStyle);
        waterMelonSeedsBtn.setStyle(melonStyle);
    }

    public void update() {
        moneyLabel.setText("Richesse: " + money + " émeraudes");
        selectCornBtn.setText("Maïs en stock: " + cornStock);
        cornSeedsBtn.setText("Graines de maïs: " + cornSeeds);
        selectWaterMelonBtn.setText("Pastèque en stock: " + waterMelonStock);
        waterMelonSeedsBtn.setText("Graines de pastèque: " + waterMelonSeeds);
    }

    public String getSelectedSeed() { return selectedSeed; }

    public void saveGame() {
        try (PrintWriter writer = new PrintWriter("save.txt")) {
            writer.println(money);
            writer.println(cornSeeds);
            writer.println(cornStock);
            writer.println(waterMelonSeeds);
            writer.println(waterMelonStock);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void loadGame() {
        File file = new File("save.txt");
        if (!file.exists()) return;
        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextInt()) money = scanner.nextInt();
            if (scanner.hasNextInt()) cornSeeds = scanner.nextInt();
            if (scanner.hasNextInt()) cornStock = scanner.nextInt();
            if (scanner.hasNextInt()) waterMelonSeeds = scanner.nextInt();
            if (scanner.hasNextInt()) waterMelonStock = scanner.nextInt();
        } catch (Exception e) { e.printStackTrace(); }
    }
}