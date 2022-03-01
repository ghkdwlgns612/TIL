package com.example.TransactionProgation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.transaction.Transactional;

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
