package com.example.marketplace.service;


import com.example.marketplace.model.Category;
import com.example.marketplace.model.Product;
import com.example.marketplace.repository.CategoryRepository;
import com.example.marketplace.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> findCategories(List<Long> ids) {
        return categoryRepository.findAllById(ids);
    }
}
