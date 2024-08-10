package com.chlima.catalog.dto;

import java.math.BigDecimal;

public record CartItemDto(
        String productId,
        BigDecimal price,
        Integer quantity
) {
}
