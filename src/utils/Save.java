package utils;

import controllers.MainController;
import models.Animal;
import models.Chicken;
import models.Cow;
import models.GoldenChicken;

import java.io.*;
import java.util.Scanner;

public class Save {
    private MainController mainGame;

    public Save(MainController game) {
        this.mainGame = game;
    }

    public void saveGame() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("save.txt"))) {
            writer.println(mainGame.inventory.money);
            writer.println(mainGame.inventory.wheatStock);
            writer.println(mainGame.inventory.wheatSeeds);
            writer.println(mainGame.inventory.waterMelonStock);
            writer.println(mainGame.inventory.waterMelonSeeds);
            writer.println(mainGame.inventory.milkStock);
            writer.println(mainGame.inventory.eggStock);
            writer.println(mainGame.inventory.goldenEggStock);

            writer.println(mainGame.inventory.cowInventory);
            writer.println(mainGame.inventory.chickenInventory);
            writer.println(mainGame.inventory.goldenChickenInventory);

            writer.println(mainGame.cowUnlocked);
            writer.println(mainGame.waterMelonUnlocked);
            writer.println(mainGame.chickenUnlocked);       
            writer.println(mainGame.goldenChickenUnlocked); 

            for (int i = 0; i < mainGame.animals.length; i++) {
                if (mainGame.animals[i] == null) {
                    writer.println("null");
                } else {
                    writer.println(mainGame.animals[i].getClass().getSimpleName() + ","
                            + mainGame.animals[i].isFed + ","
                            + mainGame.animals[i].productionProgress);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        File file = new File("save.txt");
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextInt()) mainGame.inventory.money = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.inventory.wheatStock = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.inventory.wheatSeeds = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.inventory.waterMelonStock = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.inventory.waterMelonSeeds = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.inventory.milkStock = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.inventory.eggStock = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.inventory.goldenEggStock = scanner.nextInt();

            if (scanner.hasNextInt()) mainGame.inventory.cowInventory = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.inventory.chickenInventory = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.inventory.goldenChickenInventory = scanner.nextInt();

            if (scanner.hasNext()) mainGame.cowUnlocked = Boolean.parseBoolean(scanner.next());
            if (scanner.hasNext()) mainGame.waterMelonUnlocked = Boolean.parseBoolean(scanner.next());
            if (scanner.hasNext()) mainGame.chickenUnlocked = Boolean.parseBoolean(scanner.next());
            if (scanner.hasNext()) mainGame.goldenChickenUnlocked = Boolean.parseBoolean(scanner.next());

            if (scanner.hasNextLine()) scanner.nextLine();

            for (int i = 0; i < mainGame.animals.length; i++) {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line != null && !line.equals("null") && !line.isEmpty()) {
                        String[] parts = line.split(",");
                        String type = parts[0];

                        Animal a = null;
                        if (type.equalsIgnoreCase("Cow")) a = new Cow();
                        else if (type.equalsIgnoreCase("Chicken")) a = new Chicken();
                        else if (type.equalsIgnoreCase("GoldenChicken")) a = new GoldenChicken();

                        if (a != null) {
                            if (parts.length > 1) a.isFed = Boolean.parseBoolean(parts[1]);
                            if (parts.length > 2) a.productionProgress = Double.parseDouble(parts[2]);
                            if (a.productionProgress >= 1.0) a.isReady = true;

                            mainGame.animals[i] = a;
                        }
                    } else {
                        mainGame.animals[i] = null;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de la sauvegarde.");
            e.printStackTrace();
        }
    }
}