package vendingmachine.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import vendingmachine.domain.Machine;
import vendingmachine.domain.Product;
import vendingmachine.repository.DepositRepository;
import vendingmachine.repository.ProductRepository;

class MachineServiceTest {

	MachineService machineService;
	DepositRepository depositRepository;
	ProductRepository productRepository;
	Machine machine;

	List<String> productList;

	@BeforeEach
	void setUp() {
		depositRepository = new DepositRepository();
		productRepository = new ProductRepository();
		machine = new Machine();
		machineService = new MachineService(depositRepository, productRepository, machine);
		productList = Arrays.asList("[콜라,300,20]", "[사이다,1500,300]");
		machineService.setProducts(productList);
	}

	@Test
	void setDepositsRandomized() {
		// given
		int deposit = 550;
		// when
		machineService.setDepositsRandomized(deposit);
		// then
		assertThat(depositRepository.getDepositTotal()).isEqualTo(deposit);
	}

	@Test
	void setProducts() {
		// given
		// when
		machineService.setProducts(productList);
		// then
		assertThat(productRepository.findByName("콜라")).hasValueSatisfying(p -> {
			assertThat(p.getName()).isEqualTo("콜라");
			assertThat(p.getPrice()).isEqualTo(300);
			assertThat(p.getQuantity()).isEqualTo(20);
		});
	}

	@Test
	void getAffordableList() {
		// given
		machineService.setProducts(productList);
		// when
		machine.setUserMoney(500);
		// then
		assertThat(machineService.getAffordableList()).hasSize(1);
	}

	@Test
	void purchaseProduct() {
		// given
		Product target = productRepository.findByName("콜라").get();
		machine.setUserMoney(target.getPrice() * target.getQuantity());
		// when
		IntStream.range(0, target.getQuantity()).forEach(i ->
			machineService.purchaseProduct(target.getName())
		);
		// then
		assertThat(productRepository.findAll()).hasSize(productList.size() - 1);
	}
}