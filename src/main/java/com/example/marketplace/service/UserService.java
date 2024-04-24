package com.example.marketplace.service;

import com.example.marketplace.model.User;
import com.example.marketplace.repository.RoleRepository;
import com.example.marketplace.repository.UserRepository;
import com.example.marketplace.security.UserPrincipal;
import com.example.marketplace.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public Boolean checkUsernameAvailability(String username) {
        return !userRepository.existsByUsername(username);
    }

    public Boolean checkEmailAvailability(String email) {
        return !userRepository.existsByEmail(email);
    }

    public User getCurrentUser(UserPrincipal currentUser) {

        return userRepository.findById(currentUser.getId()).orElseThrow();
    }

    public User getCurrentUser() {
        // Retrieve the Authentication object from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Extract the principal from the Authentication object and cast it to UserPrincipal
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        // Retrieve the user's ID from the UserPrincipal
        Long userId = userPrincipal.getId();

        // Fetch the user entity from the database using the UserRepository
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }
}
