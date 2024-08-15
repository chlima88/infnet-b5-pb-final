package com.chlima.order.dto;

public record RequestDeliveryDto(
        String orderId,
        String customerId,
        Integer itemsQuantity
) {
}
