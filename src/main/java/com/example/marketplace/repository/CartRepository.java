package com.example.marketplace.repository;

import com.example.marketplace.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {


    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.product.id = :productId")
    Optional<Cart> findAllByUserIdAndProductId(Long userId, Long productId);

    @Query("SELECT c from Cart c WHERE c.user.id=:userId")
    List<Cart> findAllByUserId(Long userId);
}
