package vendingmachine.domain.vo;

import vendingmachine.constant.Coin;

public class Money {

    private static final int ZERO = 0;

    private final int amount;

    public Money(int amount) {
        validateNotNegative(amount);
        this.amount = amount;
    }

    public Money() {
        this(ZERO);
    }

    private void validateNotNegative(int amount) {
        if (amount < ZERO) {
            throw new IllegalArgumentException("금액은 음수가 될 수 없습니다");
        }
    }

    public Money add(Money money) {
        return new Money(this.amount + money.amount);
    }

    public Money subtract(Money money) {
        return new Money(this.amount - money.amount);
    }

    public Money subtract(Coin coin) {
        return this.subtract(coin.amount());
    }

    public int amount() {
        return amount;
    }

    public boolean isEmpty() {
        return amount == ZERO;
    }

    public boolean isAffordableWith(Money money) {
        return amount <= money.amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Money money = (Money)o;

        return amount == money.amount;
    }

    @Override
    public int hashCode() {
        return amount;
    }
}
