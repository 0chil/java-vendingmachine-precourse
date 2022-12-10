package vendingmachine.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class ProductsTest {

    @Test
    void 상품명_가격_수량으로_상품을_추가한다() {
        List<Product> productList = List.of(new Product("상품명", 2000, 1));
        Products products = new Products(productList);

        assertThat(products.isAnyAvailableWith(new Money(2000))).isTrue();
    }

    @Test
    void 판매가_가능한지_알_수_있다() {
        List<Product> productList = List.of(new Product("상품명", 2000, 0));
        Products products = new Products(productList);

        assertThat(products.isAnyAvailableWith(new Money(2000))).isFalse();
    }
}