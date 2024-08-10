package com.chlima.order.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        String orderId,
        String customerId,
        LocalDateTime createdAt,
        String status,
        List<OrderItemDto> orderItems
) {}

