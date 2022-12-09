package vendingmachine.domain;

public class VendingMachine {
    private final CoinBox coinBox;
    private int insertedAmount = 0;
    private final Products products;

    public VendingMachine(CoinBox coinBox, Products products) {
        this.coinBox = coinBox;
        this.products = products;
    }

    public void insertAmount(int amount) {
        insertedAmount += amount;
    }

    public Coins drawChanges() {
        return coinBox.drawChanges(insertedAmount);
    }

    public void purchaseProduct(String name) {
        products.sell(name);
        insertedAmount -= products.priceOf(name);
    }

    public boolean hasStockOf(String name) {
        return !products.isSoldOut(name);
    }

    public int getLeftAmount() {
        return insertedAmount;
    }
}
