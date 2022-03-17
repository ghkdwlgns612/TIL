package com.example.strategypattern.strategy2;

import com.example.strategypattern.strategy1.Strategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV3 {
    public void execute(Strategy strategy) {
        long startTime = System.currentTimeMillis();
        strategy.call();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}

