package com.chlima.catalog.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private String cartId;
    private String customerId;
    @ElementCollection
    @CollectionTable(name = "carItem", joinColumns = @JoinColumn(name = "cartItemId"))
    private List<CartItem> cartItems;

    protected Cart(){
        this.cartItems = new ArrayList<>();
    }

    public void addItem(CartItem cartItem){
        cartItems.add(cartItem);
    }

    public void placeOrder() {
        //TODO - Feign client to orderservice
    }

}
