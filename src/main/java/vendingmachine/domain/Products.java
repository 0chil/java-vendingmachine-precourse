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

    public boolean hasProduct(String name) {
        return Objects.nonNull(products.get(name));
    }
}
