package com.nrcp.expensetracker.service;

import com.nrcp.expensetracker.model.Expense;
import com.nrcp.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    
    private final ExpenseRepository expenseRepository;
    
    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }
    
    // Get all non-deleted expenses
    public List<Expense> getAllExpenses() {
        return expenseRepository.findByIsDeletedFalse();
    }
    
    // Get expense by ID (filters out soft-deleted records)
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id)
            .filter(expense -> !expense.getIsDeleted());
    }
    
    // Get expenses by date range
    public List<Expense> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByDatePurchasedBetweenAndIsDeletedFalse(startDate, endDate);
    }
    
    // Get expenses by category
    public List<Expense> getExpensesByCategory(String category) {
        return expenseRepository.findByCategoryAndIsDeletedFalse(category);
    }
    
    // Create new expense
    @Transactional
    public Expense createExpense(Expense expense) {
        Expense saved = expenseRepository.save(expense);
        // TODO: Add action logging when ActionLog is implemented
        return saved;
    }
    
    // Update expense
    @Transactional
    public Expense updateExpense(Long id, Expense expenseDetails) {
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
        
        // Update allowed fields
        expense.setCategory(expenseDetails.getCategory());
        expense.setPurposeItem(expenseDetails.getPurposeItem());
        expense.setQuantity(expenseDetails.getQuantity());
        expense.setPricePer(expenseDetails.getPricePer());
        expense.setDatePurchased(expenseDetails.getDatePurchased());
        expense.setTotalSpent(expenseDetails.getTotalSpent());
        expense.setCurrencySpent(expenseDetails.getCurrencySpent());
        expense.setExchangeRate(expenseDetails.getExchangeRate());
        expense.setBaseCurrencyAmount(expenseDetails.getBaseCurrencyAmount());
        
        Expense updated = expenseRepository.save(expense);
        // TODO: Add action logging when ActionLog is implemented
        
        return updated;
    }
    
    // Soft delete expense
    @Transactional
    public void deleteExpense(Long id, String userEmail) {
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
        
        // Only delete if not already deleted (prevents redundant operations)
        if (!expense.getIsDeleted()) {
            expense.setIsDeleted(true);
            expenseRepository.save(expense);
            // TODO: Add action logging when ActionLog is implemented
        }
    }
    
    // Get total spent
    public Double getTotalSpent() {
        Double total = expenseRepository.getTotalSpent();
        return total != null ? total : 0.0;
    }
    
    // Get total spent for a specific date
    public Double getTotalSpentByDate(LocalDate date) {
        Double total = expenseRepository.getTotalSpentByDate(date);
        return total != null ? total : 0.0;
    }
    
    // Get total spent between dates
    public Double getTotalSpentBetweenDates(LocalDate startDate, LocalDate endDate) {
        Double total = expenseRepository.getTotalSpentBetweenDates(startDate, endDate);
        return total != null ? total : 0.0;
    }
}