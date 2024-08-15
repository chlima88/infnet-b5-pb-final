package com.chlima.delivery.dto;

public record DeliveryDto (
        String deliveryId,
        String delivererId,
        String status
) {
}
