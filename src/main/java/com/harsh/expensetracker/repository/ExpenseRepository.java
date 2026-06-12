package com.harsh.expensetracker.repository;

import com.harsh.expensetracker.entity.Category;
import com.harsh.expensetracker.entity.Expense;
import com.harsh.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUser(User user);

    List<Expense> findByUserAndCategory(
            User user,
            Category category
    );

    @Query("""
            SELECT e
            FROM Expense e
            WHERE e.user = :user
            AND MONTH(e.date) = :month
            """)
    List<Expense> findByUserAndMonth(
            @Param("user") User user,
            @Param("month") int month
    );

    List<Expense> findByUserAndDateBetween(
            User user,
            LocalDate startDate,
            LocalDate endDate
    );

    @Query("""
        SELECT COALESCE(SUM(e.amount), 0)
        FROM Expense e
        WHERE e.user = :user
        """)
    Double getTotalExpensesByUser(
            @Param("user") User user
    );

    @Query("SELECT e.category, SUM(e.amount) " +
            "FROM Expense e " +
            "WHERE e.user = :user " +
            "GROUP BY e.category")
    List<Object[]> getCategoryWiseSummary(
            @Param("user") User user
    );

    @Query("""
       SELECT COALESCE(SUM(e.amount), 0)
       FROM Expense e
       WHERE e.user = :user
       AND MONTH(e.date) = :month
       """)
    Double getCurrentMonthTotal(
            @Param("user") User user,
            @Param("month") int month
    );



}