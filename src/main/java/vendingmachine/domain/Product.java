package vendingmachine.domain;

import vendingmachine.domain.vo.Count;

public class Product {

    private final String name;
    private final Money price;
    private Count count;

    public Product(String name, int price, int count) {
        this.name = name;
        this.price = new Money(price);
        this.count = new Count(count);
    }

    public void sell() {
        count = count.decrease();
    }

    public boolean isSoldOut() {
        return count.isZero();
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price.amount();
    }
}
