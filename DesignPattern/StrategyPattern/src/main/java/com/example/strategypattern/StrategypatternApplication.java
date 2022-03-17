package com.example.strategypattern;

import com.example.strategypattern.strategy1.ContextV2;
import com.example.strategypattern.strategy1.StrategyLogic1;
import com.example.strategypattern.strategy1.StrategyLogic2;
import com.example.strategypattern.strategy2.ContextV3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class StrategypatternApplication {


    public static void main(String[] args) {
        SpringApplication.run(StrategypatternApplication.class, args);
        test4();
    }

    static void test1() {
        ContextV1 contextV1 = new ContextV1();
        contextV1.logic1();
        contextV1.logic2();
    }

    static void test2() {
        ContextV2 context1 = new ContextV2(new StrategyLogic1());
        context1.execute();
        ContextV2 context2 = new ContextV2(new StrategyLogic2());
        context2.execute();
    }

    static void test3() {
        ContextV2 context1 = new ContextV2(() -> log.info("비지니스 로직1 수행"));
        ContextV2 context2 = new ContextV2(() -> log.info("비지니스 로직2 수행"));
        context1.execute();
        context2.execute();
    }

    static void test4() {
        ContextV3 context1 = new ContextV3();
        ContextV3 context2 = new ContextV3();
        context1.execute(() -> log.info("비지니스 로직1 수행"));
        context2.execute(() -> log.info("비지니스 로직2 수행"));
    }

}
