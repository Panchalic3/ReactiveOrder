package com.example.demo.client;

import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@AllArgsConstructor
public class OrderApiClient {

    @Autowired
    private WebClient webClient;

    public Mono<OrderResponse> placeOrder(OrderRequest orderRequestValue, String header) {
        System.out.println("Before calling Order API - Thread: " + Thread.currentThread().getName());

        Mono<OrderResponse> orderResponseMono = //Creates a reactive pipeline that will emit one OrderResponse.
                webClient.post()//Creates an HTTP POST request using Spring WebClient.
                        .uri("/placeOrder")//Sets the endpoint path that will be called.
                        .bodyValue(orderRequestValue)//Adds the request body to the HTTP request.
                        .header("Authorization", header)//Adds Authorization header to the request.
                        .retrieve()//Sends the HTTP request and prepares to retrieve the response.
                        .bodyToMono(OrderResponse.class)//Converts the HTTP response body into a Mono<OrderResponse>.JSON response → OrderResponse object
                        .retry(3)//Retries the HTTP call up to 3 times if an error occurs.
                        .doOnNext(response -> System.out.println("Response received - Thread: " + Thread.currentThread().getName()))
                        .map(orderResponse -> { orderResponse.setReactiveMsg("heh");
                        return orderResponse;});

        return orderResponseMono;
    }

    public Flux<OrderRequest> getAllOrders(String header) {

        Flux<OrderRequest> orderFlux =
                webClient.get()
                        .uri("/all")
                        .header("Authorization", header)
                        .retrieve()
                        .bodyToFlux(OrderRequest.class)
                        .onBackpressureBuffer()
                        .map(orderRequest -> {
                            orderRequest.setReactiveMsg("this is reactive msg");
                        return orderRequest;
                        })
                ;

        return orderFlux;
    }

}