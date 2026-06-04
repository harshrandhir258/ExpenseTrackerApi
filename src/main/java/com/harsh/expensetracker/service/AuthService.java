package com.harsh.expensetracker.service;

import com.harsh.expensetracker.dto.RegisterRequest;
import com.harsh.expensetracker.entity.User;
import com.harsh.expensetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.harsh.expensetracker.dto.AuthResponse;
import com.harsh.expensetracker.dto.LoginRequest;
import com.harsh.expensetracker.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public ResponseEntity<String> registerUser(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User Registered Successfully");
    }

    public ResponseEntity<AuthResponse> loginUser(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {

            return ResponseEntity.status(
                    HttpStatus.UNAUTHORIZED
            ).body(
                    AuthResponse.builder()
                            .message("Invalid Email or Password")
                            .build()
            );
        }
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(
                            AuthResponse.builder()
                                    .message("Invalid Email or Password")
                                    .build()
                    );
        }
        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(
                AuthResponse.builder()
                        .token(token)
                        .message("Login Successful")
                        .build()
        );
    }

}