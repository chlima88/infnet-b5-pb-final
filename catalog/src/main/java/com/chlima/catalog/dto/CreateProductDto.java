package com.chlima.catalog.dto;

import java.math.BigDecimal;

public record CreateProductDto(
        String name,
        BigDecimal price,
        String category,
        String vendorId) {
}
