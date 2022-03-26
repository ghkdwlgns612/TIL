package com.example.springproxy.proxyfactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcreteProxy {

    public void call() {
        log.info("call 호출");
    }
}
