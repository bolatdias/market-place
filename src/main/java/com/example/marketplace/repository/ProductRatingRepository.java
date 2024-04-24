package com.example.marketplace.repository;

import com.example.marketplace.model.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {

    @Query("SELECT p.rating, p.product.id FROM ProductRating p WHERE p.user.id = :userId AND p.product.id IN :productIds")
    public List<Object[]> getRatingsByUserIdAndProductIds(@Param("userId") Long userId, @Param("productIds") List<Long> productIds);

}
