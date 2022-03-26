package com.example.springproxy.proxyfactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceInterfaceImpl implements ServiceInterface{
    @Override
    public void save() {
        log.info("저장");
    }

    @Override
    public void find() {
        log.info("찾기");
    }
}
