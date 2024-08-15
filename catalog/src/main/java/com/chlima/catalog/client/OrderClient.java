package com.chlima.catalog.client;

import com.chlima.catalog.dto.OrderDto;
import com.chlima.catalog.dto.PlaceOrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("ORDER")
public interface OrderClient {
    @PostMapping("/orders")
    public OrderDto placeOrder(@RequestBody PlaceOrderDto placeOrderDto);

}
