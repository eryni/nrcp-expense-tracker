package com.nrcp.expensetracker.repository;

import com.nrcp.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    // Find all non-deleted expenses
    List<Expense> findByIsDeletedFalse();
    
    // Find expenses by date range
    List<Expense> findByDatePurchasedBetweenAndIsDeletedFalse(LocalDate startDate, LocalDate endDate);
    
    // Find expenses by category
    List<Expense> findByCategoryAndIsDeletedFalse(String category);
    
    // Find expenses by user
    List<Expense> findByLoggedByAndIsDeletedFalse(String loggedBy);
    
    // FIX: Using totalSpent for consistency with service logic
    @Query("SELECT SUM(e.totalSpent) FROM Expense e WHERE e.isDeleted = false")
    Double getTotalSpent();
    
    @Query("SELECT SUM(e.totalSpent) FROM Expense e WHERE e.datePurchased = :date AND e.isDeleted = false")
    Double getTotalSpentByDate(LocalDate date);
    
    @Query("SELECT SUM(e.totalSpent) FROM Expense e WHERE e.datePurchased BETWEEN :startDate AND :endDate AND e.isDeleted = false")
    Double getTotalSpentBetweenDates(LocalDate startDate, LocalDate endDate);
}