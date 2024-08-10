package com.chlima.catalog.service;

import com.chlima.catalog.dto.ProductDto;
import com.chlima.catalog.model.Product;
import com.chlima.catalog.repository.ProductRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCatalog {
    private final ProductRepository productRepository;
    public ProductCatalog(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    // listProducts - name, price, vendor, rating

    public List<ProductDto> listProducts(){
        return productRepository.findAll().stream().map(this::toDTO).toList();
    }

    public ProductDto getProduct(String productId){
        Product product = productRepository.findById(productId).orElseThrow(EntityNotFoundException::new);
        return new ProductDto(
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                product.getVendor().getVendorId()
        );
    }

    private ProductDto toDTO(Product product){
        return new ProductDto(
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                product.getVendor().getVendorId()
        );
    }
}
