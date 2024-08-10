package com.chlima.order.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Embeddable
public class OrderItem {
    private String productId;
    private BigDecimal price;
    private Integer quantity;

    protected OrderItem(){}

    public static OrderItem create(String productId, BigDecimal price, Integer quantity) {
        validate(price, quantity);
        OrderItem orderItem =  new OrderItem();
        orderItem.setProductId(productId);
        orderItem.setPrice(price);
        orderItem.setQuantity(quantity);
        return orderItem;
    }

    private static void validate(BigDecimal price, Integer quantity){
        if (price.compareTo(BigDecimal.ZERO) <= 0 ) throw new IllegalArgumentException("Invalid price");
        if (quantity.compareTo(0) <= 0) throw new IllegalArgumentException("Invalid quantity");
    }
}
