package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
public class OneController {

    private WebClient webClient;
    private long threadNum = 10000;
    private RestTemplate restTemplate = new RestTemplate();
    private String url = "http://localhost:8081";

    public OneController() {
        this.webClient = WebClient.create(url);
    }

    @GetMapping("/client/thread")
    public void clientController() throws InterruptedException {
        log.info("--------------요청시작----------------");
        long start = System.currentTimeMillis();
        for (int i = 0; i < threadNum; i++) {
            new Thread( () -> {
                    restTemplate.exchange(url + "/client", HttpMethod.GET,null,String.class);
                }
            ).start();
            Thread.sleep(2L);
//            log.info("활성화된 쓰레드갯수 : {}",Thread.activeCount());
        }
        long end = System.currentTimeMillis();
        log.info("RestTemplate성능시간 : {}", end-start);
        log.info("--------------요청마감----------------");
    }

    @GetMapping("/client/web")
    public void webClientController() throws InterruptedException {
        log.info("--------------요청시작----------------");
        long start = System.currentTimeMillis();

        for (int i = 0; i < threadNum; i++) {
            Flux<String> ans = webClient
                    .get()
                    .uri("/client")
                    .retrieve()
                    .bodyToFlux(String.class).onErrorResume(res -> {
                        log.debug(res.getMessage());
                        return null;
                    });
            ans.subscribe(res -> {
                System.out.println("error");
            });
            Thread.sleep(2L);
//            log.info("활성화된 쓰레드갯수 : {}",Thread.activeCount());
        }
        long end = System.currentTimeMillis();
        log.info("WebClient성능시간 : {}", end-start);
        log.info("--------------요청마감----------------");
    }
}
