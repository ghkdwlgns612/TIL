package com.example.logtrace;

import com.example.logtrace.trace.logtrace.FieldLogTrace;
import com.example.logtrace.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {
    @Bean
    public LogTrace logTrace() {
        return new FieldLogTrace();
    }
}
