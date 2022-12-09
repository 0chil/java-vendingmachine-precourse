package vendingmachine.domain;

import java.util.EnumMap;
import java.util.Map;

import vendingmachine.constant.Coin;

public class Coins {

    private final Map<Coin, Integer> coinCounts;

    public Coins() {
        this(new EnumMap<>(Coin.class));
    }

    public Coins(Map<Coin, Integer> coinCounts) {
        this.coinCounts = new EnumMap<>(coinCounts);
    }

    public void subtract(Coin coin, int count) {
        changeCountOf(coin, countOf(coin) - count);
    }

    public void subtract(Coins coins) {
        for (Coin coin : coins.coinCounts.keySet()) {
            subtract(coin, coins.countOf(coin));
        }
    }

    public int countOf(Coin coin) {
        return coinCounts.getOrDefault(coin, 0);
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

    public int sum() {
        int sum = 0;
        for (Coin coin : coinCounts.keySet()) {
            sum += coin.times(countOf(coin));
        }
        return sum;
    }
}
