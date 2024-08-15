package com.chlima.catalog.service;

import com.chlima.catalog.client.OrderClient;
import com.chlima.catalog.dto.*;
import com.chlima.catalog.model.Cart;
import com.chlima.catalog.model.CartItem;
import com.chlima.catalog.model.Product;
import com.chlima.catalog.model.Vendor;
import com.chlima.catalog.repository.CartRepository;
import com.chlima.catalog.repository.ProductRepository;
import com.chlima.catalog.repository.VendorRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCatalog {
    private final ProductRepository productRepository;
    private final VendorRepository vendorRepository;
    private final CartRepository cartRepository;
    private final OrderClient orderClient;

    public ProductCatalog(
            ProductRepository productRepository,
            VendorRepository vendorRepository,
            CartRepository cartRepository,
            OrderClient orderClient){
        this.vendorRepository = vendorRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.orderClient = orderClient;
    }

    public List<VendorDto> listVendors(){

        return vendorRepository.findAll().stream()
                .map(this::toVendorDto).toList();
    }

    public VendorDto getVendor(String vendorId){
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new EntityNotFoundException("VendorId '"+ vendorId +"' not found"));
        return new VendorDto(
                vendor.getVendorId(),
                vendor.getName(),
                vendor.getCreatedAt()
        );
    }

    public VendorDto createVendor(CreateVendorDto vendor) {
        Vendor product = Vendor.create(vendor.name());
        return toVendorDto(vendorRepository.save(product));
    }

    public List<ProductDto> listProducts(){
        return productRepository.findAll().stream().map(this::toProductDto).toList();
    }

    public ProductDto getProduct(String productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("ProductId '"+ productId +"' not found"));
        return new ProductDto(
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                product.getVendor().getVendorId()
        );
    }

    public ProductDto createProduct(CreateProductDto productDto) {
        Vendor vendor = vendorRepository.findById(productDto.vendorId())
                .orElseThrow(()-> new EntityNotFoundException("VendorId "+ productDto.vendorId() +" not found"));
        Product product = Product.create(
                productDto.name(),
                productDto.price(),
                productDto.category(),
                vendor
        );
        return toProductDto(productRepository.save(product));
    }

    public CartDto createCart(String customerId) {
        Cart cart = Cart.create(customerId);
        return new CartDto(cartRepository.save(cart).getCartId());
    }

    public void addToCart(String cartId, AddToCartDto cartItemDto) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("CartId "+ cartId + " not found"));
        Product product = productRepository.findById(cartItemDto.productId())
                .orElseThrow(() -> new EntityNotFoundException("ProductId "+ cartItemDto.productId() +" not found"));
        CartItem cartItem = CartItem.create(
                cartItemDto.productId(),
                product.getPrice(),
                cartItemDto.quantity());
        cart.addItem(cartItem);
        cartRepository.save(cart);
    }

    public void removeFromCart(String cartId, AddToCartDto cartItemDto) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("CartId "+ cartId + " not found"));
        Product product = productRepository.findById(cartItemDto.productId())
                .orElseThrow(() -> new EntityNotFoundException("ProductId "+ cartItemDto.productId() +" not found"));
        CartItem cartItem = CartItem.create(
                cartItemDto.productId(),
                product.getPrice(),
                cartItemDto.quantity());
        cart.removeItem(cartItem);
        cartRepository.save(cart);
    }

    public OrderDto placeOrder(String cartId){
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("CartId "+ cartId + " not found"));

        List<CartItemDto> cartItemDtos = cart.getCartItems().stream()
                .map(cartItem -> (CartItemDto)toDto(cartItem, CartItemDto.class)).toList();
        PlaceOrderDto placeOrderDto = new PlaceOrderDto(cart.getCustomerId(), cartItemDtos);

        cartRepository.deleteById(cart.getCartId());
        return orderClient.placeOrder(placeOrderDto);
    }

    private ProductDto toProductDto(Product product){
        return new ProductDto(
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                product.getVendor().getVendorId()
        );
    }

    private VendorDto toVendorDto(Vendor vendor){
        return new VendorDto(
                vendor.getVendorId(),
                vendor.getName(),
                vendor.getCreatedAt()
        );
    }

    private Object toDto(Object ojb, Class<?> className){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.convertValue(ojb, className);
    }

}
