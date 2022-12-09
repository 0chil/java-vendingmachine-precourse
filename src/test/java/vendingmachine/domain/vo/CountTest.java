package vendingmachine.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import vendingmachine.domain.vo.Count;

public class CountTest {
    @ParameterizedTest
    @CsvSource(value = {"0,1", "100, 101"})
    void 추가할_수_있다(int initial, int expectedCount) {
        Count count = new Count(initial);

        assertThat(count.increase()).isEqualTo(new Count(expectedCount));
    }

    @Test
    void 뺄_수_있다() {
        Count count = new Count(500);

        assertThat(count.decrease()).isEqualTo(new Count(499));
    }

    @Test
    void 없는데_뺼_수_없다() {
        Count count = new Count(0);

        assertThatIllegalArgumentException().isThrownBy(count::decrease);
    }

    @Test
    void 음수가_될_수_없다() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Count(-1));
    }

    @Test
    void 숫자_0인지_알_수_있다() {
        assertThat(new Count(0).isZero()).isTrue();
    }
}
