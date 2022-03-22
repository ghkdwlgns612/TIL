package com.example.dynamicproxy;

import com.example.dynamicproxy.util.LogTrace;
import com.example.dynamicproxy.util.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DynamicProxyBasicConfig.class)
public class DynamicProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicProxyApplication.class, args);
    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }

}
