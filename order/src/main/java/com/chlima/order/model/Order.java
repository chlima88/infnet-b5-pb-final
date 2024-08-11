package com.chlima.order.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Setter(AccessLevel.PRIVATE)
@Entity
@Slf4j
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;
    private LocalDateTime createdAt;
    private String customerId;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ElementCollection
    @CollectionTable(name = "orderItem", joinColumns = @JoinColumn(name = "orderId"))
    private List<OrderItem> orderItems;

    protected Order(){
        this.createdAt = LocalDateTime.now();
        this.status = Status.CREATED;
        this.orderItems = new ArrayList<>();}

    public static Order create(String customerId, List<OrderItem> orderItems) {
        log.info(orderItems.toString());
        validate(orderItems);
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setOrderItems(orderItems);
        return order;
    }

    private static void validate(List<OrderItem> orderItems) {
        if (orderItems.isEmpty()) throw new IllegalArgumentException("Order Items cannot by empty");
    }

    public void pay() {
        this.status = Status.PAID;
    }

    public void cancel()  {
        this.status = Status.CANCELED;
    }

    public void ship()  {
        this.status = Status.SHIPPED;
    }


    public void complete()  {
        this.status = Status.COMPLETED;
    }

    private enum Status {
        CREATED,
        PAID,
        SHIPPED,
        CANCELED,
        COMPLETED,
    }

}
