package com.chlima.order.client;

import com.chlima.order.dto.DeliveryDto;
import com.chlima.order.dto.RequestDeliveryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("DELIVERY")
public interface DeliveryClient {
    @PostMapping("/deliveries")
    DeliveryDto requestDelivery(@RequestBody RequestDeliveryDto deliveryDto);
}
