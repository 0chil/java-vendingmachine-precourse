package vendingmachine.domain;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import vendingmachine.constant.Coin;

public class CoinBox {
    private Coins coins;

    public CoinBox(int amount) {
        this(new Money(amount));
    }

    public CoinBox(Money money) {
        Map<Coin, Integer> coinCounts = new EnumMap<>(Coin.class);
        while (money.amount() != 0) {
            Coin coin = Coin.pickRandomLessOrEqualThan(money.amount());
            int previousCount = coinCounts.getOrDefault(coin, 0);
            coinCounts.put(coin, previousCount + 1);
            money = money.subtract(coin.amount());
        }
        this.coins = new Coins(coinCounts);
    }

    CoinBox(Map<Coin, Integer> coinCounts) {
        this.coins = new Coins(coinCounts);
    }

    public Money sum() {
        return coins.sum();
    }

    Coins drawChanges(Money requestedMoney) {
        Coins coinChanges = new Coins(getChangesFor(requestedMoney));
        coins = coins.subtract(coinChanges);
        return coinChanges;
    }

    private Map<Coin, Integer> getChangesFor(Money requestedAmount) {
        Map<Coin, Integer> changes = new HashMap<>();
        for (Coin coin : Coin.valuesInDescendingOrder()) {
            int drawCount = drawableCountOf(coin, requestedAmount.amount());
            requestedAmount = requestedAmount.subtract(coin.times(drawCount));
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
