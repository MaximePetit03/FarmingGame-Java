import java.io.*;
import java.util.Scanner;

public class Save {
    private MainController mainGame;

    public Save(MainController game) {
        this.mainGame = game;
    }

    public void saveGame() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("save.txt"))) {
            // On accède aux variables via mainGame
            writer.println(mainGame.money);
            writer.println(mainGame.wheatStock);
            writer.println(mainGame.wheatSeeds);
            writer.println(mainGame.waterMelonStock);
            writer.println(mainGame.waterMelonSeeds);
            writer.println(mainGame.cowInventory);
            writer.println(mainGame.milkStock);
            writer.println(mainGame.isCowUnlocked);
            writer.println(mainGame.isWaterMelonUnlocked);

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
            if (scanner.hasNextInt()) mainGame.money = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.wheatStock = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.wheatSeeds = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.waterMelonStock = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.waterMelonSeeds = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.cowInventory = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.milkStock = scanner.nextInt();

            // Lecture des booléens
            if (scanner.hasNext()) mainGame.isCowUnlocked = Boolean.parseBoolean(scanner.next());
            if (scanner.hasNext()) mainGame.isWaterMelonUnlocked = Boolean.parseBoolean(scanner.next());

            if (scanner.hasNextLine()) scanner.nextLine(); // Nettoyer la ligne après les booléens

            for (int i = 0; i < mainGame.animals.length; i++) {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (!line.equals("null") && !line.isEmpty()) {
                        String[] parts = line.split(",");
                        if (parts[0].equals("Cow")) {
                            Cow c = new Cow();
                            c.isFed = Boolean.parseBoolean(parts[1]);
                            c.productionProgress = Double.parseDouble(parts[2]);
                            mainGame.animals[i] = c;
                        }
                    } else {
                        mainGame.animals[i] = null;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}