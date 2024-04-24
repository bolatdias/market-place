package com.example.marketplace.controller;

import com.example.marketplace.model.Cart;
import com.example.marketplace.model.Order;
import com.example.marketplace.model.User;
import com.example.marketplace.payload.ApiResponse;
import com.example.marketplace.payload.CreateOrderInput;
import com.example.marketplace.security.CurrentUser;
import com.example.marketplace.security.UserPrincipal;
import com.example.marketplace.service.CartService;
import com.example.marketplace.service.OrderService;
import com.example.marketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;

    @MutationMapping
    public Order createOrder(
            @CurrentUser UserPrincipal userPrincipal,
            @Argument("input") CreateOrderInput input
    ) {
        User user = userService.getCurrentUser(userPrincipal);
        return orderService.createOrder(user, input);
    }
}
