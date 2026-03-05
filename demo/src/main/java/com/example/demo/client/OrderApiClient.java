package com.example.demo.client;

import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class OrderApiClient {

    @Autowired
    private WebClient webClient;

    public Mono<OrderResponse> placeOrder(OrderRequest orderRequestValue, String header) {
        System.out.println("Before calling Order API - Thread: " + Thread.currentThread().getName());

        Mono<OrderResponse> orderResponseMono =
                webClient.post()
                        .uri("/placeOrder")
                        .bodyValue(orderRequestValue)
                        .header("Authorization", header)
                        .retrieve()
                        .bodyToMono(OrderResponse.class)
                        .doOnNext(response ->
                                System.out.println("Response received - Thread: " + Thread.currentThread().getName())
                        );


        return orderResponseMono;
    }

    public Flux<OrderRequest> getAllOrders(String header) {

        Flux<OrderRequest> orderFlux =
                webClient.get()
                        .uri("/all")
                        .header("Authorization", header)
                        .retrieve()
                        .bodyToFlux(OrderRequest.class);

        return orderFlux;
    }

}