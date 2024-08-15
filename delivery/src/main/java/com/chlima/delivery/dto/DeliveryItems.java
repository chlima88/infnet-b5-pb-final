package com.chlima.delivery.dto;

import java.math.BigDecimal;

public record DeliveryItems(
        String productId,
        BigDecimal price,
        Integer quantity

) {
}
