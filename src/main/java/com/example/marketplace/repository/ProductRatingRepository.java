package com.example.marketplace.repository;

import com.example.marketplace.model.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {

    @Query("SELECT p.rating FROM ProductRating p WHERE p.user.id = :userId AND p.product.id = :productId")
    public Optional<Integer> getRatingByUserIdAndProductId(Long userId, Long productId);

}
