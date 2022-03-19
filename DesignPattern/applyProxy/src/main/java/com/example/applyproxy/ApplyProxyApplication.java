package com.example.applyproxy;

import com.example.applyproxy.utils.LogTrace;
import com.example.applyproxy.utils.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(InterfaceProxyConfig.class)
public class ApplyProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApplyProxyApplication.class, args);
    }


    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
