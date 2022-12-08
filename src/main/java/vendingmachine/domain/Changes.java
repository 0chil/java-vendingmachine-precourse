package vendingmachine.domain;

import java.util.HashMap;
import java.util.Map;

import vendingmachine.constant.Coin;

public class Changes {

    private final Map<Coin, Integer> changes;

    public Changes(int amount) {
        Map<Coin, Integer> changes = new HashMap<>();
        for (Coin coin : Coin.valuesInDescendingOrder()) {
            int drawCount = coin.countUpUntil(amount);
            changes.put(coin, drawCount);
            amount -= coin.times(drawCount);
        }
        this.changes = changes;
    }

    public int getCountOf(Coin coin) {
        return changes.get(coin);
    }
}
