package vendingmachine.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class VendingMachineTest {

    @Test
    void 보유금액으로_동전을_무작위로_생성한다() {
        int amountToEmbed = 10000;
        VendingMachine vendingMachine = new VendingMachine(amountToEmbed);

        assertThat(vendingMachine.hasMoreOrEqualAmountThan(amountToEmbed)).isTrue();
        assertThat(vendingMachine.hasMoreOrEqualAmountThan(amountToEmbed + 1)).isFalse();
    }
}
