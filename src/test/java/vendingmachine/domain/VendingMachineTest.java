package vendingmachine.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VendingMachineTest {

    @Test
    void 보유금액으로_동전을_무작위로_생성한다() {
        VendingMachine vendingMachine = new VendingMachine(10000);

        Assertions.assertThat(vendingMachine.hasMoreOrEqualCoinsThan(10000)).isTrue();
    }
}
