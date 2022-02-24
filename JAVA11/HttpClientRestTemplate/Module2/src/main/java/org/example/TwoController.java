package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TwoController {

    static int cnt = 0;

    @GetMapping("/rest")
    public void restController() {

    }

    @GetMapping("/client")
    public void clientController() throws InterruptedException {
        Thread.sleep(1000L);
        cnt++;
        log.info("요청 갯수 : {}",cnt);
    }
}
