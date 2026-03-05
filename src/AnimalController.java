import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

public class AnimalController {

    // On définit les éléments pour les 5 enclos
    @FXML private Button animalBtn0, animalBtn1, animalBtn2, animalBtn3, animalBtn4;
    @FXML private ProgressBar animalBar0, animalBar1, animalBar2, animalBar3, animalBar4;

    private MainController mainGame;

    // Permet au MainController de se lier à ce contrôleur
    public void setMainController(MainController game) {
        this.mainGame = game;
    }

    public void updateDisplay() {
        if (mainGame == null) return;
        // On pourrait ici mettre à jour les textes des boutons et barres
        // Mais pour l'instant, ton MainController le fait déjà via Lookup.
    }
}