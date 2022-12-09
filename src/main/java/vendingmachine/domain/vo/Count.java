package vendingmachine.domain.vo;

public class Count {

    private static final int ZERO = 0;

    private final int count;

    public Count() {
        this(ZERO);
    }

    public Count(int count) {
        validateRange(count);
        this.count = count;
    }

    public Count increase() {
        return new Count(count + 1);
    }

    public Count decrease() {
        return new Count(count - 1);
    }

    public Count decrease(Count count) {
        return new Count(this.count - count.count);
    }

    private void validateRange(int count) {
        if (count < ZERO) {
            throw new IllegalArgumentException("금액은 음수가 될 수 없습니다");
        }
    }

    public boolean isZero() {
        return count == ZERO;
    }

    public int count() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Count count1 = (Count)o;

        return count == count1.count;
    }

    @Override
    public int hashCode() {
        return count;
    }
}
