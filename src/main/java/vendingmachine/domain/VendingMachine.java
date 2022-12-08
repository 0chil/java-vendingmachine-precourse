package vendingmachine.domain;

public class VendingMachine {
    private final int embeddedAmount;
    private int insertedAmount = 0;
    private final Products products;

    public VendingMachine(int embeddedAmount, Products products) {
        this.embeddedAmount = embeddedAmount;
        this.products = products;
    }

    public boolean hasMoreOrEqualAmountThan(int amount) {
        return amount <= embeddedAmount;
    }

    public void insertAmount(int amount) {
        insertedAmount += amount;
    }

    public Changes drawChanges() {
        return new Changes(insertedAmount);
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
