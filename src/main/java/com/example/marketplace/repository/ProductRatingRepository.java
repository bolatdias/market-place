package com.example.marketplace.repository;

import com.example.marketplace.model.Product;
import com.example.marketplace.model.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {
}
