package vendingmachine.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VendingMachine {
    private final int embeddedAmount;
    private int insertedAmount = 0;
    private final Map<String, Product> products;

    public VendingMachine(int embeddedAmount) {
        this.embeddedAmount = embeddedAmount;
        this.products = new HashMap<>();
    }

    public boolean hasMoreOrEqualAmountThan(int amount) {
        return amount <= embeddedAmount;
    }

    public void insertAmount(int amount) {
        insertedAmount += amount;
    }

    public void fillProduct(String name, int price, int count) {
        validateNotExists(name);
        products.put(name, new Product(name, price, count));
    }

    private void validateNotExists(String productName) {
        if (hasProduct(productName)) {
            throw new IllegalArgumentException("이미 있는 상품은 추가할 수 없습니다");
        }
    }

    public boolean hasProduct(String name) {
        return Objects.nonNull(products.get(name));
    }

    public int insertedAmount() {
        return insertedAmount;
    }
}
