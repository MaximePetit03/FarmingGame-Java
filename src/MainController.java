import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
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
    @FXML private Label inventoryLabel;

    @FXML private ProgressBar bar0, bar1, bar2, bar3, bar4;

    private ProgressBar[] progressBars;
    private List<CultivableField> fields = new ArrayList<>();

    // Valeurs par défaut si aucun fichier n'existe
    private int money = 500;
    private int cornInStock = 10;

    @FXML
    public void initialize() {
        loadGame();

        progressBars = new ProgressBar[]{bar0, bar1, bar2, bar3, bar4};

        for (int i = 0; i < 5; i++) {
            fields.add(new CultivableField());
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            for (int i = 0; i < 5; i++) {
                fields.get(i).incrementGrowth();
                progressBars[i].setProgress(fields.get(i).getGrowthProgress());
            }
            update();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML private void handleField0() { harvestSystem(0); }
    @FXML private void handleField1() { harvestSystem(1); }
    @FXML private void handleField2() { harvestSystem(2); }
    @FXML private void handleField3() { harvestSystem(3); }
    @FXML private void handleField4() { harvestSystem(4); }

    private void harvestSystem(int index) {
        CultivableField field = fields.get(index);

        if (!field.isOccupied()) {
            if (cornInStock > 0) {
                cornInStock -= 1;
                field.plant("Corn");
            } else {
                System.out.println("Plus de graines !");
            }
        } else if (field.isReady()) {
            String crop = field.harvest();
            if (crop != null) {
                cornInStock += 2;
            }
        }
        update();
    }

    private void update() {
        moneyLabel.setText("Richesse: " + money + " euros \ndans la France à Macron");
        inventoryLabel.setText("Mais en stock: " + cornInStock);
    }

    // Sauvegarde
    public void saveGame() {
        try (PrintWriter writer = new PrintWriter("save.txt")) {
            writer.println(money);
            writer.println(cornInStock);
            System.out.println("Partie sauvegardée");
        } catch (IOException e) {
            System.err.println("Erreur de sauvegarde : " + e.getMessage());
        }
    }

    // CHargement du jeu
    private void loadGame() {
        File file = new File("save.txt");
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextInt()) money = scanner.nextInt();
            if (scanner.hasNextInt()) cornInStock = scanner.nextInt();
            System.out.println("Données chargées");
        } catch (Exception e) {
            System.err.println("Erreur de chargement : " + e.getMessage());
        }
    }
}