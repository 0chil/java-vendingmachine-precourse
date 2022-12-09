package vendingmachine.domain;

import java.util.EnumMap;
import java.util.Map;

import vendingmachine.constant.Coin;

public class Coins {

    private final Map<Coin, Integer> coinCounts;

    public Coins(Map<Coin, Integer> coinCounts) {
        this.coinCounts = new EnumMap<>(coinCounts);
    }

    public Coins subtract(Coin coin, int count) {
        return changeCountOf(coin, countOf(coin) - count);
    }

    public Coins subtract(Coins coins) {
        Coins result = this;
        for (Coin coin : coins.coinCounts.keySet()) {
            result = result.subtract(coin, coins.countOf(coin));
        }
        return result;
    }

    public int countOf(Coin coin) {
        return coinCounts.getOrDefault(coin, 0);
    }

    private Coins changeCountOf(Coin coin, int count) {
        validateNotNegative(count);
        Map<Coin, Integer> newCoins = new EnumMap<>(coinCounts);
        newCoins.put(coin, count);
        return new Coins(newCoins);
    }

    private void validateNotNegative(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("동전 개수는 음수가 될 수 없습니다");
        }
    }

    public int sum() {
        int sum = 0;
        for (Coin coin : coinCounts.keySet()) {
            sum += coin.times(countOf(coin));
        }
        return sum;
    }
}
