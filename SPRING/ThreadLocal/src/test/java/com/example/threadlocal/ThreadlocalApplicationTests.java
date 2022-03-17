package com.example.threadlocal;

import com.example.threadlocal.field.FieldService;
import com.example.threadlocal.field.ThreadLocalService;
import com.example.threadlocal.log.FieldLogTrace;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ThreadlocalApplicationTests {
    private FieldLogTrace trace = new FieldLogTrace();
    private FieldService fieldService = new FieldService();
    private ThreadLocalService service = new ThreadLocalService();

    @Test
    void begin_end_level2() {
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");
        trace.end(status2);
        trace.end(status1);
    }

    @Test
    void begin_exception_level2() {
        TraceStatus status1 = trace.begin("hello");
        TraceStatus status2 = trace.begin("hello2");
        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
    }

    @Test
    void field() {
        System.out.println("main start");
        Runnable userA = () -> {
            fieldService.logic("userA");
        };
        Runnable userB = () -> {
            fieldService.logic("userB");
        };
        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");
        threadA.start(); //A실행
        sleep(2000); //동시성 문제 발생X
        sleep(100); //동시성 문제 발생O
        threadB.start(); //B실행
        sleep(3000); //메인 쓰레드 종료 대기
        System.out.println("main exit");
    }

    @Test
    void threadLocal() {
        System.out.println("main start");
        Runnable userA = () -> {
            service.logic("userA");
        };
        Runnable userB = () -> {
            service.logic("userB");
        };
        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");
        threadA.start();
        sleep(100);
        threadB.start();

        sleep(2000);
        System.out.println("main exit");
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
