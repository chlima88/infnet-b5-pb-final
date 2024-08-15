package com.chlima.delivery.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Data
@Setter(AccessLevel.PRIVATE)
public class Deliverer {

    @Id
    private String delivererId;
    private LocalDateTime createdAt;
    private String name;
    @Indexed(unique = true)
    private String phone;
    private Status status;

    protected Deliverer(){
        delivererId = UUID.randomUUID().toString();
        createdAt = LocalDateTime.now();
        status = Status.AVAILABLE;
    }

    public static Deliverer create(@NonNull String name, @NonNull String phone){
        Deliverer deliverer = new Deliverer();
        deliverer.setName(name);
        deliverer.setPhone(phone);
        return deliverer;
    }

    public void makeAvailable() {
        status = Status.AVAILABLE;
    }

    public void makeUnavailable() {
        status = Status.UNAVAILABLE;
    }

    private enum Status {
        AVAILABLE,
        UNAVAILABLE
    }
}
