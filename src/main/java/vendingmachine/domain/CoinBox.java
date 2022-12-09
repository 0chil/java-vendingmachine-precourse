package vendingmachine.domain;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import vendingmachine.constant.Coin;

public class CoinBox {
    private final Map<Coin, Integer> coinCounts;

    public CoinBox(int amount) {
        Map<Coin, Integer> coinCounts = new EnumMap<>(Coin.class);
        while (amount != 0) {
            Coin coin = Coin.pickRandomLessOrEqualThan(amount);
            int previousCount = coinCounts.getOrDefault(coin, 0);
            coinCounts.put(coin, previousCount + 1);
            amount -= coin.getAmount();
        }
        this.coinCounts = coinCounts;
    }

    CoinBox(Map<Coin, Integer> coinCounts) {
        this.coinCounts = new EnumMap<>(coinCounts);
    }

    public int sum() {
        int sum = 0;
        for (Coin coin : Coin.values()) {
            sum += sumOf(coin);
        }
        return sum;
    }

    CoinBox drawChanges(int requestedAmount) {
        Map<Coin, Integer> changes = new HashMap<>();
        for (Coin coin : Coin.valuesInDescendingOrder()) {
            int drawCount = drawableCountOf(coin, requestedAmount);
            minus(coin, drawCount);
            requestedAmount -= coin.times(drawCount);
            changes.put(coin, drawCount);
        }
        return new CoinBox(changes);
    }

    private void minus(Coin coin, int count) {
        changeCountOf(coin, countOf(coin) - count);
    }

    private void changeCountOf(Coin coin, int count) {
        validateNotNegative(count);
        coinCounts.put(coin, count);
    }

    private void validateNotNegative(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("동전 개수는 음수가 될 수 없습니다");
        }
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
