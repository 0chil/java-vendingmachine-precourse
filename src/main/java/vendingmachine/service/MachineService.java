package vendingmachine.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import camp.nextstep.edu.missionutils.Randoms;
import vendingmachine.constant.Coin;
import vendingmachine.domain.Deposit;
import vendingmachine.domain.Product;
import vendingmachine.repository.DepositRepository;
import vendingmachine.repository.ProductRepository;

public class MachineService {

	private final DepositRepository depositRepository;
	private List<Coin> coinList;
	private ProductRepository productRepository;

	public MachineService(DepositRepository depositRepository, ProductRepository productRepository) {
		this.depositRepository = depositRepository;
		this.productRepository = productRepository;
	}

	public void setDepositsRandomized(int deposit) {
		this.coinList = Arrays.asList(Coin.values());
		Map<Coin, Integer> countMap = new HashMap<>();
		while (deposit > 0) {
			Deposit randomizedDeposit = getDepositRandomized(deposit);
			deposit -= randomizedDeposit.getCoin().getAmount() * randomizedDeposit.getCount();
			countMap.put(randomizedDeposit.getCoin(),
				addIfNotNull(countMap.get(randomizedDeposit.getCoin()), randomizedDeposit.getCount()));
		}

		depositRepository.save(
			countMap.keySet().stream().map(coin -> new Deposit(coin, countMap.get(coin))).collect(Collectors.toList()));
	}

	private Integer addIfNotNull(Integer previousCount, Integer count) {
		if (previousCount != null)
			return previousCount + count;
		return count;
	}

	private Deposit getDepositRandomized(int deposit) {
		Coin coinRandomized = coinList.get(Randoms.pickNumberInRange(0, coinList.size() - 1));
		int maxCount = deposit / coinRandomized.getAmount();
		int countRandomized = Randoms.pickNumberInRange(0, maxCount);
		return new Deposit(coinRandomized, countRandomized);
	}

	public void setProducts(List<String> inputList) {
		List<Product> productList = inputList.stream()
			.map(s -> getProduct(trimBrackets(s)))
			.collect(Collectors.toList());
		productRepository.save(productList);
	}

	private String trimBrackets(String s) {
		return s.substring(1, s.length() - 1);
	}

	private Product getProduct(String s) {
		List<String> infoList = Arrays.asList(s.split(",", -1));
		String name = infoList.get(0);
		int price = Integer.parseInt(infoList.get(1));
		int quantity = Integer.parseInt(infoList.get(2));

		return new Product(name, price, quantity);
	}
}
