package com.chlima.catalog.dto;

import com.chlima.catalog.model.Vendor;

import java.math.BigDecimal;

public record ProductDto(
        String productId,
        String name,
        BigDecimal price,
        String category,
        String vendorId) {
}
