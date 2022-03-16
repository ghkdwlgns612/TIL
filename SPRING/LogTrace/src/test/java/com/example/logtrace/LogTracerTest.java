package com.example.logtrace;

import com.example.logtrace.trace.TraceStatus;
import com.example.logtrace.trace.logtrace.FieldLogTrace;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LogTracerTest {
    FieldLogTrace trace = new FieldLogTrace();

    @Test
    void begin_end_level2() {
        //given
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");
        //when
        trace.end(status2);
        trace.end(status1);
        //then
    }

    @Test
    void begin_exception_level2() {
        //given
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");
        //when
        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
        //then
    }
}
