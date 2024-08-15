package com.chlima.delivery.client;

import com.chlima.delivery.dto.ShipOrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("ORDER")
public interface OrderClient {
    @PostMapping("/orders/{orderId}/ship")
    void shipOrder(@PathVariable String orderId, @RequestBody ShipOrderDto shipOrderDto);

    @GetMapping("/orders/{orderId}/complete")
    void completeOrder(@PathVariable String orderId);
}