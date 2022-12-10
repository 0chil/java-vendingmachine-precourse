package vendingmachine.domain;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import vendingmachine.constant.Coin;
import vendingmachine.domain.vo.Count;
import vendingmachine.domain.vo.Money;

public class CoinBox {
    private Coins coins;

    public CoinBox(int amount) {
        this(new Money(amount));
    }

    public CoinBox(Money money) {
        Map<Coin, Count> coinCounts = new EnumMap<>(Coin.class);
        while (money.amount() != 0) {
            Coin coin = Coin.pickRandomLessOrEqualThan(money);
            Count previousCount = coinCounts.getOrDefault(coin, new Count());
            coinCounts.put(coin, previousCount.increase());
            money = money.subtract(coin.amount());
        }
        this.coins = new Coins(coinCounts);
    }

    CoinBox(Map<Coin, Integer> coinCounts) {
        this.coins = Coins.create(coinCounts);
    }

    public Money sum() {
        return coins.sum();
    }

    Coins drawChanges(Money requestedMoney) {
        Coins coinChanges = new Coins(getChangesFor(requestedMoney.amount()));
        coins = coins.subtract(coinChanges);
        return coinChanges;
    }

    private Map<Coin, Count> getChangesFor(int requestedAmount) {
        Map<Coin, Count> changes = new HashMap<>();
        for (Coin coin : Coin.valuesInDescendingOrder()) {
            int drawCount = drawableCountOf(coin, requestedAmount);
            requestedAmount -= coin.times(drawCount);
            changes.put(coin, new Count(drawCount));
        }
        return changes;
    }

    public int countOf(Coin coin) {
        return coins.getCount(coin);
    }

    private int drawableCountOf(Coin coin, int requestedAmount) {
        return Math.min(countOf(coin), coin.countUntil(requestedAmount));
    }
}
