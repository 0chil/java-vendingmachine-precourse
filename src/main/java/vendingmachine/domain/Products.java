package vendingmachine.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import vendingmachine.domain.vo.Money;

public class Products {

    private final Map<String, Product> products;

    Products() {
        this(List.of());
    }

    public Products(List<Product> products) {
        Map<String, Product> productsMap = new HashMap<>();
        for (Product product : products) {
            productsMap.put(product.getName(), product);
        }
        this.products = productsMap;
    }

    private Product find(String name) {
        Product product = products.get(name);
        validateNotNull(product);
        return product;
    }

    private void validateNotNull(Product product) {
        if (Objects.isNull(product)) {
            throw new IllegalArgumentException("그런 상품은 없습니다");
        }
    }

    public void sell(String name) {
        find(name).sell();
    }

    public Money priceOf(String name) {
        return find(name).getPrice();
    }

    public boolean isAnyAvailableWith(Money money) {
        for (Product product : products.values()) {
            if (!product.isSoldOut() && product.isAffordableWith(money)) {
                return true;
            }
        }
        return false;
    }
}
