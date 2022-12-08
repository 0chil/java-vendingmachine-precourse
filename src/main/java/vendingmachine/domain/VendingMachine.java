package vendingmachine.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VendingMachine {
    private final int embeddedAmount;
    private final Map<String, Product> products;

    public VendingMachine(int embeddedAmount) {
        this.embeddedAmount = embeddedAmount;
        this.products = new HashMap<>();
    }

    public boolean hasMoreOrEqualAmountThan(int amount) {
        return amount <= embeddedAmount;
    }

    public void fillProduct(String name, int price, int count) {
        products.put(name, new Product(name, price, count));
    }

    public boolean hasProduct(String name) {
        return Objects.nonNull(products.get(name));
    }
}
