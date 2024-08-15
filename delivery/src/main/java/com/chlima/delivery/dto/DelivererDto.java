package com.chlima.delivery.dto;

import java.time.LocalDateTime;

public record DelivererDto(
        String delivererId,
        LocalDateTime createdAt,
        String name,
        String phone
) {
}
