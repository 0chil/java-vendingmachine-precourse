package vendingmachine.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import camp.nextstep.edu.missionutils.Randoms;

public enum Coin {
    COIN_500(500),
    COIN_100(100),
    COIN_50(50),
    COIN_10(10);

    private final int amount;

    Coin(final int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public int countUntil(int givenAmount) {
        return givenAmount / amount;
    }

    public int times(int multiplier) {
        return amount * multiplier;
    }

    private boolean isAmountLessOrEqualThan(int amount) {
        return this.amount <= amount;
    }

    public static List<Coin> valuesInDescendingOrder() {
        return Arrays.stream(values())
                .sorted()
                .collect(Collectors.toList());
    }

    public static Coin pickRandomLessOrEqualThan(int amount) {
        List<Integer> pickableAmounts = Arrays.stream(values())
                .filter(coin -> coin.isAmountLessOrEqualThan(amount))
                .map(Coin::getAmount)
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
