package vendingmachine.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import vendingmachine.constant.Coin;
import vendingmachine.domain.Deposit;

public class DepositRepository {

	private final Map<Coin, Deposit> depositMap;

	public DepositRepository() {
		depositMap = new TreeMap<>();
	}

	public void save(List<Deposit> depositList) {
		depositList.forEach(deposit -> depositMap.put(deposit.getCoin(), deposit));
	}

	public Optional<Deposit> findByCoin(Coin coin) {
		return Optional.ofNullable(depositMap.get(coin));
	}

	public int getDepositTotal() {
		return depositMap.values()
			.stream()
			.mapToInt(deposit -> deposit.getCoin().getAmount() * deposit.getCount()).sum();
	}
}
