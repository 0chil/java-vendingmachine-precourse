package vendingmachine.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import vendingmachine.constant.Coin;

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

    public int insertedAmount() {
        return insertedAmount;
    }

    public Map<Coin, Integer> drawChanges() {
        Map<Coin, Integer> changes = new HashMap<>();
        List<Coin> coinsInDescendingOrder = Arrays.stream(Coin.values())
                .sorted()
                .collect(Collectors.toList());
        int changesLeft = insertedAmount;
        for (Coin coin : coinsInDescendingOrder) {
            int drawCount = changesLeft / coin.getAmount();
            changes.put(coin, drawCount);
            changesLeft %= coin.getAmount();
        }
        return changes;
    }

    public void purchaseProduct(String name) {
        products.sell(name);
        insertedAmount -= products.priceOf(name);
    }

    public boolean hasStockOf(String name) {
        return !products.isSoldOut(name);
    }
}
