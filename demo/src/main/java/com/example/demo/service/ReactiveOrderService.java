package com.example.demo.service;

import com.example.demo.client.OrderApiClient;
import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class ReactiveOrderService {

    private OrderApiClient orderApiClient;

    public Mono<OrderResponse> placeOrder(OrderRequest orderRequestValue, String header) {
        log.info("in service");

        Mono<OrderResponse> orderResponseMono =
                orderApiClient.placeOrder(orderRequestValue, header);

        return orderResponseMono;
    }

    public Flux<OrderRequest> getAllOrders(String header) {

        Flux<OrderRequest> orderFlux =
                orderApiClient.getAllOrders(header);

        return orderFlux;
    }
}