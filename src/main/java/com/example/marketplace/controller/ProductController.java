package com.example.marketplace.controller;


import com.example.marketplace.model.Category;
import com.example.marketplace.model.Product;
import com.example.marketplace.service.ProductService;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @QueryMapping
    public List<Product> products() {
        return productService.findAllProducts();
    }


    @BatchMapping
    List<Category> category(List<Product> products) {
        List<Category> categoryList = products
                .stream()
                .map(p -> p.getCategory())
                .collect(Collectors.toList());

        return categoryList;
    }


    @QueryMapping
    public List<Category> categories() {
        return productService.findAllCategories();
    }
}
