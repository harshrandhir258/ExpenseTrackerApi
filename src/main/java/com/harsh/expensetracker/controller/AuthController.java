package com.harsh.expensetracker.controller;

import com.harsh.expensetracker.dto.RegisterRequest;
import com.harsh.expensetracker.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody RegisterRequest request) {

        return authService.registerUser(request);
    }
}