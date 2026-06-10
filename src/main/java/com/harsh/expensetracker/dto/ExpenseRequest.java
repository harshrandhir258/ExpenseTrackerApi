package com.harsh.expensetracker.dto;

import com.harsh.expensetracker.entity.Category;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExpenseRequest {

    @NotNull
    private Double amount;

    private String description;

    @NotNull
    private Category category;

    @NotNull
    private LocalDate date;
}