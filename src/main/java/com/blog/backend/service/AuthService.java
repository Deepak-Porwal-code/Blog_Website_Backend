package com.blog.backend.service;

import com.blog.backend.dto.LoginRequest;
import com.blog.backend.dto.SignupRequest;
import com.blog.backend.dto.UserResponse;
import com.blog.backend.entity.User;
import com.blog.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    public UserResponse login(LoginRequest loginRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Get user details after successful authentication
            Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
            if (userOptional.isEmpty()) {
                throw new Exception("User not found with email: " + loginRequest.getEmail());
            }
            
            User user = userOptional.get();
            return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
        } catch (AuthenticationException e) {
            throw new Exception("Invalid email or password");
        }
    }
    
    public UserResponse signup(SignupRequest signupRequest) throws Exception {
        // Check if user already exists by email
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new Exception("User already exists with email: " + signupRequest.getEmail());
        }
        
        // Check if username is already taken
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new Exception("Username is already taken: " + signupRequest.getUsername());
        }
        
        // Create new user
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword())); // Encode password
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        User savedUser = userRepository.save(user);
        
        return new UserResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }
    
    public void logout() {
        // Clear the security context
        SecurityContextHolder.clearContext();
    }
}