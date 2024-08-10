package com.chlima.catalog.dto;

import java.util.List;

public record PlaceOrderDto (
        String customerId,
        List<CartItemDto> orderItems
) {
}
