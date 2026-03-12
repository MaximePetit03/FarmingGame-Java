package controllers;

import javafx.scene.control.Label;
import models.Weather;

public class WeatherController {

    public Label weatherLabel;

    public WeatherController() {
        this.weatherLabel = new Label("Chargement...");

        this.weatherLabel.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.7); " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 12; " +
                        "-fx-background-radius: 0 0 15 0; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 15px; " +
                        "-fx-border-color: rgba(255, 255, 255, 0.2); " +
                        "-fx-border-width: 0 1 1 0; " +
                        "-fx-border-radius: 0 0 15 0;"
        );

        this.weatherLabel.setPrefWidth(200);
    }

    public void updateView(Weather weather) {
        String name = weather.getCurrentName();
        String icon = weather.getCurrentIcon();
        String color = weather.getCurrentColor();

        weatherLabel.setText(icon + "  MÉTÉO : " + name.toUpperCase());

        String currentStyle = weatherLabel.getStyle();
        weatherLabel.setStyle(currentStyle + "-fx-text-fill: " + color + ";");
    }

    public Label getView() {
        return weatherLabel;
    }
}