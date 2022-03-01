package com.example.TransactionProgation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransactionProgationApplicationTests {

	@Autowired
	private OrderService orderService;

	@Test
	void getTransactionName1() {
		orderService.getOrder1();
	}

	@Test
	void getTransactionName2() {
		orderService.getOrder2();
	}

	@Test
	void getTransactionName3() {
		Assertions.assertThrows(Exception.class, () -> {
			orderService.getOrder3();
		});
	}

	@Test
	void getTransactionName4() {
		Assertions.assertThrows(Exception.class, () -> {
			orderService.getOrder4();
		});
	}

	@Test
	void getTransactionName5() {
		orderService.getOrder5();
	}
}
