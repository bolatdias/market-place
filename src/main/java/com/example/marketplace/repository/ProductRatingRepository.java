package com.example.marketplace.repository;

import com.example.marketplace.model.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {
}
