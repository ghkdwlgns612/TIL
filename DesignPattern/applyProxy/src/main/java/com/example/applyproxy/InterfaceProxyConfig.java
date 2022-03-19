package com.example.applyproxy;

import com.example.applyproxy.implement.OrderControllerImpl;
import com.example.applyproxy.implement.OrderRepositoryImpl;
import com.example.applyproxy.implement.OrderServiceImpl;
import com.example.applyproxy.interfaces.OrderController;
import com.example.applyproxy.interfaces.OrderRepository;
import com.example.applyproxy.interfaces.OrderService;
import com.example.applyproxy.proxy.OrderControllerInterfaceProxy;
import com.example.applyproxy.proxy.OrderRepositoryInterfaceProxy;
import com.example.applyproxy.proxy.OrderServiceInterfaceProxy;
import com.example.applyproxy.utils.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceProxyConfig {

    @Bean
    public OrderController orderController(LogTrace logTrace) {
        OrderControllerImpl controllerImpl = new OrderControllerImpl(orderService(logTrace));
        return new OrderControllerInterfaceProxy(controllerImpl,logTrace);
    }

    @Bean
    public OrderService orderService(LogTrace logTrace) {
        OrderServiceImpl serviceImpl = new OrderServiceImpl(orderRepository(logTrace));
        return new OrderServiceInterfaceProxy(serviceImpl, logTrace);
    }

    @Bean
    public OrderRepository orderRepository(LogTrace logTrace) {
        OrderRepositoryImpl repositoryImpl = new OrderRepositoryImpl();
        return new OrderRepositoryInterfaceProxy(repositoryImpl, logTrace);
    }

}
