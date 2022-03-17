package com.example.strategypattern.strategy1;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV2 {

    private Strategy strategy;

    public ContextV2(Strategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        long startTime = System.currentTimeMillis(); //비즈니스 로직 실행
        strategy.call(); //위임
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
