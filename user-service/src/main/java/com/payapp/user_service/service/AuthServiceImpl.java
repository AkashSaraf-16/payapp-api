package com.payapp.user_service.service;

import com.payapp.user_service.dto.JwtResponse;
import com.payapp.user_service.dto.LoginRequest;
import com.payapp.user_service.dto.SignupRequest;
import com.payapp.user_service.entity.User;
import com.payapp.user_service.repository.UserRepository;
import com.payapp.user_service.util.JWTUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil){
        this.userRepo = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public boolean signup(SignupRequest request){
        Optional<User> existingUser = userRepo.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return false;
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole("USER");  // Normal users only!
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepo.save(user);
        // Save the new user
        User savedUser = userRepo.save(user);

        return true;
    }

    public JwtResponse login(LoginRequest request) {

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );

        return new JwtResponse(token);
    }
}
