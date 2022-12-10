package vendingmachine.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.List;

import org.junit.jupiter.api.Test;

import vendingmachine.domain.vo.Money;

public class VendingMachineTest {

    @Test
    void 금액을_투입할_수_있다() {
        VendingMachine vendingMachine = new VendingMachine(new CoinBox(10000), new Products());
        vendingMachine.insertAmount(2000);

        assertThat(vendingMachine.drawChanges().sum()).isEqualTo(new Money(2000));
    }

    @Test
    void 상품을_구매한다() {
        Product coke = new Product("콜라", 2000, 1);
        Products products = new Products(List.of(coke));
        VendingMachine vendingMachine = new VendingMachine(new CoinBox(10000), products);
        vendingMachine.insertAmount(2000);

        assertThatNoException().isThrownBy(() -> vendingMachine.purchaseProduct("콜라"));
    }

    @Test
    void 가진_돈을_넘어서_상품을_구매할_수_없다() {
        Product coke = new Product("콜라", 2000, 1);
        Products products = new Products(List.of(coke));
        VendingMachine vendingMachine = new VendingMachine(new CoinBox(10000), products);
        vendingMachine.insertAmount(2000);
        vendingMachine.purchaseProduct("콜라");

        assertThatIllegalArgumentException().isThrownBy(() -> vendingMachine.purchaseProduct("콜라"));
    }

    @Test
    void 상품이_없으면_오류를_던진다() {
        Products products = new Products(List.of(new Product("콜라", 2000, 1)));
        VendingMachine vendingMachine = new VendingMachine(new CoinBox(10000), products);
        vendingMachine.insertAmount(2000);

        assertThatIllegalArgumentException().isThrownBy(() -> vendingMachine.purchaseProduct("사이다"));
    }
}
