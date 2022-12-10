package vendingmachine.domain;

import java.util.Objects;

import vendingmachine.domain.vo.Count;
import vendingmachine.domain.vo.Money;

public class Product {

    private static final int PRICE_MINIMUM = 100;
    private static final int PRICE_UNIT = 10;

    private final String name;
    private final Money price;
    private Count count;

    public Product(String name, int price, int count) {
        validatePriceRange(price);
        validatePriceUnit(price);
        validateNameNotBlank(name);
        this.name = name;
        this.price = new Money(price);
        this.count = new Count(count);
    }

    public void sell() {
        count = count.decrease();
    }

    private void validateNameNotBlank(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다");
        }
    }

    private void validatePriceRange(int price) {
        if (price < PRICE_MINIMUM) {
            throw new IllegalArgumentException("상품의 가격이 너무 작습니다");
        }
    }

    private void validatePriceUnit(int price) {
        if (price % PRICE_UNIT != 0) {
            throw new IllegalArgumentException("상품의 가격 단위가 잘못되었습니다");
        }
    }

    public boolean isSoldOut() {
        return count.isZero();
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public boolean isAffordableWith(Money money) {
        return price.isAffordableWith(money);
    }
}
