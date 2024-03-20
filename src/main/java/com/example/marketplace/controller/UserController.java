package com.example.marketplace.controller;


import com.example.marketplace.model.User;
import com.example.marketplace.security.CurrentUser;
import com.example.marketplace.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser(
            @CurrentUser User user
    ) {
        return ResponseEntity.
                status(HttpStatus.OK)
                .body(user);
    }
}
