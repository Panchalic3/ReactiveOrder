package com.example.demo.controller;

import com.example.demo.service.ReactiveOrderService;
import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive/orders")
@Slf4j
public class ReactiveOrderController {

    private ReactiveOrderService reactiveOrderService;

    public ReactiveOrderController(ReactiveOrderService reactiveOrderServiceValue) {
        this.reactiveOrderService = reactiveOrderServiceValue;
    }

    @PostMapping("/post")
    public Mono<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequestValue,
                                          @RequestHeader("Authorization") String header) {
        log.info("in controller");

        Mono<OrderResponse> orderResponseMono =
                reactiveOrderService.placeOrder(orderRequestValue, header);

        return orderResponseMono;
    }

    @GetMapping("/all")
    public Flux<OrderRequest> getAllOrders(@RequestHeader("Authorization") String header) {

        Flux<OrderRequest> orderFlux =
                reactiveOrderService.getAllOrders(header);

        return orderFlux;
    }

}