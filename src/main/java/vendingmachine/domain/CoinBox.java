package vendingmachine.domain;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import vendingmachine.constant.Coin;

public class CoinBox {
    private Coins coins;

    public CoinBox(int amount) {
        Map<Coin, Integer> coinCounts = new EnumMap<>(Coin.class);
        while (amount != 0) {
            Coin coin = Coin.pickRandomLessOrEqualThan(amount);
            int previousCount = coinCounts.getOrDefault(coin, 0);
            coinCounts.put(coin, previousCount + 1);
            amount -= coin.getAmount();
        }
        this.coins = new Coins(coinCounts);
    }

    CoinBox(Map<Coin, Integer> coinCounts) {
        this.coins = new Coins(coinCounts);
    }

    public int sum() {
        return coins.sum();
    }

    Coins drawChanges(int requestedAmount) {
        Coins coinChanges = new Coins(getChangesFor(requestedAmount));
        coins = coins.subtract(coinChanges);
        return coinChanges;
    }

    private Map<Coin, Integer> getChangesFor(int requestedAmount) {
        Map<Coin, Integer> changes = new HashMap<>();
        for (Coin coin : Coin.valuesInDescendingOrder()) {
            int drawCount = drawableCountOf(coin, requestedAmount);
            requestedAmount -= coin.times(drawCount);
            changes.put(coin, drawCount);
        }
        return changes;
    }

    public int countOf(Coin coin) {
        return coins.countOf(coin);
    }

    private int drawableCountOf(Coin coin, int requestedAmount) {
        return Math.min(countOf(coin), coin.countUntil(requestedAmount));
    }
}
