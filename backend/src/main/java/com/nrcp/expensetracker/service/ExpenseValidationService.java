package com.nrcp.expensetracker.service;

import com.nrcp.expensetracker.model.Expense;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseValidationService {
    
    // Supported currency codes
    private static final List<String> SUPPORTED_CURRENCIES = List.of(
        "PHP", "USD", "CAD", "EUR", "GBP", "JPY", "AUD"
    );
    
    /**
     * Validate business rules for expense.
     * @param expense The expense to validate.
     * @return List of validation error messages (empty if valid).
     */
    public List<String> validateExpense(Expense expense) {
        List<String> errors = new ArrayList<>();
        
        // Check if currency is supported
        if (expense.getCurrencySpent() != null && 
            !SUPPORTED_CURRENCIES.contains(expense.getCurrencySpent().toUpperCase())) {
            errors.add("Currency '" + expense.getCurrencySpent() + 
                      "' is not supported. Supported: " + 
                      String.join(", ", SUPPORTED_CURRENCIES));
        }
        
        // Validate total spent calculation (Quantity * PricePer)
        if (expense.getQuantity() != null && 
            expense.getPricePer() != null && 
            expense.getTotalSpent() != null) {
            
            BigDecimal expectedTotal = expense.getPricePer()
                .multiply(BigDecimal.valueOf(expense.getQuantity()));
            
            // Allow small rounding differences (tolerance up to 0.01)
            BigDecimal difference = expectedTotal.subtract(expense.getTotalSpent()).abs();
            if (difference.compareTo(BigDecimal.valueOf(0.01)) > 0) { // Reduced tolerance to 0.01
                errors.add("Total spent (" + expense.getTotalSpent() + 
                          ") mismatch: expected " + expectedTotal);
            }
        }
        
        // Validate base currency amount calculation (TotalSpent * ExchangeRate)
        if (expense.getTotalSpent() != null && 
            expense.getExchangeRate() != null && 
            expense.getBaseCurrencyAmount() != null) {
            
            BigDecimal expectedBase = expense.getTotalSpent()
                .multiply(expense.getExchangeRate());
            
            // Allow small rounding differences (tolerance up to 0.01)
            BigDecimal difference = expectedBase.subtract(expense.getBaseCurrencyAmount()).abs();
            if (difference.compareTo(BigDecimal.valueOf(0.01)) > 0) { // Reduced tolerance to 0.01
                errors.add("Base amount (" + expense.getBaseCurrencyAmount() + 
                          ") mismatch: expected " + expectedBase);
            }
        }
        
        // Check: Exchange rate is required if currency is not the base currency (PHP)
        if (expense.getCurrencySpent() != null && 
            !expense.getCurrencySpent().equals("PHP") && 
            expense.getExchangeRate() == null) {
            errors.add("Exchange rate is required for non-PHP currencies");
        }
        
        return errors;
    }
    

    // Checks if a given currency is supported
    public boolean isCurrencySupported(String currency) {
        return currency != null && SUPPORTED_CURRENCIES.contains(currency.toUpperCase());
    }
    
    
    // Returns the list of supported currencies
    public List<String> getSupportedCurrencies() {
        return new ArrayList<>(SUPPORTED_CURRENCIES);
    }
}