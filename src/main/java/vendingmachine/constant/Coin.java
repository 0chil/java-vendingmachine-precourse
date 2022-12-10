package vendingmachine.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import camp.nextstep.edu.missionutils.Randoms;
import vendingmachine.domain.Money;

public enum Coin {
    COIN_500(500),
    COIN_100(100),
    COIN_50(50),
    COIN_10(10);

    private final int amount;

    Coin(final int amount) {
        this.amount = amount;
    }

    public Money amount() {
        return new Money(amount);
    }

    public int countUntil(int givenAmount) {
        return givenAmount / amount;
    }

    public int times(int multiplier) {
        return amount * multiplier;
    }

    private boolean isAffordableWith(Money money) {
        return amount().isAffordableWith(money);
    }

    public static List<Coin> valuesInDescendingOrder() {
        return Arrays.stream(values())
                .sorted()
                .collect(Collectors.toList());
    }

    public static Coin pickRandomLessOrEqualThan(Money money) {
        List<Integer> pickableAmounts = Arrays.stream(values())
                .filter(coin -> coin.isAffordableWith(money))
                .map(coin -> coin.amount)
                .collect(Collectors.toList());
        return Coin.valueOf(Randoms.pickNumberInList(pickableAmounts));
    }

    public static Coin valueOf(int amount) {
        return Arrays.stream(values())
                .filter(coin -> coin.amount == amount)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("금액에 맞는 동전이 없습니다"));
    }
}
