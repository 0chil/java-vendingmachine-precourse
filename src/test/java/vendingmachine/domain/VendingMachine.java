package vendingmachine.domain;

public class VendingMachine {

    private final int embeddedAmount;
    public VendingMachine(int embeddedAmount) {
        this.embeddedAmount = embeddedAmount;
    }

    public boolean hasMoreOrEqualCoinsThan(int amount) {
        return amount >= embeddedAmount;
    }
}
