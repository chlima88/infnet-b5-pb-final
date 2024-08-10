package com.chlima.catalog.repository;

import com.chlima.catalog.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, String> {
}
