package com.ouichousoft.serviceuser.controller;

import com.ouichousoft.serviceuser.model.User;
import com.ouichousoft.serviceuser.repository.UserRepository;
import com.ouichousoft.serviceuser.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping

@Tag(name = "User Management", description = "API pour gérer les utilisateurs")
public class AccountController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/account")
    @Operation(summary = "creation de compte", description = "creeer un compte pour se connecter à l'applciation")
    public ResponseEntity<?> createAccount(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User created successfully.");
    }

    @PostMapping("/token")
    @Operation(summary = "get token", description = "authentification")
    public ResponseEntity<?> authenticate(@RequestBody User loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if (user.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            String token = jwtService.generateToken(user.get().getEmail());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Invalid credentials.");
    }}
