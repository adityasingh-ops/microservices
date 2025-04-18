package com.aditya.orderservice.controllers;

import com.aditya.orderservice.dto.OrderRequest;
import com.aditya.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
         orderService.placeOrder(orderRequest);
         return "Order placed";
    }
}
