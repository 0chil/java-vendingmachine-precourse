package vendingmachine.domain;

import static java.lang.Math.min;

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
        while (!money.isEmpty()) {
            Coin coin = Coin.pickRandomLessOrEqualThan(money);
            Count previousCount = coinCounts.getOrDefault(coin, new Count());
            coinCounts.put(coin, previousCount.increase());
            money = money.subtract(coin);
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
        Coins changes = new Coins(getChangesFor(requestedMoney));
        coins = coins.subtract(changes);
        return changes;
    }

    private Map<Coin, Count> getChangesFor(Money requestedAmount) {
        Map<Coin, Count> changes = new HashMap<>();
        for (Coin coin : Coin.valuesInDescendingOrder()) {
            Count drawCount = drawableCountOf(coin, requestedAmount);
            requestedAmount = requestedAmount.subtract(coin.times(drawCount));
            changes.put(coin, drawCount);
        }
        return changes;
    }

    public int countOf(Coin coin) {
        return coins.countOf(coin).count();
    }

    private Count drawableCountOf(Coin coin, Money requestedAmount) {
        return new Count(min(countOf(coin), coin.countUntil(requestedAmount)));
    }
}
