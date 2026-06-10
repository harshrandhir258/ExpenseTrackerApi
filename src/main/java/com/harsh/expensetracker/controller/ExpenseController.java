package com.harsh.expensetracker.controller;

import com.harsh.expensetracker.dto.ExpenseRequest;
import com.harsh.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public String createExpense(@Valid @RequestBody ExpenseRequest request) {

        return expenseService.createExpense(request);
    }

}