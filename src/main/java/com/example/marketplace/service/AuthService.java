package com.example.marketplace.service;


import com.example.marketplace.exception.AppException;
import com.example.marketplace.model.Company;
import com.example.marketplace.model.Role;
import com.example.marketplace.model.RoleName;
import com.example.marketplace.model.User;
import com.example.marketplace.payload.CreateCompanyInput;
import com.example.marketplace.payload.CreateUserInput;
import com.example.marketplace.payload.Token;
import com.example.marketplace.payload.UserPayload;
import com.example.marketplace.repository.CompanyRepository;
import com.example.marketplace.repository.RoleRepository;
import com.example.marketplace.repository.UserRepository;
import com.example.marketplace.security.JwtTokenProvider;
import com.example.marketplace.util.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final UserMapper userMapper;
    private final CompanyRepository companyRepository;

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

    public UserPayload createUser(CreateUserInput input) {
        User user = userMapper.createInputToModel(input);
        insertUser(user, RoleName.ROLE_USER);

        return new UserPayload(user, "success");
    }

    private void insertUser(User user, RoleName role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(role)
                .orElseThrow(() -> new AppException("UserController Role not set."));

        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);
    }


    @Transactional
    public UserPayload createCompany(CreateCompanyInput input) {
        User user = userMapper.createInputToUser(input);
        insertUser(user, RoleName.ROLE_COMPANY);

        Company company = userMapper.createInputToModel(input);
        company.setUser(user);
        companyRepository.save(company);
        return new UserPayload(user, "success");
    }

}
