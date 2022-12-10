package vendingmachine.view;

import vendingmachine.constant.Coin;
import vendingmachine.domain.CoinBox;
import vendingmachine.domain.Coins;

public class OutputView {

    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String COIN_FORMAT = "%d원 - %d개\n";
    private static final String INSERTED_AMOUNT_FORMAT = "투입 금액: %d원\n";

    public void printCoinBox(CoinBox coinBox) {
        System.out.println("자판기가 보유한 동전");
        for (Coin coin : Coin.valuesInDescendingOrder()) {
            printCoin(coin, coinBox.count(coin));
        }
    }

    public void printChanges(Coins changes) {
        System.out.println("잔돈");
        for (Coin coin : Coin.valuesInDescendingOrder()) {
            if (changes.count(coin).isZero()) {
                continue;
            }
            printCoin(coin, changes.count(coin).count());
        }
    }

    private void printCoin(Coin coin, int count) {
        System.out.printf(COIN_FORMAT, coin.asMoney().amount(), count);
    }

    public void printInsertedAmount(int amount) {
        System.out.printf(INSERTED_AMOUNT_FORMAT, amount);
    }

    public void printError(String message) {
        System.out.println(ERROR_PREFIX + message);
    }
}
