import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Main.fxml"));
        Parent root = loader.load();

        MainController controller = loader.getController();

        primaryStage.setTitle("Farm My Farm");
        primaryStage.setScene(new Scene(root, 1080, 720));

        // Save quand on ferme la fenêtre
        primaryStage.setOnCloseRequest(event -> {
            controller.saveGame();
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}