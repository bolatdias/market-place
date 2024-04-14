package com.example.marketplace.controller;

import com.example.marketplace.payload.CreateUserInput;
import com.example.marketplace.payload.Token;
import com.example.marketplace.payload.UserPayload;
import com.example.marketplace.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @MutationMapping
    public UserPayload createUser(@Argument("input") CreateUserInput input) {
        return authService.createUser(input);
    }


    @QueryMapping
    public Token getAccessToken(
            @Argument("usernameOrEmail") String usernameOrEmail,
            @Argument("password") String password) {
        return authService.authenticateUser(usernameOrEmail, password);
    }
}
