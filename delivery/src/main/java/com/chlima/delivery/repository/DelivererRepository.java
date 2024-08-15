package com.chlima.delivery.repository;

import com.chlima.delivery.model.Deliverer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DelivererRepository extends MongoRepository<Deliverer, String> {
    @Query("{ status: 'AVAILABLE' }")
    Optional<Deliverer> findFirstAvailable();
}
