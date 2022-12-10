package vendingmachine.view;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import camp.nextstep.edu.missionutils.Console;
import vendingmachine.dto.ProductDto;

public class InputView {

    private static final Pattern PRODUCT_INPUT_PATTERN = Pattern.compile(
            "\\[(.+),(\\d+),(\\d+)\\](;\\[(.+),(\\d+),(\\d+)\\])*");
    private static final Pattern PRODUCT_PATTERN = Pattern.compile("\\[(.+),(\\d+),(\\d+)\\]");
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("-?\\d+");

    public int promptEmbeddedMoney() {
        System.out.println("자판기가 보유하고 있는 금액을 입력해 주세요.");
        String input = Console.readLine();
        validateNumeric(input);
        return Integer.parseInt(input);
    }

    public List<ProductDto> promptProducts() {
        System.out.println("상품명과 가격, 수량을 입력해 주세요.");

        String input = Console.readLine();
        if (!PRODUCT_INPUT_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException("상품 입력 형식이 잘못되었습니다");
        }
        String[] products = input.split(";");
        return getProductDtos(products);
    }

    private List<ProductDto> getProductDtos(String[] products) {
        return Arrays.stream(products)
                .map(this::getProductDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private ProductDto getProductDto(String product) {
        Matcher matcher = PRODUCT_PATTERN.matcher(product);
        if (matcher.find()) {
            return getProductDto(matcher);
        }
        return null;
    }

    private ProductDto getProductDto(Matcher matcher) {
        String name = matcher.group(1);
        String price = matcher.group(2);
        String count = matcher.group(3);
        validateNumeric(price);
        validateNumeric(count);
        return new ProductDto(name, Integer.parseInt(price), Integer.parseInt(count));
    }

    public int promptInsertAmount() {
        System.out.println("투입 금액을 입력해 주세요.");
        String input = Console.readLine();
        validateNumeric(input);
        return Integer.parseInt(input);
    }

    private void validateNumeric(String input) {
        if (!NUMERIC_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException("숫자를 입력하셔야 합니다");
        }
    }

    public String promptProductToBuy() {
        System.out.println("구매할 상품명을 입력해 주세요.");
        return Console.readLine();
    }
}
