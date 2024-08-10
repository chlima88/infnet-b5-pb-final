package com.chlima.catalog.model.Base;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.jpa.domain.AbstractPersistable;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity extends AbstractPersistable<UUID> {

    @Id
    @GeneratedValue
    private UUID id;

}
