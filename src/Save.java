import java.io.*;
import java.util.Scanner;

public class Save {
    private MainController mainGame;

    public Save(MainController game) {
        this.mainGame = game;
    }

    public void saveGame() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("save.txt"))) {
            writer.println(mainGame.money);
            writer.println(mainGame.wheatStock);
            writer.println(mainGame.wheatSeeds);
            writer.println(mainGame.waterMelonStock);
            writer.println(mainGame.waterMelonSeeds);
            writer.println(mainGame.milkStock);
            writer.println(mainGame.eggStock);       
            writer.println(mainGame.goldenEggStock); 

            writer.println(mainGame.cowInventory);
            writer.println(mainGame.chickenInventory);      
            writer.println(mainGame.goldenChickenInventory);

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
            if (scanner.hasNextInt()) mainGame.money = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.wheatStock = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.wheatSeeds = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.waterMelonStock = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.waterMelonSeeds = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.milkStock = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.eggStock = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.goldenEggStock = scanner.nextInt();

            if (scanner.hasNextInt()) mainGame.cowInventory = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.chickenInventory = scanner.nextInt();
            if (scanner.hasNextInt()) mainGame.goldenChickenInventory = scanner.nextInt();

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
                        if (type.equals("Cow")) a = new Cow();
                        else if (type.equals("Chicken")) a = new Chicken();
                        else if (type.equals("GoldenChicken")) a = new GoldenChicken();

                        if (a != null) {
                            a.isFed = Boolean.parseBoolean(parts[1]);
                            a.productionProgress = Double.parseDouble(parts[2]);
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