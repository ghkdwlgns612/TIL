### 트랜잭션의 전파에 관하여

난 트랜잭션 초보다. 이번을 계기로 중수갈 수 있는 발판을 삼으려한다.
전파레벨에 관하여 정말 정리를 잘 해놓은 블로그를 발견했다. 바로 [여기](http://wonwoo.ml/index.php/post/966) 클릭하면 볼 수 있다.
난 이것을 직접 실습하고 테스트로 검증해보았다. 매일 @Transaction만 써봤지 다른 옵션들은 사용하지 않았다.

전파레벨을 지정해주는 것은 다양한 서비스들이 유기적으로 연결되어져있기 때문이다. 트랜잭션은 서비스 + 서비스 + 서비스...라고 생각한다.
그렇기 때문에 잘못되면 롤백을 해야하는데 어디까지 롤백을 해줘야 잘했다고 할 수 있을까? 그리고 서비스에 몇 가지 제약을 줘서 아무나 못쓰도록 막고싶다.
이러한 상황들에 맞서기 위해서 전파레벨을 지정해줘야한다. 예제는 OrderService(부모) -> PaymentService(자식)으로 구성되어 진행한다..

 
<OrderService>

```
@Service
@Slf4j
public class OrderService {

    @Autowired
    private PaymentService paymentService;

    @Transactional
    public void getOrder1() {
        log.info("order의 트랜잭션 : {}", TransactionSynchronizationManager.getCurrentTransactionName());
        paymentService.getPayment1();
        log.info("주문을 하셨습니다.");
    }

    @Transactional
    public void getOrder2() {
        log.info("order의 트랜잭션 : {}", TransactionSynchronizationManager.getCurrentTransactionName());
        paymentService.getPayment2();
        log.info("주문을 하셨습니다.");
    }

    @Transactional
    public void getOrder3() {
        log.info("order의 트랜잭션 : {}", TransactionSynchronizationManager.getCurrentTransactionName());
        paymentService.getPayment3();
        log.info("주문을 하셨습니다.");
    }

    @Transactional(value = Transactional.TxType.MANDATORY) //자식 트랜잭션만 가능. 단독으로 부를 수 없음.
    public void getOrder4() {
        log.info("order의 트랜잭션 : {}", TransactionSynchronizationManager.getCurrentTransactionName());
        paymentService.getPayment4();
        log.info("주문을 하셨습니다.");
    }

    @Transactional
    public void getOrder5() {
        log.info("order의 트랜잭션 : {}", TransactionSynchronizationManager.getCurrentTransactionName());
        paymentService.getPayment5();
        log.info("주문을 하셨습니다.");
    }
}
```

OrderService는 기본적으로 먼저 실행되어지는 메소드들이다. 4번째만 조금 다르다. 이것은 무조건 부모가 있어야만 부를 수 있는 메소드이다. 그래서 일부러 예외를 발생시키도록 설정하였다.

<PaymentService>

```
@Service
@Slf4j
public class PaymentService {
    @Transactional
    public void getPayment1() {
        log.info("payment의 트랜잭션 : {}", TransactionSynchronizationManager.getCurrentTransactionName());
        log.info("결제를 하셨습니다.");
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW) //새로운 트랜잭션을 생성. 부모 트랜잭션에러시 롤백.
    public void getPayment2() {
        log.info("payment의 트랜잭션 : {}", TransactionSynchronizationManager.getCurrentTransactionName());
        log.info("결제를 하셨습니다.");
    }

    @Transactional(value = Transactional.TxType.NEVER) //이미 있는 트랜잭션이면 에러가 발생.
    public void getPayment3() {
        log.info("payment의 트랜잭션 : {}", TransactionSynchronizationManager.getCurrentTransactionName());
        log.info("결제를 하셨습니다.");
    }

    @Transactional(value = Transactional.TxType.MANDATORY) //자식 트랜잭션만 가능. 단독으로 부를 수 없음.
    public void getPayment4() {
        log.info("payment의 트랜잭션 : {}", TransactionSynchronizationManager.getCurrentTransactionName());
        log.info("결제를 하셨습니다.");
    }

    @Transactional(value = Transactional.TxType.NOT_SUPPORTED) //새로운 트랜잭션 생성. 부모가 잘못되어도 롤백되지 않음.
    public void getPayment5() {
        log.info("payment의 트랜잭션 : {}", TransactionSynchronizationManager.getCurrentTransactionName());
        log.info("결제를 하셨습니다.");
    }
}
```

PaymentService는 다양한 옵션들을 구현해봤다. TransactionSynchronizationManager.getCurrentTransactionName() 이것은 현재 메소드가 속한 트랜잭션을 리턴해준다.
아래의 테스트는 전파레벨을 보고 한 번 어떻게 될지 예측해보고 돌려보는 편이 좋아보인다.

<Test>

```
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
```

예측해봤는가? 설명은 PaymentService코드에 주석으로 달아주었다. 꼭 한 번은 생각해보기를 바란다.
이제 궁금한 건 서버가 모듈로 분리되어있을경우는 어떻게 롤백을 처리해줄까? 정말 궁금하다. 면접 끝나고 한 번 해보겠다.
