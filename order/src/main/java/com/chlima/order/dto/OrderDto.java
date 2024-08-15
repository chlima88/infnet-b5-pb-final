package com.chlima.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        String orderId,
        String customerId,
        LocalDateTime createdAt,
        String status,
        List<OrderItemDto> orderItems,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String deliveryId
) {}

