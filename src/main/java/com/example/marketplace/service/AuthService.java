package com.example.marketplace.service;


import com.example.marketplace.exception.AppException;
import com.example.marketplace.model.Role;
import com.example.marketplace.model.RoleName;
import com.example.marketplace.model.User;
import com.example.marketplace.payload.CreateUserInput;
import com.example.marketplace.payload.Token;
import com.example.marketplace.repository.RoleRepository;
import com.example.marketplace.repository.UserRepository;
import com.example.marketplace.security.JwtTokenProvider;
import com.example.marketplace.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    UserMapper userMapper;

    public Token authenticateUser(String usernameOrEmail, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usernameOrEmail,
                        password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        return new Token(jwt);
    }

    public User createUser(CreateUserInput input) {
        User user = userMapper.createInputToModel(input);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("UserController Role not set."));

        user.setRoles(Collections.singleton(userRole));
        return userRepository.save(user);
    }

}
