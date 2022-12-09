package vendingmachine.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

import vendingmachine.constant.Coin;

public class CoinsTest {

    @Test
    void 투입금액으로_동전을_무작위로_만든다() {
        Coins coins = new Coins(450);

        assertThat(coins.sum()).isEqualTo(450);
    }

    @Test
    void 잔돈을_반환한다() {
        Map<Coin, Integer> coinMap = Map.ofEntries(
                Map.entry(Coin.COIN_500, 5),
                Map.entry(Coin.COIN_100, 5),
                Map.entry(Coin.COIN_50, 2),
                Map.entry(Coin.COIN_10, 3)
        );
        int drawAmount = 450;
        Coins coins = new Coins(coinMap);
        Coins changes = coins.drawChanges(drawAmount);

        assertThat(changes.sum()).isEqualTo(drawAmount);
    }

    @Test
    void 최소개수의_동전으로_잔돈을_반환한다() {
        Map<Coin, Integer> coinMap = Map.ofEntries(
                Map.entry(Coin.COIN_500, 5),
                Map.entry(Coin.COIN_100, 5),
                Map.entry(Coin.COIN_50, 2),
                Map.entry(Coin.COIN_10, 3)
        );
        Map<Coin, Integer> expectedChanges = Map.ofEntries(
                Map.entry(Coin.COIN_500, 0),
                Map.entry(Coin.COIN_100, 4),
                Map.entry(Coin.COIN_50, 1),
                Map.entry(Coin.COIN_10, 0)
        );
        Coins coins = new Coins(coinMap);
        Coins changes = coins.drawChanges(450);

        assertThat(expectedChanges)
                .allSatisfy((coin, count) -> assertThat(changes.countOf(coin)).isEqualTo(count));
    }
}
