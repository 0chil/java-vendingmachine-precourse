package vendingmachine.controller;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import vendingmachine.domain.CoinBox;
import vendingmachine.domain.Product;
import vendingmachine.domain.Products;
import vendingmachine.domain.VendingMachine;
import vendingmachine.dto.ProductDto;
import vendingmachine.view.InputView;
import vendingmachine.view.OutputView;

public class MachineController {

    private static final InputView INPUT_VIEW = new InputView();
    private static final OutputView OUTPUT_VIEW = new OutputView();
    private VendingMachine vendingMachine;

    public void run() {
        this.vendingMachine = new VendingMachine(createCoinBox(), new Products(receiveProducts()));
        insertMoney();
        while (vendingMachine.isAnyAvailable()) {
            OUTPUT_VIEW.printInsertedAmount(vendingMachine.getInsertedAmount());
            purchaseProduct();
        }
        OUTPUT_VIEW.printInsertedAmount(vendingMachine.getInsertedAmount());
        OUTPUT_VIEW.printChanges(vendingMachine.drawChanges());
    }

    private void insertMoney() {
        repeat(() -> vendingMachine.insertAmount(INPUT_VIEW.promptInsertAmount()));
    }

    private void purchaseProduct() {
        repeat(() -> vendingMachine.purchaseProduct(INPUT_VIEW.promptProductToBuy()));
    }

    private List<Product> receiveProducts() {
        return repeat(() -> INPUT_VIEW.promptProducts()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList()));
    }

    private Product convert(ProductDto productDto) {
        return new Product(productDto.getName(), productDto.getPrice(), productDto.getCount());
    }

    private CoinBox createCoinBox() {
        return repeat(() -> {
            CoinBox coinBox = new CoinBox(INPUT_VIEW.promptEmbeddedMoney());
            OUTPUT_VIEW.printCoinBox(coinBox);
            return coinBox;
        });
    }

    private <T> T repeat(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            OUTPUT_VIEW.printError(e.getMessage());
            return repeat(supplier);
        }
    }

    private void repeat(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException e) {
            OUTPUT_VIEW.printError(e.getMessage());
            repeat(runnable);
        }
    }
}
