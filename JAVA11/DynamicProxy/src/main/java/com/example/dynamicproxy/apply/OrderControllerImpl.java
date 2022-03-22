package com.example.dynamicproxy.apply;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderControllerImpl implements OrderController{
    private final OrderService orderService;

    @Override
    @GetMapping("/request")
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }

    @Override
    @GetMapping("/no")
    public String noLog() {
        return "ok";
    }
}
