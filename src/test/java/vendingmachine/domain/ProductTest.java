package vendingmachine.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTest {

    @Test
    void 가격은_100원_이상이어야한다() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Product("이름", 90, 1));
        assertThatNoException().isThrownBy(() -> new Product("이름", 100, 1));
    }

    @Test
    void 가격은_10원_단위로_나누어_떨어져야한다() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Product("이름", 101, 1));
    }

    @Test
    void 재고는_음수일_수_없다() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Product("이름", 100, -1));
        assertThatNoException().isThrownBy(() -> new Product("이름", 100, 0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 상품명은_공백일_수_없다(String name) {
        assertThatIllegalArgumentException().isThrownBy(() -> new Product(name, 100, 0));
    }

    @ParameterizedTest
    @CsvSource(value = {"0,true", "1,false"})
    void 품절인지_알_수_있다(int count, boolean expected) {
        Product product = new Product("이름", 100, count);

        assertThat(product.isSoldOut()).isEqualTo(expected);
    }

    @Test
    void 판매할_수_있다() {
        Product product = new Product("이름", 100, 1);
        product.sell();

        assertThat(product.isSoldOut()).isTrue();
    }
}