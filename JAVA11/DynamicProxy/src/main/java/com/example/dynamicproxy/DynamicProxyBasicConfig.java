package com.example.dynamicproxy;

import com.example.dynamicproxy.apply.*;
import com.example.dynamicproxy.handler.LogTraceBasicHandler;
import com.example.dynamicproxy.handler.TimeInvocationHandler;
import com.example.dynamicproxy.util.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyBasicConfig {

    @Bean
    public OrderController orderController(LogTrace logTrace) {
        OrderController orderController = new OrderControllerImpl(orderService(logTrace));
        OrderController proxy = (OrderController) Proxy.newProxyInstance(OrderController.class.getClassLoader(),
                new Class[]{OrderController.class},
                new LogTraceBasicHandler(orderController, logTrace));
        return proxy;
    }

    @Bean
    public OrderService orderService(LogTrace logTrace) {
        OrderService orderService = new OrderServiceImpl(orderRepository(logTrace));
        OrderService proxy = (OrderService) Proxy.newProxyInstance(OrderService.class.getClassLoader(),
                new Class[]{OrderService.class},
                new LogTraceBasicHandler(orderService, logTrace));
        return proxy;
    }

    @Bean
    public OrderRepository orderRepository(LogTrace logTrace) {
        OrderRepository orderRepository = new OrderRepositoryImpl();
        OrderRepository proxy = (OrderRepository) Proxy.newProxyInstance(OrderRepository.class.getClassLoader(),
                new Class[]{OrderRepository.class},
                new LogTraceBasicHandler(orderRepository, logTrace));
        return proxy;
    }
}
