package com.chlima.delivery.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Setter(AccessLevel.PRIVATE)
@Data
public class Delivery {
    @Id
    private String deliveryId;
    @DBRef(lazy = true)
    private Deliverer deliverer;
    private LocalDateTime createdAt;
    private Status status;
    private String orderId;
    private String customerId;
    private Integer itemsQuantity;

    protected Delivery(){
        createdAt = LocalDateTime.now();
        deliveryId = UUID.randomUUID().toString();
        status = Status.FINDING_DELIVERER;
    }

    public static Delivery create(String customerId, String orderId, Integer itemsQuantity){
        Delivery delivery = new Delivery();
        delivery.orderId = orderId;
        delivery.customerId = customerId;
        delivery.itemsQuantity = itemsQuantity;
        return delivery;
    }

    public void startDelivery(){
        if (deliverer == null) throw new UnsupportedOperationException("No deliverer assigned.");
        this.status = Status.IN_ROUTE;
    }

    public void completeDelivery(){
        this.deliverer.makeAvailable();
        this.status = Status.COMPLETE;
    }

    public void assignDeliverer(Deliverer deliverer){
        this.deliverer = deliverer;
        this.deliverer.makeUnavailable();
    }

    private enum Status {
        FINDING_DELIVERER("FINDING_DELIVERER"),
        IN_ROUTE("IN_ROUTE"),
        COMPLETE("COMPLETE"),
        ;
        private final String name;

        Status(String name) { this.name = name; }

        @Override
        public String toString() {
            return name;
        }
    }

}
