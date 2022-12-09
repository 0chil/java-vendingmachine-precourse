package vendingmachine.domain;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static vendingmachine.constant.Coin.COIN_10;
import static vendingmachine.constant.Coin.COIN_100;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import vendingmachine.constant.Coin;

public class CoinsTest {

    @ParameterizedTest(name = "5개 - {0}개 = {1}")
    @CsvSource(value = {"5,0", "3,2"})
    void 동전을_뺄_수_있다(int subtractCount, int expectedCount) {
        Map<Coin, Integer> coinMap = ofEntries(entry(COIN_100, 5));
        Coins coins = new Coins(coinMap);
        coins.subtract(COIN_100, subtractCount);

        assertThat(coins.countOf(COIN_100)).isEqualTo(expectedCount);
    }

    @Test
    void 가진_동전_이상으로_뺄_수_없다() {
        Map<Coin, Integer> coinMap = ofEntries(entry(COIN_100, 5));
        Coins coins = new Coins(coinMap);

        assertThatIllegalArgumentException().isThrownBy(() -> coins.subtract(COIN_100, 6));
    }

    @Test
    void 동전이_몇_개인지_알_수_있다() {
        Map<Coin, Integer> coinMap = ofEntries(entry(COIN_100, 5));
        Coins coins = new Coins(coinMap);

        assertThat(coins.countOf(COIN_100)).isEqualTo(5);
    }

    @Test
    void 총_금액을_알_수_있다() {
        Map<Coin, Integer> coinMap = ofEntries(entry(COIN_100, 5), entry(COIN_10, 5));
        Coins coins = new Coins(coinMap);

        assertThat(coins.sum()).isEqualTo(550);
    }
}
