package com.example.marketplace.controller;

import com.example.marketplace.model.User;
import com.example.marketplace.security.CurrentUser;
import com.example.marketplace.security.UserPrincipal;
import com.example.marketplace.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;


@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @QueryMapping
    boolean checkUsernameAvailability(
            @Argument("username") String username) {
        return userService.checkUsernameAvailability(username);
    }

    @QueryMapping
    boolean checkEmailAvailability(
            @Argument("email") String email) {
        return userService.checkEmailAvailability(email);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    User getCurrentUser(
            @CurrentUser UserPrincipal currentUser
    ) {
        return userService.getCurrentUser(currentUser);
    }


}
