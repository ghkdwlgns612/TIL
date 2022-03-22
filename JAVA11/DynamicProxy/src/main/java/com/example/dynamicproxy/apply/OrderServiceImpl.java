package com.example.dynamicproxy.apply;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
