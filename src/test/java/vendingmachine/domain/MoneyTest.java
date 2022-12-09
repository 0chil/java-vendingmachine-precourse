package vendingmachine.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MoneyTest {
    @ParameterizedTest
    @CsvSource(value = {"0,1000,1000", "100,0,100", "100,150,250"})
    void 금액을_추가할_수_있다(int initial, int addAmount, int expectedAmount) {
        Money money = new Money(initial);

        assertThat(money.add(addAmount)).isEqualTo(new Money(expectedAmount));
    }

    @Test
    void 금액을_뺄_수_있다() {
        Money money = new Money(1500);

        assertThat(money.subtract(1000)).isEqualTo(new Money(500));
    }

    @Test
    void 기존_금액_이상_뺼_수_없다() {
        Money money = new Money(1500);

        assertThatIllegalArgumentException().isThrownBy(() -> money.subtract(1501));
    }

    @Test
    void 금액은_음수가_될_수_없다() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Money(-1));
    }
}
