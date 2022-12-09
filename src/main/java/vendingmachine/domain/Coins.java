package vendingmachine.domain;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import vendingmachine.constant.Coin;

public class Coins {
    private final Map<Coin, Integer> coinCounts;

    public Coins(int amount) {
        Map<Coin, Integer> coinCounts = new EnumMap<>(Coin.class);
        while (amount != 0) {
            Coin coin = Coin.pickRandomLessOrEqualThan(amount);
            int previousCount = coinCounts.getOrDefault(coin, 0);
            coinCounts.put(coin, previousCount + 1);
            amount -= coin.getAmount();
        }
        this.coinCounts = coinCounts;
    }

    Coins(Map<Coin, Integer> coinCounts) {
        this.coinCounts = coinCounts;
    }

    public int sum() {
        int sum = 0;
        for (Coin coin : Coin.values()) {
            sum += sumOf(coin);
        }
        return sum;
    }

    Coins drawChanges(int requestedAmount) {
        Map<Coin, Integer> changes = new HashMap<>();
        for (Coin coin : Coin.valuesInDescendingOrder()) {
            int drawCount = drawableCountOf(coin, requestedAmount);
            changes.put(coin, drawCount);
            requestedAmount -= coin.times(drawCount);
        }
        return new Coins(changes);
    }

    public Map<Coin, Integer> toMap() {
        return new EnumMap<>(coinCounts);
    }

    private int sumOf(Coin coin) {
        return coin.times(countOf(coin));
    }

    public int countOf(Coin coin) {
        return coinCounts.getOrDefault(coin, 0);
    }

    private int drawableCountOf(Coin coin, int requestedAmount) {
        return Math.min(countOf(coin), coin.countUntil(requestedAmount));
    }
}
