import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML private Label moneyLabel;
    @FXML private Label inventoryLabel;

    // Listes pour les 5 barres de progression des graines
    @FXML private ProgressBar bar0, bar1, bar2, bar3, bar4;

    private ProgressBar[] progressBars;
    private List<CultivableField> fields = new ArrayList<>();
    private int money = 500;
    private int cornInStock = 10; // L'inventaire

    @FXML
    public void initialize() {
        progressBars = new ProgressBar[]{bar0, bar1, bar2, bar3, bar4};

        // Créer les 5 objets de terrain
        for (int i = 0; i < 5; i++) {
            fields.add(new CultivableField());
        }

        // Boucle de jeu
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            for (int i = 0; i < 5; i++) {
                fields.get(i).incrementGrowth();
                progressBars[i].setProgress(fields.get(i).getGrowthProgress());
            }
            updateUI();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML private void handleField0() { processClick(0); }
    @FXML private void handleField1() { processClick(1); }
    @FXML private void handleField2() { processClick(2); }
    @FXML private void handleField3() { processClick(3); }
    @FXML private void handleField4() { processClick(4); }

    private void processClick(int index) {
        CultivableField field = fields.get(index);

        if (!field.isOccupied()) {
            cornInStock -= 1;
            field.plant("Corn");
        } else if (field.isReady()) {
            String crop = field.harvest();
            if (crop != null) {
                cornInStock += 10; // On ajoute à l'inventaire
            }
        }
        updateUI();
    }

    private void updateUI() {
        moneyLabel.setText("Balance: " + money + " euros dans la France de Macron");
        inventoryLabel.setText("Mais en stock: " + cornInStock);
    }
}