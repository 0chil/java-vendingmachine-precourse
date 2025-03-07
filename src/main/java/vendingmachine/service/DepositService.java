package vendingmachine.service;

import static vendingmachine.constant.Coin.*;
import static vendingmachine.validator.MoneyValidator.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import camp.nextstep.edu.missionutils.Randoms;
import vendingmachine.constant.Coin;
import vendingmachine.domain.Deposit;
import vendingmachine.repository.DepositRepository;

public class DepositService {

	private final DepositRepository depositRepository;

	public DepositService() {
		this(new DepositRepository());
	}

	public DepositService(DepositRepository depositRepository) {
		this.depositRepository = depositRepository;
	}

	public void setDepositsRandomized(String input) {
		validateInteger(input);
		int deposit = Integer.parseInt(input);
		Map<Coin, Integer> countMap = new HashMap<>();
		Arrays.asList(Coin.values()).forEach(coin -> countMap.put(coin, 0));
		while (deposit > 0) {
			int amountRandomized = Randoms.pickNumberInList(Coin.getAmountList());
			int amountToSub = getAmountToSub(deposit, amountRandomized);
			if (Coin.ofAmount(amountToSub).isPresent()) {
				Coin target = Coin.ofAmount(amountToSub).get();
				deposit -= amountToSub;
				countMap.put(target, countMap.get(target) + 1);
			}
		}
		countMap.forEach((coin, integer) -> depositRepository.save(new Deposit(coin, integer)));
	}

	private int getAmountToSub(int deposit, int amountRandomized) {
		if (deposit < amountRandomized)
			return 0;
		return amountRandomized;
	}

	public DepositRepository spit(int money) {
		DepositRepository changes = new DepositRepository();
		spitRecursive(0, money, changes);
		return changes;
	}

	private void spitRecursive(int coinIndex, int moneySum, DepositRepository changes) {
		if (moneySum == 0)
			return;
		if (coinIndex >= Coin.values().length)
			return;
		Coin coin = Coin.values()[coinIndex];
		Deposit deposit = depositRepository.findByCoin(coin).orElse(new Deposit(COIN_10, 0));
		int count = Math.min(moneySum / coin.getAmount(), deposit.getCount());

		moneySum -= coin.getAmount() * count;
		deposit.decreaseBy(count);
		changes.save(new Deposit(coin, count));

		spitRecursive(coinIndex + 1, moneySum, changes);
	}

	public String getDeposits() {
		return depositRepository.toString();
	}
}
