package vendingmachine.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;

public class VendingMachineTest {

    @Test
    void 보유금액으로_동전을_무작위로_생성한다() {
        int amountToEmbed = 10000;
        VendingMachine vendingMachine = new VendingMachine(amountToEmbed);

        assertThat(vendingMachine.hasMoreOrEqualAmountThan(amountToEmbed)).isTrue();
        assertThat(vendingMachine.hasMoreOrEqualAmountThan(amountToEmbed + 1)).isFalse();
    }

    @Test
    void 상품명_가격_수량으로_상품을_추가한다() {
        VendingMachine vendingMachine = new VendingMachine(10000);
        vendingMachine.fillProduct("상품명", 2000, 1);

        assertThat(vendingMachine.hasProduct("상품명")).isTrue();
    }

    @Test
    void 이미_있는_상품은_추가할_수_없다() {
        VendingMachine vendingMachine = new VendingMachine(10000);
        vendingMachine.fillProduct("상품명", 2000, 1);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> vendingMachine.fillProduct("상품명", 2000, 1));
    }
}
