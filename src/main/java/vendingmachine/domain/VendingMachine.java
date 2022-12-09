package vendingmachine.domain;

public class VendingMachine {
    private final CoinBox embeddedAmount;
    private int insertedAmount = 0;
    private final Products products;

    public VendingMachine(int embeddedAmount, Products products) {
        this.embeddedAmount = new CoinBox(embeddedAmount);
        this.products = products;
    }

    public boolean hasMoreOrEqualAmountThan(int amount) {
        return amount <= embeddedAmount.sum();
    }

    public void insertAmount(int amount) {
        insertedAmount += amount;
    }

    public Coins drawChanges() {
        return embeddedAmount.drawChanges(insertedAmount);
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
