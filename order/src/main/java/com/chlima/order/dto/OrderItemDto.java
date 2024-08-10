package com.chlima.order.dto;

import java.math.BigDecimal;

public record OrderItemDto(
        String productId,
        BigDecimal price,
        Integer quantity
) {}
