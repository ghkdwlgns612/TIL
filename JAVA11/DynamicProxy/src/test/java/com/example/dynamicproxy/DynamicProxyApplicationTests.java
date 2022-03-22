package com.example.dynamicproxy;

import com.example.dynamicproxy.handler.TimeInvocationHandler;
import com.example.dynamicproxy.implement.AInterfaceImpl;
import com.example.dynamicproxy.implement.BInterfaceImpl;
import com.example.dynamicproxy.interfaces.AInterface;
import com.example.dynamicproxy.interfaces.BInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Proxy;

@SpringBootTest
@Slf4j
class DynamicProxyApplicationTests {

    @Test
    void dynamicA() {
        AInterface target = new AInterfaceImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);
        AInterface proxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);
        proxy.call();

        log.info("targetClass={}",target.getClass());
        log.info("proxyClass={}",proxy.getClass());
    }

    @Test
    void dynamicB() {
        BInterface target = new BInterfaceImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);
        BInterface proxy = (BInterface) Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[]{BInterface.class}, handler);
        proxy.call();

        log.info("targetClass={}",target.getClass());
        log.info("proxyClass={}",proxy.getClass());
    }


    @Test
    void logTracing() {

    }
}
