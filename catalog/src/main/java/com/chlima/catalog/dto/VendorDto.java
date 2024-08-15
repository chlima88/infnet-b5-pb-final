package com.chlima.catalog.dto;

import java.time.LocalDateTime;

public record VendorDto (
        String vendorId,
        String name,
        LocalDateTime createdAt
){
}
