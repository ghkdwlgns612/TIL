package com.example.strategypattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV1 {

    public void logic1() {
        long startTime = System.currentTimeMillis(); //비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    public void logic2() {
        long startTime = System.currentTimeMillis(); //비즈니스 로직 실행
        log.info("비즈니스 로직2 실행");
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
