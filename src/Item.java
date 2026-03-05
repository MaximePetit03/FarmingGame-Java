public abstract class Item {
    String name;
    int buyPrice;
    int sellPrice;

    public Item(String name, int buyPrice, int sellPrice) {
        this.name = name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }
}
