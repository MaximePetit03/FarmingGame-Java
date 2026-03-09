import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AnimalController {

    private MainController mainGame;

    @FXML private Button animalBtn0;
    @FXML private Button animalBtn1;
    @FXML private Button animalBtn2;
    @FXML private Button animalBtn3;
    @FXML private Button animalBtn4;
    @FXML private Button animalBtn5;

    private Button[] animalButtons;

    public void setMainController(MainController game) {
        this.mainGame = game;

        this.animalButtons = new Button[]{animalBtn0, animalBtn1, animalBtn2, animalBtn3, animalBtn4, animalBtn5};

        setupButtons();
        updateUI();
    }

    private void setupButtons() {
        for (int i = 0; i < animalButtons.length; i++) {
            final int index = i;
            if (animalButtons[index] != null) {

                animalButtons[index].setOnAction(e -> onAnimalClick(index));

                animalButtons[index].setOnMouseEntered(e -> {
                    Animal a = mainGame.animals[index];
                    if (a == null) {
                        animalButtons[index].setStyle("-fx-background-color: #7f8c8d; -fx-text-fill: white; -fx-background-radius: 10; -fx-cursor: hand;");
                    } else {
                        animalButtons[index].setOpacity(0.8);
                    }
                });

                animalButtons[index].setOnMouseExited(e -> {
                    animalButtons[index].setOpacity(1.0);
                    animalButtons[index].setTranslateY(0);
                    updateUI();
                });
            }
        }
    }

    private void onAnimalClick(int index) {
        Animal a = mainGame.animals[index];

        if (a == null) {
            if (mainGame.cowInventory > 0 || mainGame.chickenInventory > 0 || mainGame.goldenChickenInventory > 0) {
                mainGame.placeAnimal(index);
            }
        } else {
            if (!a.isFed) {
                a.feed(mainGame);
            } else if (a.isReady) {
                a.collectProduct(mainGame);
            }
        }
        updateUI();
        mainGame.update();
    }

    public void updateUI() {
        if (animalButtons == null) return;

        for (int i = 0; i < animalButtons.length; i++) {
            if (animalButtons[i] != null) {
                Animal a = mainGame.animals[i];
                if (a == null) {
                    animalButtons[i].setText("ENCLOS VIDE");
                    animalButtons[i].setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-background-radius: 10;");
                } else {
                    animalButtons[i].setText(a.getText());
                    animalButtons[i].setStyle(a.getStyle());
                }
            }
        }
    }
}