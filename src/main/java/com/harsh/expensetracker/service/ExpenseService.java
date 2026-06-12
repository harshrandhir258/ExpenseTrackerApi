package com.harsh.expensetracker.service;

import com.harsh.expensetracker.dto.ExpenseRequest;
import com.harsh.expensetracker.entity.Category;
import com.harsh.expensetracker.entity.Expense;
import com.harsh.expensetracker.entity.User;
import com.harsh.expensetracker.repository.ExpenseRepository;
import com.harsh.expensetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Expense> getAllExpenses() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return expenseRepository.findByUser(user);
    }

    public Expense getExpenseById(Long id) {

        return expenseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Expense not found"));
    }

    public String updateExpense(Long id,
                                ExpenseRequest request) {

        Expense expense = expenseRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Expense not found"));

        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());

        expenseRepository.save(expense);

        return "Expense Updated Successfully";
    }

    public String deleteExpense(Long id) {

        Expense expense = expenseRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Expense not found"));

        expenseRepository.delete(expense);

        return "Expense Deleted Successfully";
    }

    public List<Expense> getExpensesByCategory(Category category) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return expenseRepository.findByUserAndCategory(user, category);
    }

    public List<Expense> getExpensesByMonth(int month) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return expenseRepository.findByUserAndMonth(
                user,
                month
        );
    }

    public List<Expense> getExpensesByDateRange(
            LocalDate startDate,
            LocalDate endDate) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return expenseRepository.findByUserAndDateBetween(
                user,
                startDate,
                endDate
        );
    }

    public Double getTotalExpenses() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return expenseRepository
                .getTotalExpensesByUser(user);
    }

    public Map<Category, Double> getCategoryWiseSummary() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<Object[]> results =
                expenseRepository.getCategoryWiseSummary(user);

        Map<Category, Double> summary = new HashMap<>();

        for (Object[] row : results) {

            Category category = (Category) row[0];
            Double total = (Double) row[1];

            summary.put(category, total);
        }

        return summary;
    }

    public Double getCurrentMonthTotal() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        int currentMonth = LocalDate.now().getMonthValue();

        return expenseRepository.getCurrentMonthTotal(
                user,
                currentMonth
        );
    }

}
