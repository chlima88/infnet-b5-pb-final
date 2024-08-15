package com.chlima.order.dto;

public record DeliveryDto (
        String deliveryId,
        String delivererId,
        String status
) {
}
