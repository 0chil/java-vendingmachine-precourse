package vendingmachine.domain;

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

    public Money add(int amount) {
        return new Money(this.amount + amount);
    }

    public Money subtract(int amount) {
        return new Money(this.amount - amount);
    }

    public int amount() {
        return amount;
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
