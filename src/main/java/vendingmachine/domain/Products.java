package vendingmachine.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public void sell(String name) {
        validateName(name);
        products.get(name).sell();
    }

    public boolean isSoldOut(String name) {
        validateName(name);
        return products.get(name).isSoldOut();
    }

    public int priceOf(String name) {
        validateName(name);
        return products.get(name).getPrice();
    }

    private void validateName(String name) {
        if (!hasProduct(name)) {
            throw new IllegalArgumentException("그런 상품은 없습니다");
        }
    }

    public boolean hasProduct(String name) {
        return Objects.nonNull(products.get(name));
    }
}
