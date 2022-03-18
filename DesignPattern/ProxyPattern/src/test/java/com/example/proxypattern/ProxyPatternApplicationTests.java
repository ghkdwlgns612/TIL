package com.example.proxypattern;

import com.example.proxypattern.proxy1.ProxyPatternClient;
import com.example.proxypattern.proxy1.RealSubject;
import com.example.proxypattern.proxy1.Subject;
import com.example.proxypattern.proxy2.CacheProxy;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProxyPatternApplicationTests {

    @Test
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void cacheProxyTest() {
        Subject realSubject = new RealSubject();
        Subject cacheProxy = new CacheProxy(realSubject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);
        client.execute();
        client.execute();
        client.execute();
    }

}
