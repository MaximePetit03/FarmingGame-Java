public abstract class Item {
    public String name;
    public int buyPrice;
    public int sellPrice;

    public Item(String name, int buyPrice, int sellPrice) {
        this.name = name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }
}