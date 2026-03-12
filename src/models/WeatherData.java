package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeatherData {
    public String name;
    public String icon;
    public double multiplier;
    public String color;

    public WeatherData(String name, String icon, double multiplier, String color) {
        this.name = name;
        this.icon = icon;
        this.multiplier = multiplier;
        this.color = color;
    }
}