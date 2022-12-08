package vendingmachine.domain;

public class VendingMachine {

    private final int embeddedAmount;
    public VendingMachine(int embeddedAmount) {
        this.embeddedAmount = embeddedAmount;
    }

    public boolean hasMoreOrEqualAmountThan(int amount) {
        return amount <= embeddedAmount;
    }
}
