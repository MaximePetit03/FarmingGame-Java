import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainController {

    // --- ÉLÉMENTS DU MAIN.FXML (Inventaire fixe) ---
    @FXML private Label moneyLabel;
    @FXML private Button selectWheatBtn;
    @FXML private Button selectWaterMelonBtn;
    @FXML private Button wheatSeedsBtn;
    @FXML private Button waterMelonSeedsBtn;

    // --- RÉCUPÉRATION DES INCLUSIONS (fx:id dans Main.fxml) ---
    @FXML private VBox cultivableField; // L'ID de l'include des champs
    @FXML private VBox animalArea;     // L'ID de l'include des animaux
    @FXML private VBox marketArea;     // L'ID de l'include du marché

    // LE LIEN MAGIQUE : JavaFX injecte le contrôleur de l'include ici
    // Il faut que le nom soit : ID_DE_L_INCLUDE + "Controller"
    @FXML private MarketController marketAreaController;

    // --- COMPOSANTS IMBRIQUÉS (Gérés par Lookup) ---
    private Button[] fieldButtons = new Button[5];
    private ProgressBar[] fieldProgressBars = new ProgressBar[5];
    private Button[] animalButtons = new Button[5];
    private ProgressBar[] animalProgressBars = new ProgressBar[5];

    // --- DONNÉES DU JEU ---
    public int money = 25;
    public int wheatStock = 0;
    public int wheatSeeds = 10;
    public int waterMelonStock = 0;
    public int waterMelonSeeds = 2;
    public int milkStock = 0;

    public String selectedSeed = "wheat"; // Graine sélectionnée pour planter

    // Listes de données métier
    public List<CultivableField> fields = new ArrayList<>();
    public List<Animal> animals = new ArrayList<>();

    @FXML
    public void initialize() {
        // 1. Initialisation des champs
        fields.clear();
        for (int i = 0; i < 5; i++) {
            fields.add(new CultivableField());
        }

        // 2. RÉCUPÉRATION DES BOUTONS DES CHAMPS (Lookup)
        for (int i = 0; i < 5; i++) {
            if (cultivableField != null) {
                fieldButtons[i] = (Button) cultivableField.lookup("#btn" + i);
                fieldProgressBars[i] = (ProgressBar) cultivableField.lookup("#bar" + i);
            }
        }

        // 3. CONFIGURATION DES ÉVÉNEMENTS DES CHAMPS
        for (int i = 0; i < 5; i++) {
            final int index = i;
            if (fieldButtons[index] != null) {
                fieldButtons[index].setOnAction(e -> {
                    fields.get(index).harvestSystem(this);
                    update();
                });
            }
        }

        // 4. LIAISON AVEC LE MARCHÉ (Lien magique vers MarketController)
        if (marketAreaController != null) {
            marketAreaController.setMainController(this);
        }

        // 5. CHARGEMENT ET STYLE
        loadGame();
        applyStyle();

        // 6. BOUCLE DE JEU (Pousse des plantes chaque seconde)
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            updateGameLogic();
            update();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        update();
    }

    // --- SÉLECTION DE LA GRAINE ---
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

    // --- LOGIQUE MÉTIER ---
    private void updateGameLogic() {
        for (CultivableField f : fields) {
            f.updateProgress();
        }
    }

    // --- MISE À JOUR VISUELLE ---
    public void update() {
        // Inventaire (Labels fixes à gauche)
        if (moneyLabel != null) moneyLabel.setText("Richesse: " + money + " émeraudes");
        if (wheatSeedsBtn != null) wheatSeedsBtn.setText("Graines Blé: " + wheatSeeds);
        if (selectWheatBtn != null) selectWheatBtn.setText("Blé: " + wheatStock);
        if (waterMelonSeedsBtn != null) waterMelonSeedsBtn.setText("Graines Pastèque: " + waterMelonSeeds);
        if (selectWaterMelonBtn != null) selectWaterMelonBtn.setText("Pastèque: " + waterMelonStock);

        // État des parcelles
        for (int i = 0; i < 5; i++) {
            if (fieldButtons[i] != null && fieldProgressBars[i] != null) {
                fieldButtons[i].setText(fields.get(i).getStateText());
                fieldProgressBars[i].setProgress(fields.get(i).getProgress());
            }
        }
    }

    // --- SYSTÈME DE SAUVEGARDE TXT ---
    public void saveGame() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("save.txt"))) {
            writer.println(money);
            writer.println(wheatStock);
            writer.println(wheatSeeds);
            writer.println(waterMelonStock);
            writer.println(waterMelonSeeds);
            System.out.println("Sauvegarde réussie.");
        } catch (IOException e) {
            System.err.println("Erreur de sauvegarde : " + e.getMessage());
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
            System.out.println("Données chargées.");
        } catch (FileNotFoundException e) {
            System.err.println("Aucun fichier trouvé.");
        }
    }
}