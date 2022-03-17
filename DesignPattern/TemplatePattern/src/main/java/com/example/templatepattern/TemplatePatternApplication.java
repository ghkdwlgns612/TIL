package com.example.templatepattern;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class TemplatePatternApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplatePatternApplication.class, args);
        test3();
    }

    static void test1() {
        TemplateMethod template = new TemplateMethod();
        template.run();
    }

    static void test2() {
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();
        AbstractTemplate template2 = new SubClassLogic2();
        template2.execute();
    }

    static void test3() {
        AbstractTemplate template1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비지니스 로직1 실행");
            }
        };
        AbstractTemplate template2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비지니스 로직2 실행");
            }
        };
        log.info("클래스 이름1 = {}", template1.getClass());
        template1.execute();
        log.info("클래스 이름2 = {}", template2.getClass());
        template2.execute();
    }
}
