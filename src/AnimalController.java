import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

public class AnimalController {

    @FXML private Button animalBtn0, animalBtn1, animalBtn2, animalBtn3, animalBtn4;
    @FXML private ProgressBar animalBar0, animalBar1, animalBar2, animalBar3, animalBar4;

    private MainController mainGame;

    public void setMainController(MainController game) {
        this.mainGame = game;
    }

    public void updateDisplay() {

    }
}