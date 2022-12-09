package vendingmachine.domain;

import java.util.EnumMap;
import java.util.Map;

import vendingmachine.constant.Coin;
import vendingmachine.domain.vo.Count;

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

    public Coins subtract(Coin coin, Count count) {
        return changeCountOf(coin, countOf(coin).decrease(count));
    }

    public Coins subtract(Coins coins) {
        Coins result = this;
        for (Coin coin : coins.coinCounts.keySet()) {
            result = result.subtract(coin, coins.countOf(coin));
        }
        return result;
    }

    public int getCount(Coin coin) {
        return countOf(coin).count();
    }

    private Count countOf(Coin coin) {
        return coinCounts.getOrDefault(coin, new Count(0));
    }

    private Coins changeCountOf(Coin coin, Count count) {
        Map<Coin, Count> newCoins = new EnumMap<>(coinCounts);
        newCoins.put(coin, count);
        return new Coins(newCoins);
    }

    public Money sum() {
        int sum = 0;
        for (Coin coin : coinCounts.keySet()) {
            sum += coin.times(getCount(coin));
        }
        return new Money(sum);
    }
}
