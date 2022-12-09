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

    private Product find(String name) {
        validateNameExists(name);
        return products.get(name);
    }

    private void validateNameExists(String name) {
        if (!hasProduct(name)) {
            throw new IllegalArgumentException("그런 상품은 없습니다");
        }
    }

    public void sell(String name) {
        find(name).sell();
    }

    public boolean isSoldOut(String name) {
        return find(name).isSoldOut();
    }

    public int priceOf(String name) {
        return find(name).getPrice();
    }

    public boolean hasProduct(String name) {
        return Objects.nonNull(products.get(name));
    }
}
