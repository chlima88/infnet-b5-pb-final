package com.chlima.customer.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String customerId;
    private LocalDateTime createdAt;
    private String fullname;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Embedded private Contact contact;
    @Embedded private Address address;

    protected Customer() {
        this.createdAt = LocalDateTime.now();
    }

    public static Customer create(
        @NonNull String fullname,
        @NonNull Address address,
        @NonNull Contact contact
    ) {
        Customer customer = new Customer();
        customer.setFullname(fullname);
        customer.setAddress(address);
        customer.setContact(contact);
        return customer;
    }

    public void enable() {
        this.status = Status.ENABLED;
    }
    public void disable() {
        this.status = Status.DISABLED;
    }

    private enum Status {
        ENABLED,
        DISABLED
    }
}
