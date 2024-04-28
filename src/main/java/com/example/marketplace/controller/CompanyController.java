package com.example.marketplace.controller;


import com.example.marketplace.model.Product;
import com.example.marketplace.model.User;
import com.example.marketplace.payload.CreateProductInput;
import com.example.marketplace.security.CurrentUser;
import com.example.marketplace.security.UserPrincipal;
import com.example.marketplace.service.ProductService;
import com.example.marketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CompanyController {

    private final ProductService productService;
    private final UserService userService;



    @MutationMapping(value = "createProduct")
    @PreAuthorize("hasRole('COMPANY')")
    public Product createProduct(
            @CurrentUser UserPrincipal currentUser,
            @Argument("input") CreateProductInput input
            ) {
        User user=userService.getCurrentUser(currentUser);
        return productService.createProduct(input, user);
    }
}
