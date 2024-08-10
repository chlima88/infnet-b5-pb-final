package com.chlima.order.controller;

import com.chlima.order.dto.CreateOrderDto;
import com.chlima.order.dto.OrderDto;
import com.chlima.order.service.OrderCatalog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class OrderController {

    private final OrderCatalog orderCatalog;

    public OrderController(OrderCatalog orderCatalog) {
        this.orderCatalog = orderCatalog;
    }

    @PostMapping("/place-order")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody CreateOrderDto orderDto) {
        OrderDto order = orderCatalog.createOrder(orderDto);
        return ResponseEntity.ok().body(order);
    }
}
