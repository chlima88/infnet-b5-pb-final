package com.chlima.delivery.dto;

public record RequestDeliveryDto(
        String orderId,
        String customerId,
        //List<DeliveryItemsDto> orderItems
        Integer itemsQuantity
) {
}
