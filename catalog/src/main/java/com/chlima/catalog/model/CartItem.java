package com.chlima.catalog.model;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Embeddable
@Setter(AccessLevel.PRIVATE)
@Getter
@ToString
public class CartItem {
    private String productId;
    private BigDecimal price;
    private Integer quantity;

    protected CartItem(){}

    public static CartItem create(String productId, BigDecimal price, Integer quantity) {
        validate(price, quantity);
        CartItem cartItem =  new CartItem();
        cartItem.setProductId(productId);
        cartItem.setPrice(price);
        cartItem.setQuantity(quantity);
        return cartItem;
    }

    private static void validate(BigDecimal price, Integer quantity){
        if (price.compareTo(BigDecimal.ZERO) <= 0 ) throw new IllegalArgumentException("Invalid price");
        if (quantity.compareTo(0) <= 0) throw new IllegalArgumentException("Invalid quantity");
    }
}
