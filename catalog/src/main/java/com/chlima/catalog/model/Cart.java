package com.chlima.catalog.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
@Slf4j
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String cartId;
    private String customerId;
    @ElementCollection
    @CollectionTable(name = "cartItem", joinColumns = @JoinColumn(name = "cartId"))
    private List<CartItem> cartItems;

    protected Cart(){
        this.cartItems = new ArrayList<>();
    }

    public static Cart create(String customerId){
        Cart cart = new Cart();
        cart.setCustomerId(customerId);
        return cart;
    }

    public void addItem(CartItem item){
        if (cartItems.stream()
            .filter(storedItem -> storedItem.getProductId().equals(item.getProductId()))
            .toList().isEmpty()
        ) {
            cartItems.add(item);
        } else {
            adjustItemAmount(item.getProductId(), item.getQuantity());
        }
    }

    public void removeItem(CartItem item){
        CartItem storedItem = cartItems.stream()
                .filter(sItem -> sItem.getProductId().equals(item.getProductId()))
                .toList().getFirst();

        if (storedItem.getQuantity() <= item.getQuantity()) {
            cartItems.remove(storedItem);
        } else {
            adjustItemAmount(item.getProductId(), -1 * item.getQuantity());
        }
    }

    private void increaseItemAmount(String productId, Integer amount) {
        cartItems = cartItems.stream()
            .map(storedItem ->
                storedItem.getProductId().equals(productId)
                    ? CartItem.create(storedItem.getProductId(), storedItem.getPrice(),storedItem.getQuantity() + amount)
                    : storedItem)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    private void adjustItemAmount(String productId, Integer amount) {
        cartItems = cartItems.stream()
                .map(storedItem ->
                        storedItem.getProductId().equals(productId)
                                ? CartItem.create(
                                        storedItem.getProductId(),
                                        storedItem.getPrice(),
                                storedItem.getQuantity() + amount)
                                : storedItem)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void updateItem(CartItem cartItem) {
        cartItems = cartItems.stream().map(storedItem ->
                storedItem.getProductId().equals(cartItem.getProductId())
                        ? cartItem : storedItem).toList();
    }

}
