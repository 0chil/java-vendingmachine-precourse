package vendingmachine.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import vendingmachine.constant.Coin;

public class VendingMachineTest {

    @Test
    void 금액을_투입할_수_있다() {
        VendingMachine vendingMachine = new VendingMachine(new CoinBox(10000), new Products());
        vendingMachine.insertAmount(2000);

        assertThat(vendingMachine.getLeftAmount()).isEqualTo(2000);
    }

    @Test
    void 투입금액을_최소개수의_동전으로_반환한다() {
        VendingMachine vendingMachine = new VendingMachine(new CoinBox(10000), new Products());
        vendingMachine.insertAmount(450);
        Map<Coin, Integer> expectedChanges = Map.ofEntries(
                Map.entry(Coin.COIN_500, 0),
                Map.entry(Coin.COIN_100, 4),
                Map.entry(Coin.COIN_50, 1),
                Map.entry(Coin.COIN_10, 0)
        );
        Coins changes = vendingMachine.drawChanges();

        assertThat(expectedChanges)
                .allSatisfy((coin, count) -> assertThat(changes.getCount(coin)).isEqualTo(count));
    }

    @Test
    void 상품을_구매한다() {
        Product coke = new Product("콜라", 2000, 1);
        Products products = new Products(List.of(coke));
        VendingMachine vendingMachine = new VendingMachine(new CoinBox(10000), products);
        vendingMachine.insertAmount(2000);
        vendingMachine.purchaseProduct("콜라");

        assertThat(vendingMachine.hasStockOf("콜라")).isFalse();
    }

    @Test
    void 상품이_없으면_오류를_던진다() {
        Products products = new Products(List.of(new Product("콜라", 2000, 1)));
        VendingMachine vendingMachine = new VendingMachine(new CoinBox(10000), products);
        vendingMachine.insertAmount(2000);

        assertThatIllegalArgumentException().isThrownBy(() -> vendingMachine.purchaseProduct("사이다"));
    }
}
