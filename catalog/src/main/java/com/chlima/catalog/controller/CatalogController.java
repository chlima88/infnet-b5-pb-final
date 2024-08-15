package com.chlima.catalog.controller;

import com.chlima.catalog.dto.*;
import com.chlima.catalog.service.ProductCatalog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin( origins = "*")
public class CatalogController {

    private final ProductCatalog productCatalog;
    public CatalogController(ProductCatalog productCatalog){
        this.productCatalog = productCatalog;
    }


    @Operation(summary = "Get vendor by id",
            description = "Returns vendor data associated with the informed id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Product id not found", content = @Content)
    })
    @Tag(name = "Vendor")
    @GetMapping("/vendors/{vendorId}")
    public ResponseEntity<VendorDto> getVendor(
            @PathVariable String vendorId){
        return ResponseEntity.ok().body(productCatalog.getVendor(vendorId));
    }

    @Operation(summary = "Get vendor list",
            description = "Retrieves a list of all vendors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @GetMapping("/vendors")
    @Tag(name = "Vendor")
    public ResponseEntity<List<VendorDto>> listVendors() {
        return ResponseEntity.ok().body(productCatalog.listVendors());
    }

    @Operation(summary = "Create vendor",
            description = "Creates a vendor using the payload data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping("/vendors")
    @Tag(name = "Vendor")
    public ResponseEntity<VendorDto> createVendor(@RequestBody CreateVendorDto vendorDto) {
        return ResponseEntity.ok().body(productCatalog.createVendor(vendorDto));
    }


    @Operation(summary = "Get product by id",
            description = "Returns product data associated with the informed id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Product id not found", content = @Content)
    })
    @GetMapping("/products/{productId}")
    @Tag(name = "Product")
    public ResponseEntity<ProductDto> getProduct(
            @PathVariable String productId){
        return ResponseEntity.ok().body(productCatalog.getProduct(productId));
    }

    @Operation(summary = "Get product list",
            description = "Retrieves a list of all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @GetMapping("/products")
    @Tag(name = "Product")
    public ResponseEntity<List<ProductDto>> listProducts() {
        return ResponseEntity.ok().body(productCatalog.listProducts());
    }

    @Operation(summary = "Create product",
            description = "Creates a product using the payload data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping("/products")
    @Tag(name = "Product")
    public ResponseEntity<ProductDto> createProduct(@RequestBody CreateProductDto productDto) {
        return ResponseEntity.ok().body(productCatalog.createProduct(productDto));
    }

    @Operation(summary = "Create cart",
            description = "Creates a cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping("/carts")
    @Tag(name = "Cart")
    public ResponseEntity<CartDto> createCart(@RequestBody CreateCartDto createCartDto) {
        return ResponseEntity.ok().body(productCatalog.createCart(createCartDto.customerId()));
    }

    @Operation(summary = "Add cart item",
            description = "Adds an item to a previously created cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PostMapping("/carts/{cartId}/items")
    @Tag(name = "Cart")
    public ResponseEntity<Void> addToCart(
            @PathVariable String cartId,
            @RequestBody AddToCartDto cartItemDto
    ) {
        productCatalog.addToCart(cartId, cartItemDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove cart item",
            description = "Removes an item to a previously created cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @DeleteMapping("/carts/{cartId}/items")
    @Tag(name = "Cart")
    public ResponseEntity<Void> removeFromCart(
            @PathVariable String cartId,
            @RequestBody AddToCartDto cartItemDto
    ) {
        productCatalog.removeFromCart(cartId, cartItemDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Place Order",
            description = "Places an order with the cart items associated to the informed cart id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PostMapping("/carts/{cartId}/orders")
    @Tag(name = "Cart")
    public ResponseEntity<OrderDto> placeOrder(@PathVariable String cartId){
        return ResponseEntity.ok().body(productCatalog.placeOrder(cartId));
    }
}
