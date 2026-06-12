package com.harsh.expensetracker.controller;

import com.harsh.expensetracker.dto.ExpenseRequest;
import com.harsh.expensetracker.entity.Category;
import com.harsh.expensetracker.entity.Expense;
import com.harsh.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.harsh.expensetracker.repository.ExpenseRepository;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public String createExpense(@Valid @RequestBody ExpenseRequest request) {

        return expenseService.createExpense(request);
    }

    @GetMapping
    public List<Expense> getAllExpenses() {

        return expenseService.getAllExpenses();
    }

    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id) {
        return expenseService.getExpenseById(id);
    }

    @PutMapping("/{id}")
    public String updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequest request) {

        return expenseService.updateExpense(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id) {

        return expenseService.deleteExpense(id);
    }

    @GetMapping("/category/{category}")
    public List<Expense> getExpensesByCategory(@PathVariable Category category) {

        return expenseService.getExpensesByCategory(category);
    }

    @GetMapping("/month/{month}")
    public List<Expense> getExpensesByMonth(@PathVariable int month) {

        return expenseService.getExpensesByMonth(month);
    }

    @GetMapping("/date-range")
    public List<Expense> getExpensesByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        return expenseService.getExpensesByDateRange(
                startDate,
                endDate
        );
    }

    @GetMapping("/summary/total")
    public Double getTotalExpenses() {

        return expenseService.getTotalExpenses();
    }

    @GetMapping("/summary/category")
    public Map<Category, Double> getCategoryWiseSummary() {

        return expenseService.getCategoryWiseSummary();
    }

    @GetMapping("/summary/current-month")
    public Double getCurrentMonthTotal() {

        return expenseService.getCurrentMonthTotal();
    }

}