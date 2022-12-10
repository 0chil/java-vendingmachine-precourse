package vendingmachine.domain;

import vendingmachine.domain.vo.Money;

public class VendingMachine {
    private final CoinBox coinBox;
    private Money insertedMoney = new Money();
    private final Products products;

    public VendingMachine(CoinBox coinBox, Products products) {
        this.coinBox = coinBox;
        this.products = products;
    }

    public void insertAmount(Money money) {
        insertedMoney = insertedMoney.add(money);
    }

    public Coins drawChanges() {
        return coinBox.drawChanges(insertedMoney);
    }

    public void purchaseProduct(String name) {
        products.sell(name);
        insertedMoney = insertedMoney.subtract(products.priceOf(name));
    }
}
