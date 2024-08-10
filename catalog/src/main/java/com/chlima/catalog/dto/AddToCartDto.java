package com.chlima.catalog.dto;

public record AddToCartDto(
        String productId,
        Integer quantity
) {
}
