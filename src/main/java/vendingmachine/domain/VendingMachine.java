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
    private final Products products2;

    public VendingMachine(int embeddedAmount, Products products) {
        this.embeddedAmount = embeddedAmount;
        this.products2 = products;
    }

    public boolean hasMoreOrEqualAmountThan(int amount) {
        return amount <= embeddedAmount;
    }

    public void insertAmount(int amount) {
        insertedAmount += amount;
    }

    public boolean hasProduct(String name) {
        return products2.hasProduct(name);
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
}
