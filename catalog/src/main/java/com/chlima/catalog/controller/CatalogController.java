package com.chlima.catalog.controller;

import com.chlima.catalog.dto.ProductDto;
import com.chlima.catalog.service.ProductCatalog;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

}
