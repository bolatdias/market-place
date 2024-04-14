package com.example.marketplace.controller;


import com.example.marketplace.model.Cart;
import com.example.marketplace.model.User;
import com.example.marketplace.payload.AddToCartInput;
import com.example.marketplace.payload.PurchasePayload;
import com.example.marketplace.security.CurrentUser;
import com.example.marketplace.security.UserPrincipal;
import com.example.marketplace.service.AuthService;
import com.example.marketplace.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final AuthService authService;


    @QueryMapping
    public List<Cart> cart(
            @CurrentUser UserPrincipal currentUser
    ) {
        return cartService.getCarts(currentUser.getId());
    }


    @MutationMapping
    public Cart addProductToCart(
            @Argument("input") AddToCartInput input,
            @CurrentUser UserPrincipal currentUser
    ) {
        User user = authService.getUser(currentUser);
        return cartService.addProductToCart(user, input);
    }

    @MutationMapping
    public PurchasePayload purchaseCart(
            @CurrentUser UserPrincipal userPrincipal
    ) {
        User user = authService.getUser(userPrincipal);
        return cartService.purchaseCart(user);
    }
}
