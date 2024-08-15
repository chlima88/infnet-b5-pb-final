package com.chlima.order.controller;

import com.chlima.order.dto.CreateOrderDto;
import com.chlima.order.dto.OrderDto;
import com.chlima.order.dto.ShipOrderDto;
import com.chlima.order.service.OrderCatalog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin( origins = "*")
public class OrderController {

    private final OrderCatalog orderCatalog;

    public OrderController(OrderCatalog orderCatalog) {
        this.orderCatalog = orderCatalog;
    }

    @Operation(summary = "Create order",
            description = "Create an order using the payload data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @Tag(name = "Order")
    @PostMapping("/orders")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody CreateOrderDto orderDto) {
        OrderDto order = orderCatalog.createOrder(orderDto);
        return ResponseEntity.ok().body(order);
    }

    @Operation(summary = "Pay order",
            description = "Request pay order action using the orderId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @Tag(name = "Order")
    @GetMapping("/orders/{orderId}/pay")
    public ResponseEntity<OrderDto> payOrder(@PathVariable String orderId) {
        return ResponseEntity.ok().body(orderCatalog.payOrder(orderId));
    }

    @Operation(summary = "Ship order",
            description = "Request ship order action using the orderId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @Tag(name = "Order")
    @PostMapping("/orders/{orderId}/ship")
    public ResponseEntity<OrderDto> shipOrder(@PathVariable String orderId, @RequestBody ShipOrderDto shipOrderDto) {
        return ResponseEntity.ok().body(orderCatalog.shipOrder(orderId, shipOrderDto.deliveryId()));
    }

    @Operation(summary = "Complete order",
            description = "Request complete order action using the orderId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @Tag(name = "Order")
    @GetMapping("/orders/{orderId}/complete")
    public ResponseEntity<OrderDto> completeOrder(@PathVariable String orderId) {
        return ResponseEntity.ok().body(orderCatalog.completeOrder(orderId));
    }

    @Operation(summary = "Get order",
            description = "Request an order by orderId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @Tag(name = "Order")
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDto> getStatus(@PathVariable String orderId) {
        return ResponseEntity.ok().body(orderCatalog.findOrderById(orderId));
    }
}
