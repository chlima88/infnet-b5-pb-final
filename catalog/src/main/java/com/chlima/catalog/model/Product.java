package com.chlima.catalog.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String productId;
    private String name;
    private BigDecimal price;
    private String category;
    @ManyToOne @JoinColumn(name = "vendorId", nullable = false)
    private Vendor vendor;

    protected Product() {}

    public static Product create(String name, BigDecimal price, String category, Vendor vendor){
        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setVendor(vendor);
        product.setPrice(price);
        return product;
    }
}
