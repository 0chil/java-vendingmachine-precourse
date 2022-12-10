package vendingmachine.domain;

import java.util.EnumMap;
import java.util.Map;

import vendingmachine.constant.Coin;
import vendingmachine.domain.vo.Count;
import vendingmachine.domain.vo.Money;

public class Coins {

    private final Map<Coin, Count> coinCounts;

    Coins(Map<Coin, Count> coinCounts) {
        this.coinCounts = coinCounts;
    }

    public static Coins create(Map<Coin, Integer> newCoins) {
        Map<Coin, Count> countMap = new EnumMap<>(Coin.class);
        for (Map.Entry<Coin, Integer> entry : newCoins.entrySet()) {
            countMap.put(entry.getKey(), new Count(entry.getValue()));
        }
        return new Coins(countMap);
    }

    private Coins subtract(Coin coin, Count count) {
        return changeCountOf(coin, count(coin).decrease(count));
    }

    public Coins subtract(Coins otherCoins) {
        Coins result = this;
        for (Coin coin : otherCoins.coinCounts.keySet()) {
            result = result.subtract(coin, otherCoins.count(coin));
        }
        return result;
    }

    public Count count(Coin coin) {
        return coinCounts.getOrDefault(coin, new Count(0));
    }

    private Coins changeCountOf(Coin coin, Count count) {
        Map<Coin, Count> newCoins = new EnumMap<>(coinCounts);
        newCoins.put(coin, count);
        return new Coins(newCoins);
    }

    public Money sum() {
        Money sum = new Money();
        for (Coin coin : coinCounts.keySet()) {
            sum = sum.add(coin.times(count(coin)));
        }
        return sum;
    }
}
