package com.chlima.delivery.controller;

import com.chlima.delivery.dto.DelivererDto;
import com.chlima.delivery.dto.DelivererRegisterDto;
import com.chlima.delivery.dto.DeliveryDto;
import com.chlima.delivery.dto.RequestDeliveryDto;
import com.chlima.delivery.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin( origins = "*")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Operation(summary = "Stop the deliverer activity by its id",
            description = "Start the deliverer activity by updating its status to available")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "DelivererId not found", content = @Content)
    })
    @Tag(name = "Deliverer")
    @GetMapping("/deliverers/{delivererId}/start")
    public ResponseEntity<Void> delivererDefineAvailable(@PathVariable String delivererId){
        deliveryService.delivererDefineAvailable(delivererId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Stop the deliverer activity by its id",
            description = "Stop the deliverer activity by updating its status to available")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "DelivererId not found", content = @Content)
    })
    @Tag(name = "Deliverer")
    @GetMapping("/deliverers/{delivererId}/stop")
    public ResponseEntity<Void> delivererDefineUnavailable(@PathVariable String delivererId){
        deliveryService.delivererDefineUnavailable(delivererId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Creates a deliverer",
            description = "Creates a deliverer using the informed payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @Tag(name = "Deliverer")
    @PostMapping("/deliverers")
    public ResponseEntity<DelivererDto> delivererRegister(@RequestBody DelivererRegisterDto deliveryDto){
        return ResponseEntity.ok().body(deliveryService.delivererRegister(deliveryDto));
    }

    @Operation(summary = "Creates a delivery",
            description = "Creates a delivery using the informed payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @Tag(name = "Delivery")
    @PostMapping("/deliveries")
    public ResponseEntity<DeliveryDto> requestDelivery(@RequestBody RequestDeliveryDto deliveryDto){
        return ResponseEntity.ok().body(deliveryService.requestDelivery(deliveryDto));
    }

    @Operation(summary = "Complete delivery",
            description = "Completes a delivery by deliveryId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @Tag(name = "Delivery")
    @GetMapping("/deliveries/{deliveryId}/complete")
    public ResponseEntity<DeliveryDto> completeDelivery(@PathVariable String deliveryId){
        return ResponseEntity.ok().body(deliveryService.completeDelivery(deliveryId));
    }
}
