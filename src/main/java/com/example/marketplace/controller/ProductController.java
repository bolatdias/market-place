package com.example.marketplace.controller;


import com.example.marketplace.model.Category;
import com.example.marketplace.model.Product;
import com.example.marketplace.service.ProductService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @QueryMapping
    public List<Product> findAllProducts() {
        return productService.findAllProducts();
    }

    @QueryMapping
    public List<Category> findAllCategories() {
        return productService.findAllCategories();
    }
}
