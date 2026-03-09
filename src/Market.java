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

        if (selectedItem.equals("Cow") && !game.cowUnlocked) {
            if (game.money >= 1000) {
                game.money -= 1000;
                game.cowUnlocked = true;
                game.update();
            }
            return;
        }

        if (game.money >= totalBuyPrice) {
            boolean itemProcessed = false;

            if (selectedItem.equals("WheatSeed")) { game.wheatSeeds += buyQuantity; itemProcessed = true; }
            else if (selectedItem.equals("WaterMelonSeed")) { game.waterMelonSeeds += buyQuantity; itemProcessed = true; }
            else if (selectedItem.equals("WheatFood")) { game.wheatStock += buyQuantity; itemProcessed = true; }
            else if (selectedItem.equals("WaterMelonFood")) { game.waterMelonStock += buyQuantity; itemProcessed = true; }
            else if (selectedItem.equals("Cow")) { game.cowInventory += buyQuantity; itemProcessed = true; }
            else if (selectedItem.equals("Milk")) { game.milkStock += buyQuantity; itemProcessed = true; }

            if (itemProcessed) {
                game.money -= totalBuyPrice;
                game.update();
            }
        }
    }

    public void confirmSale(MainController game) {
        boolean selItem = false;

        if (selectedItem.equals("WheatSeed") && game.wheatSeeds >= sellQuantity) { game.wheatSeeds -= sellQuantity; selItem = true; }
        else if (selectedItem.equals("WaterMelonSeed") && game.waterMelonSeeds >= sellQuantity) { game.waterMelonSeeds -= sellQuantity; selItem = true; }
        else if (selectedItem.equals("WheatFood") && game.wheatStock >= sellQuantity) { game.wheatStock -= sellQuantity; selItem = true; }
        else if (selectedItem.equals("WaterMelonFood") && game.waterMelonStock >= sellQuantity) { game.waterMelonStock -= sellQuantity; selItem = true; }
        else if (selectedItem.equals("Milk") && game.milkStock >= sellQuantity) { game.milkStock -= sellQuantity; selItem = true; }

        if (selItem) {
            game.money += totalSellPrice;
            game.update();
        }
    }
}