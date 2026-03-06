public class Market {
    private Object selectedItem;
    private int quantity = 1;
    private int unitPrice = 0;
    private int totalPrice = 0;

    public void incrementQuantity() {
        quantity += 1;
        updateTotalPrice();
    }

    public void decrementQuantity() {
        if (quantity > 1) {
            quantity -= 1;
            updateTotalPrice();
        }
    }

    private void updateTotalPrice() {
        this.totalPrice = this.quantity * this.unitPrice;
    }

    public void selectProduct(Object item, int price) {
        selectedItem = item;
        unitPrice = price;
        quantity = 1;
        updateTotalPrice();
    }

    public int getQuantity() { return quantity; }
    public int getTotalPrice() { return totalPrice; }

    public void confirmPurchase(MainController game) {
        if (game.money >= totalPrice && selectedItem != null) {
            boolean itemProcessed = false;

            if (selectedItem.equals("WheatSeed")) { game.wheatSeeds += quantity; itemProcessed = true; }
            else if (selectedItem.equals("WaterMelonSeed")) { game.waterMelonSeeds += quantity; itemProcessed = true; }
            else if (selectedItem.equals("WheatFood")) { game.wheatStock += quantity; itemProcessed = true; }
            else if (selectedItem.equals("WaterMelonFood")) { game.waterMelonStock += quantity; itemProcessed = true; }
            else if (selectedItem.equals("Cow")) { game.cowInventory += quantity; itemProcessed = true; }
            else if (selectedItem.equals("Milk")) { game.milkStock += quantity; itemProcessed = true; } // Achat de lait

            if (itemProcessed) {
                game.money -= totalPrice;
                game.update();
            }
        }
    }

    public void confirmSale(MainController game) {
        boolean selItem = false;

        if (selectedItem.equals("WheatSeed") && game.wheatSeeds >= quantity) { game.wheatSeeds -= quantity; selItem = true; }
        else if (selectedItem.equals("WaterMelonSeed") && game.waterMelonSeeds >= quantity) { game.waterMelonSeeds -= quantity; selItem = true; }
        else if (selectedItem.equals("WheatFood") && game.wheatStock >= quantity) { game.wheatStock -= quantity; selItem = true; }
        else if (selectedItem.equals("WaterMelonFood") && game.waterMelonStock >= quantity) { game.waterMelonStock -= quantity; selItem = true; }
        else if (selectedItem.equals("Milk") && game.milkStock >= quantity) { game.milkStock -= quantity; selItem = true; } // Vente de lait

        if (selItem) {
            game.money += totalPrice;
            game.update();
        }
    }
}