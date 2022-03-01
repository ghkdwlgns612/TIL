package com.example.TransactionProgation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.transaction.Transactional;

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
