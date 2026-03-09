package com.example.demo.service;

import com.example.demo.client.OrderApiClient;
import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
@Slf4j
public class ReactiveOrderService {

    private OrderApiClient orderApiClient;

    public Mono<OrderResponse> placeOrder(OrderRequest orderRequestValue, String header) {

        Mono<OrderResponse>     orderResponseMono =
                orderApiClient.placeOrder(orderRequestValue, header)
                        .map(response -> {
                            response.setReactiveMsg("This is reactive api msg");
                            return response;
                        });
        return orderResponseMono;
    }

    public Flux<OrderRequest> getAllOrders(String header) {

        Flux<OrderRequest> orderFlux =
                orderApiClient.getAllOrders(header)
                        .parallel()
                        .runOn(Schedulers.parallel())
                        .filter(orderRequest -> orderRequest.getItems().size() >= 1)
                        .doOnNext(res -> {System.out.println("patallel");})
                        .sequential()
        ;

        return orderFlux;
    }
}