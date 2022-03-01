package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TwoController {

    static int cnt = 0;

    @GetMapping("/client")
    public synchronized void clientController() throws InterruptedException {
//        Thread.sleep(1L);
        cnt++;
        log.info("요청 처리갯수 : {}",cnt);
    }
}
