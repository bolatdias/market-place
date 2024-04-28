package com.example.marketplace.repository;

import com.example.marketplace.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"category", "images", "company"})
    List<Product> findAll();


    @EntityGraph(attributePaths = {"category", "images"})
    @Query("SELECT p FROM Product p WHERE " +
            "p.title LIKE CONCAT('%',:query, '%')" +
            "Or p.description LIKE CONCAT('%', :query, '%')" +
            "ORDER BY CASE WHEN p.title LIKE CONCAT('%',:query, '%') THEN 0 ELSE 1 END, p.title, p.description")
    List<Product> searchProducts(String query);

}
