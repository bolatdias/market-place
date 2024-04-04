package com.example.marketplace.repository;

import com.example.marketplace.model.Category;
import com.example.marketplace.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @EntityGraph("Image.product")
    List<Category> findAll();
}
