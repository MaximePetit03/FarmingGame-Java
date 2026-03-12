package models;

import controllers.MainController;

public class Market {
    private String selectedItem;

    private int buyQuantity = 1;
    private int sellQuantity = 1;

    private int unitBuyPrice = 0;
    private int unitSellPrice = 0;

    private int totalBuyPrice = 0;
    private int totalSellPrice = 0;

    public void incrementBuyQuantity() {
        buyQuantity += 1;
        updatePrices();
    }
    public void decrementBuyQuantity() {
        if (buyQuantity > 1) {
            buyQuantity -= 1;
            updatePrices();
        }
    }

    public void incrementSellQuantity() {
        sellQuantity += 1;
        updatePrices();
    }

    public void decrementSellQuantity() {
        if (sellQuantity > 1) {
            sellQuantity -= 1;
            updatePrices();
        }
    }

    public String getSelectedProductName() {
        return selectedItem;
    }

    private void updatePrices() {
        this.totalBuyPrice = this.buyQuantity * this.unitBuyPrice;
        this.totalSellPrice = this.sellQuantity * this.unitSellPrice;
    }

    public void selectProduct(String item, int buyPrice, int sellPrice) {
        this.selectedItem = item;
        this.unitBuyPrice = buyPrice;
        this.unitSellPrice = sellPrice;
        this.buyQuantity = 1;
        this.sellQuantity = 1;
        updatePrices();
    }

    public int getBuyQuantity() { return buyQuantity; }
    public int getSellQuantity() { return sellQuantity; }
    public int getTotalBuyPrice() { return totalBuyPrice; }
    public int getTotalSellPrice() { return totalSellPrice; }

    public void confirmPurchase(MainController game) {
        if (selectedItem == null) return;

        if (game.inventory.money >= totalBuyPrice) {
            boolean itemProcessed = false;

            if (selectedItem.equals("WheatSeed")) {
                game.inventory.wheatSeeds += buyQuantity;
                itemProcessed = true;
            }
            else if (selectedItem.equals("WaterMelonSeed")) {
                game.inventory.waterMelonSeeds += buyQuantity;
                itemProcessed = true;
            }

            else if (selectedItem.equals("WheatFood")) {
                game.inventory.wheatStock += buyQuantity;
                itemProcessed = true;
            }
            else if (selectedItem.equals("WaterMelonFood")) {
                game.inventory.waterMelonStock += buyQuantity;
                itemProcessed = true;
            }
            else if (selectedItem.equals("Milk")) {
                game.inventory.milkStock += buyQuantity;
                itemProcessed = true;
            }
            else if (selectedItem.equals("Egg")) { // NOUVEAU
                game.inventory.eggStock += buyQuantity;
                itemProcessed = true;
            }
            else if (selectedItem.equals("GoldenEgg")) { // NOUVEAU
                game.inventory.goldenEggStock += buyQuantity;
                itemProcessed = true;
            }

            else if (selectedItem.equals("models.Cow")) {
                game.inventory.cowInventory += buyQuantity;
                itemProcessed = true;
            }
            else if (selectedItem.equals("models.Chicken")) { // NOUVEAU
                game.inventory.chickenInventory += buyQuantity;
                itemProcessed = true;
            }
            else if (selectedItem.equals("models.GoldenChicken")) { // NOUVEAU
                game.inventory.goldenChickenInventory += buyQuantity;
                itemProcessed = true;
            }

            if (itemProcessed) {
                game.inventory.money -= totalBuyPrice;
                buyQuantity = 1;
                game.update();
            }
        }
    }

    public void confirmSale(MainController game) {
        boolean selItem = false;

        if (selectedItem.equals("WheatSeed") && game.inventory.wheatSeeds >= sellQuantity) {
            game.inventory.wheatSeeds -= sellQuantity; selItem = true;
        }
        else if (selectedItem.equals("WaterMelonSeed") && game.inventory.waterMelonSeeds >= sellQuantity) {
            game.inventory.waterMelonSeeds -= sellQuantity; selItem = true;
        }

        else if (selectedItem.equals("WheatFood") && game.inventory.wheatStock >= sellQuantity) {
            game.inventory.wheatStock -= sellQuantity; selItem = true;
        }
        else if (selectedItem.equals("WaterMelonFood") && game.inventory.waterMelonStock >= sellQuantity) {
            game.inventory.waterMelonStock -= sellQuantity; selItem = true;
        }

        else if (selectedItem.equals("Milk") && game.inventory.milkStock >= sellQuantity) {
            game.inventory.milkStock -= sellQuantity; selItem = true;
        }
        else if (selectedItem.equals("Egg") && game.inventory.eggStock >= sellQuantity) { // NOUVEAU
            game.inventory.eggStock -= sellQuantity; selItem = true;
        }
        else if (selectedItem.equals("GoldenEgg") && game.inventory.goldenEggStock >= sellQuantity) { // NOUVEAU
            game.inventory.goldenEggStock -= sellQuantity; selItem = true;
        }

        else if (selectedItem.equals("models.Cow") && game.inventory.cowInventory >= sellQuantity) {
            game.inventory.cowInventory -= sellQuantity; selItem = true;
        }
        else if (selectedItem.equals("models.Chicken") && game.inventory.chickenInventory >= sellQuantity) {
            game.inventory.chickenInventory -= sellQuantity; selItem = true;
        }
        else if (selectedItem.equals("models.GoldenChicken") && game.inventory.goldenChickenInventory >= sellQuantity) {
            game.inventory.goldenChickenInventory -= sellQuantity; selItem = true;
        }

        if (selItem) {
            game.inventory.money += totalSellPrice;
            sellQuantity = 1;
            game.update();
        }
    }
}