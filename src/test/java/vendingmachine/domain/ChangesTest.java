package vendingmachine.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

import vendingmachine.constant.Coin;

public class ChangesTest {

    @Test
    void 금액을_최소개수의_동전으로_만든다() {
        Changes changes = new Changes(450);
        Map<Coin, Integer> expectedChanges = Map.ofEntries(
                Map.entry(Coin.COIN_500, 0),
                Map.entry(Coin.COIN_100, 4),
                Map.entry(Coin.COIN_50, 1),
                Map.entry(Coin.COIN_10, 0)
        );

        assertThat(expectedChanges)
                .allSatisfy((coin, count) -> assertThat(changes.getCountOf(coin)).isEqualTo(count));
    }
}
