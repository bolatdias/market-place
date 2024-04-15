package com.example.marketplace.repository;

import com.example.marketplace.model.Cart;
import com.example.marketplace.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c from Cart c WHERE c.user.id=:userId")
    Optional<Cart> findAllByUserId(Long userId);
}
