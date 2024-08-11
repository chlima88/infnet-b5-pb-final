package com.chlima.order.controller;

import com.chlima.order.dto.CreateOrderDto;
import com.chlima.order.dto.OrderDto;
import com.chlima.order.service.OrderCatalog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/order/{orderId}/pay")
    public ResponseEntity<OrderDto> payOrder(@PathVariable String orderId) {
        return ResponseEntity.ok().body(orderCatalog.payOrder(orderId));
    }

    @GetMapping("/order/{orderId}/ship")
    public ResponseEntity<OrderDto> shipOrder(@PathVariable String orderId) {
        return ResponseEntity.ok().body(orderCatalog.shipOrder(orderId));
    }
}
