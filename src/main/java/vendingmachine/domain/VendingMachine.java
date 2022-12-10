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

    public void insertAmount(int amount) {
        insertedMoney = insertedMoney.add(new Money(amount));
    }

    public Coins drawChanges() {
        return coinBox.drawChanges(insertedMoney);
    }

    public boolean isAnyAvailable() {
        return products.isAnyAvailableWith(insertedMoney);
    }

    public void purchaseProduct(String name) {
        validateProductAffordable(name);
        validateProductHasStock(name);
        products.sell(name);
        insertedMoney = insertedMoney.subtract(products.priceOf(name));
    }

    private void validateProductHasStock(String name) {
        if (products.isSoldOut(name)) {
            throw new IllegalArgumentException("상품이 품절입니다");
        }
    }

    private void validateProductAffordable(String name) {
        if (!products.isAffordableWith(name, insertedMoney)) {
            throw new IllegalArgumentException("투입금액이 부족합니다");
        }
    }

    public int getInsertedAmount() {
        return insertedMoney.amount();
    }
}
