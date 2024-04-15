package com.example.marketplace.repository;

import com.example.marketplace.model.Cart;
import com.example.marketplace.model.CartProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartProductRepository extends CrudRepository<CartProduct, Long> {
    @Query("SELECT c from CartProduct c WHERE c.cart.user.id=:userId")
    Optional<CartProduct> findAllByUserId(Long userId);

    @Query("SELECT c FROM CartProduct c WHERE c.cart.user.id = :userId AND c.product.id = :productId")
    Optional<CartProduct> findAllByUserIdAndProductId(Long userId, Long productId);
}
