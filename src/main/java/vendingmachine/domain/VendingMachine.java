package vendingmachine.domain;

public class VendingMachine {
    private final CoinBox coinBox;
    private Money insertedMoney = new Money();
    private final Products products;

    public VendingMachine(CoinBox coinBox, Products products) {
        this.coinBox = coinBox;
        this.products = products;
    }

    public void insertAmount(int amount) {
        insertedMoney = insertedMoney.add(amount);
    }

    public Coins drawChanges() {
        return coinBox.drawChanges(insertedMoney);
    }

    public void purchaseProduct(String name) {
        products.sell(name);
        insertedMoney = insertedMoney.subtract(products.priceOf(name));
    }

    public boolean hasStockOf(String name) {
        return !products.isSoldOut(name);
    }

    public int getLeftAmount() {
        return insertedMoney.amount();
    }
}
