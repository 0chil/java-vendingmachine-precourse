package vendingmachine.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

import vendingmachine.constant.Coin;

public class VendingMachineTest {

    @Test
    void 보유금액으로_동전을_무작위로_생성한다() {
        int amountToEmbed = 10000;
        VendingMachine vendingMachine = new VendingMachine(amountToEmbed, new Products());

        assertThat(vendingMachine.hasMoreOrEqualAmountThan(amountToEmbed)).isTrue();
        assertThat(vendingMachine.hasMoreOrEqualAmountThan(amountToEmbed + 1)).isFalse();
    }

    @Test
    void 금액을_투입할_수_있다() {
        VendingMachine vendingMachine = new VendingMachine(10000, new Products());
        vendingMachine.insertAmount(2000);

        assertThat(vendingMachine.insertedAmount()).isEqualTo(2000);
    }

    @Test
    void 투입금액을_최소개수의_동전으로_반환한다() {
        VendingMachine vendingMachine = new VendingMachine(10000, new Products());
        vendingMachine.insertAmount(450);
        Map<Coin, Integer> expectedChanges = Map.ofEntries(
                Map.entry(Coin.COIN_500, 0),
                Map.entry(Coin.COIN_100, 4),
                Map.entry(Coin.COIN_50, 1),
                Map.entry(Coin.COIN_10, 0)
        );

        assertThat(vendingMachine.drawChanges()).containsAllEntriesOf(expectedChanges);
    }
}
