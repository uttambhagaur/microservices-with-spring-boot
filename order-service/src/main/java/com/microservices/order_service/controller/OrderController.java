package com.microservices.order_service.controller;

import com.microservices.order_service.dto.OrderRequest;
import com.microservices.order_service.dto.OrderResponse;
import com.microservices.order_service.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<OrderResponse> addOrder(@RequestBody OrderRequest orderRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.orderService.addOrder(orderRequest));
    }
}
