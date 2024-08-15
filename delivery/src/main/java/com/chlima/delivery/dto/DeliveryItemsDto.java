package com.chlima.delivery.dto;

import java.math.BigDecimal;

public record DeliveryItemsDto(
        String productId,
        BigDecimal price,
        Integer quantity
) {}
