package com.chlima.catalog.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Embeddable
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String vendorId;
    private String name;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Product> productList;

    protected Vendor(){
        this.createdAt = LocalDateTime.now();
    }

    public static Vendor create(String name) {
        Vendor vendor = new Vendor();
        vendor.setName(name);
        return vendor;
    }
}
