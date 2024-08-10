package com.chlima.order.dto;

import java.util.List;

public record CreateOrderDto(
        String customerId,
        List<OrderItemDto> orderItems

) {}