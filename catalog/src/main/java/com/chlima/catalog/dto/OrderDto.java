package com.chlima.catalog.dto;

import java.time.LocalDateTime;

public record OrderDto(
        String orderId,
       String customerId,
       LocalDateTime createdAt,
       String status) {}
