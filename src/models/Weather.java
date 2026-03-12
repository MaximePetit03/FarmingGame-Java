package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Weather {
    public List<WeatherData> weatherList = new ArrayList<>();
    public WeatherData current;

    public Weather() {
        weatherList.add(new WeatherData("Soleil", "☀️", 1.0, "#FFD700"));
        weatherList.add(new WeatherData("Pluie", "🌧️", 1.5, "#81D4FA"));
        weatherList.add(new WeatherData("Orage", "⛈️", 2.0, "#E1BEE7"));
        weatherList.add(new WeatherData("Brouillard", "🌫️", 0.8, "#CFD8DC"));

        generateRandomWeather();
    }

    public void generateRandomWeather() {
        Random rand = new Random();
        this.current = weatherList.get(rand.nextInt(weatherList.size()));
    }

    public String getCurrentName() { return current.name; }
    public String getCurrentIcon() { return current.icon; }
    public String getCurrentColor() { return current.color; }
    public double getGrowthMultiplier() { return current.multiplier; }
}
