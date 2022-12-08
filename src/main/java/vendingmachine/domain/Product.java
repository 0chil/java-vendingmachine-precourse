package vendingmachine.domain;

public class Product {

    private final String name;
    private final int price;
    private int count;

    public Product(String name, int price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public void sell() {
        validateNotSoldOut();
        count -= 1;
    }

    private void validateNotSoldOut() {
        if (isSoldOut()) {
            throw new IllegalArgumentException("재고가 소진된 상품입니다");
        }
    }

    public boolean isSoldOut() {
        return count == 0;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
