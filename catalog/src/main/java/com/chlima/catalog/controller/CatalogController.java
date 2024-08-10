package com.chlima.catalog.controller;

import com.chlima.catalog.dto.*;
import com.chlima.catalog.service.ProductCatalog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CatalogController {

    private final ProductCatalog productCatalog;
    public CatalogController(ProductCatalog productCatalog){
        this.productCatalog = productCatalog;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId){
        return ResponseEntity.ok().body(productCatalog.getProduct(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> listProducts() {
        return ResponseEntity.ok().body(productCatalog.listProducts());
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody CreateProductDto productDto) {
        return ResponseEntity.ok().body(productCatalog.createProduct(productDto));
    }

    @PostMapping("/cart/create")
    public ResponseEntity<String> createCart(@RequestBody CreateCartDto createCartDto) {
        return ResponseEntity.ok().body(productCatalog.createCart(createCartDto.customerId()));
    }

    @PostMapping("/cart/{cartId}/add-item")
    public ResponseEntity<Void> addToCart(
            @PathVariable String cartId,
            @RequestBody AddToCartDto cartItemDto
    ) {
        productCatalog.addToCart(cartId, cartItemDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cart/{cartId}/remove-item")
    public ResponseEntity<Void> removeFromCart(
            @PathVariable String cartId,
            @RequestBody AddToCartDto cartItemDto
    ) {
        productCatalog.removeFromCart(cartId, cartItemDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cart/{cartId}/place-order")
    public ResponseEntity<OrderDto> placeOrder(@PathVariable String cartId){
        return ResponseEntity.ok().body( productCatalog.placeOrder(cartId));
    }

}
