package com.harsh.expensetracker.service;

import com.harsh.expensetracker.dto.ExpenseRequest;
import com.harsh.expensetracker.entity.Expense;
import com.harsh.expensetracker.entity.User;
import com.harsh.expensetracker.repository.ExpenseRepository;
import com.harsh.expensetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public String createExpense(ExpenseRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Expense expense = Expense.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .category(request.getCategory())
                .date(request.getDate())
                .user(user)
                .build();

        expenseRepository.save(expense);

        return "Expense Added Successfully";
    }
}
