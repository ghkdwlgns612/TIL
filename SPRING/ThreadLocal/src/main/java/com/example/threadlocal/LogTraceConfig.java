package com.example.threadlocal;

import com.example.threadlocal.log.FieldLogTrace;
import com.example.threadlocal.log.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new FieldLogTrace();
    }

}
