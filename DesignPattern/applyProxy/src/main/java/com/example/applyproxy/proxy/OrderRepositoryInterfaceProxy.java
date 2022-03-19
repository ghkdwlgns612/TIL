package com.example.applyproxy.proxy;

import com.example.applyproxy.interfaces.OrderRepository;
import com.example.applyproxy.utils.LogTrace;
import com.example.applyproxy.utils.TraceStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryInterfaceProxy implements OrderRepository {

    private final OrderRepository target;
    private final LogTrace logTrace;

    @Override
    public void save(String itemId) {
        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderRepository.request()"); //target 호출
            target.save(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
