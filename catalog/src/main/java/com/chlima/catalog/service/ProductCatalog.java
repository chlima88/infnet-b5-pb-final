package com.chlima.catalog.service;

import com.chlima.catalog.dto.CreateProductDto;
import com.chlima.catalog.dto.ProductDto;
import com.chlima.catalog.model.Product;
import com.chlima.catalog.model.Vendor;
import com.chlima.catalog.repository.ProductRepository;
import com.chlima.catalog.repository.VendorRepository;
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
    private final VendorRepository vendorRepository;

    public ProductCatalog(ProductRepository productRepository, VendorRepository vendorRepository){
        this.vendorRepository = vendorRepository;
        this.productRepository = productRepository;
    }

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

    public void addToCart(String productId) {

    }

    public String createProduct(CreateProductDto productDto) {
        Vendor vendor = vendorRepository.findById(productDto.vendorId())
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found"));
        Product product = Product.create(
                productDto.name(),
                productDto.price(),
                productDto.category(),
                vendor
        );
        return productRepository.save(product).getProductId();
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
