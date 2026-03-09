package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest {

    private String customerId;
    private List<String> items;
    private List<Integer> quantities;
    private String paymentMethod;
    private String status;

    private  String reactiveMsg;
}