package vendingmachine.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

import vendingmachine.constant.Coin;

public class CoinBoxTest {

    @Test
    void 투입금액으로_동전을_무작위로_만든다() {
        Money insertedMoney = new Money(450);
        CoinBox coinBox = new CoinBox(insertedMoney);

        assertThat(coinBox.sum()).isEqualTo(insertedMoney);
    }

    @Test
    void 잔돈을_반환한다() {
        Map<Coin, Integer> coinMap = Map.ofEntries(
                Map.entry(Coin.COIN_500, 5),
                Map.entry(Coin.COIN_100, 5),
                Map.entry(Coin.COIN_50, 2),
                Map.entry(Coin.COIN_10, 3)
        );
        Money drawAmount = new Money(450);
        CoinBox coinBox = new CoinBox(coinMap);
        Coins changes = coinBox.drawChanges(drawAmount);

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
        CoinBox coinBox = new CoinBox(coinMap);
        Coins changes = coinBox.drawChanges(new Money(450));

        assertThat(expectedChanges)
                .allSatisfy((coin, count) -> assertThat(changes.countOf(coin)).isEqualTo(count));
    }

    @Test
    void 잔돈을_반환할_수_없으면_반환할_수_있는_금액만_반환한다() {
        Map<Coin, Integer> coinMap = Map.ofEntries(
                Map.entry(Coin.COIN_500, 5),
                Map.entry(Coin.COIN_100, 5),
                Map.entry(Coin.COIN_50, 0),
                Map.entry(Coin.COIN_10, 3)
        );
        Map<Coin, Integer> expectedChanges = Map.ofEntries(
                Map.entry(Coin.COIN_500, 0),
                Map.entry(Coin.COIN_100, 4),
                Map.entry(Coin.COIN_50, 0),
                Map.entry(Coin.COIN_10, 3)
        );
        CoinBox coinBox = new CoinBox(coinMap);
        Coins changes = coinBox.drawChanges(new Money(450));

        assertThat(expectedChanges)
                .allSatisfy((coin, count) -> assertThat(changes.countOf(coin)).isEqualTo(count));
    }

    @Test
    void 잔돈을_반환하면_그만큼_줄어든다() {
        Map<Coin, Integer> coinMap = Map.ofEntries(
                Map.entry(Coin.COIN_500, 5),
                Map.entry(Coin.COIN_100, 5),
                Map.entry(Coin.COIN_50, 0),
                Map.entry(Coin.COIN_10, 3)
        );
        Map<Coin, Integer> expectedCoins = Map.ofEntries(
                Map.entry(Coin.COIN_500, 5),
                Map.entry(Coin.COIN_100, 1),
                Map.entry(Coin.COIN_50, 0),
                Map.entry(Coin.COIN_10, 0)
        );
        CoinBox coinBox = new CoinBox(coinMap);
        coinBox.drawChanges(new Money(450));

        assertThat(expectedCoins)
                .allSatisfy((coin, count) -> assertThat(coinBox.countOf(coin)).isEqualTo(count));
    }
}
