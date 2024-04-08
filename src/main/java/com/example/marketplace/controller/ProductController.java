package com.example.marketplace.controller;


import com.example.marketplace.model.Category;
import com.example.marketplace.model.Product;
import com.example.marketplace.model.ProductRating;
import com.example.marketplace.model.User;
import com.example.marketplace.payload.ProductRatingInput;
import com.example.marketplace.payload.SearchProductInput;
import com.example.marketplace.security.CurrentUser;
import com.example.marketplace.security.UserPrincipal;
import com.example.marketplace.service.ProductService;
import com.example.marketplace.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @QueryMapping
    public List<Product> products() {
        return productService.findAllProducts();
    }

    @QueryMapping
    public Product product(@Argument("id") Long id) {
        return productService.findProductById(id);
    }

    @QueryMapping
    public Category category(@Argument("id") Long id) {
        return productService.findCategoryById(id);
    }


    @MutationMapping
    public ProductRating rateProduct(
            @Argument("input") ProductRatingInput input,
            @CurrentUser UserPrincipal currentUser
    ) {
        User user = userService.getCurrentUser(currentUser);
        return productService.rateProduct(user, input);
    }

    @QueryMapping
    public List<Category> categories() {
        return productService.findAllCategories();
    }

    @QueryMapping
    public List<Product> searchProducts(
            @Argument("input") SearchProductInput input) {
        return productService.searchProduct(input);
    }
}
