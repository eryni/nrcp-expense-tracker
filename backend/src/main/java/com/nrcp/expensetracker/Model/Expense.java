package com.nrcp.expensetracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*; // Imports for validation annotations i.e. validate the data 
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity // Marks class as JPA entity
@Table(name = "expense") // Maps to 'expense' database table
public class Expense {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Long expenseId; // Primary key
    
    @Column(name = "date_logged", nullable = false)
    private LocalDateTime dateLogged = LocalDateTime.now(); // Record creation timestamp
    
    @NotBlank(message = "Logged by email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Column(name = "logged_by", nullable = false, length = 100)
    private String loggedBy; // User who logged the expense
    
    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category must not exceed 50 characters")
    @Column(name = "category", nullable = false, length = 50)
    private String category;
    
    @NotBlank(message = "Purpose/Item description is required")
    @Size(max = 255, message = "Purpose/Item must not exceed 255 characters")
    @Column(name = "purpose_item", nullable = false, length = 255)
    private String purposeItem;
    
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @NotNull(message = "Price per unit is required")
    @DecimalMin(value = "0.01", message = "Price per unit must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price per unit must have at most 10 digits and 2 decimal places")
    @Column(name = "price_per", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePer;
    
    @NotNull(message = "Purchase date is required")
    @PastOrPresent(message = "Purchase date cannot be in the future")
    @Column(name = "date_purchased", nullable = false)
    private LocalDate datePurchased;
    
    @NotNull(message = "Total spent is required")
    @DecimalMin(value = "0.01", message = "Total spent must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Total spent must have at most 10 digits and 2 decimal places")
    @Column(name = "total_spent", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalSpent; // Total spent in foreign currency
    
    @NotBlank(message = "Currency code is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency code must be a 3-letter ISO code (e.g., PHP, USD, CAD)")
    @Column(name = "currency_spent", nullable = false, length = 3)
    private String currencySpent;
    
    @DecimalMin(value = "0.0001", message = "Exchange rate must be greater than 0")
    @Digits(integer = 10, fraction = 4, message = "Exchange rate must have at most 10 digits and 4 decimal places")
    @Column(name = "exchange_rate", precision = 10, scale = 4)
    private BigDecimal exchangeRate;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Base currency amount must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Base currency amount must have at most 10 digits and 2 decimal places")
    @Column(name = "base_currency_amount", precision = 10, scale = 2)
    private BigDecimal baseCurrencyAmount; // Total converted to base currency
    
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false; // Soft delete flag
    
    // Constructors (JPA requirement)
    public Expense() {}
    
    // Getters and Setters (omitted for brevity)

    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public LocalDateTime getDateLogged() {
        return dateLogged;
    }

    public void setDateLogged(LocalDateTime dateLogged) {
        this.dateLogged = dateLogged;
    }

    public String getLoggedBy() {
        return loggedBy;
    }

    public void setLoggedBy(String loggedBy) {
        this.loggedBy = loggedBy;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPurposeItem() {
        return purposeItem;
    }

    public void setPurposeItem(String purposeItem) {
        this.purposeItem = purposeItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePer() {
        return pricePer;
    }

    public void setPricePer(BigDecimal pricePer) {
        this.pricePer = pricePer;
    }

    public LocalDate getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(LocalDate datePurchased) {
        this.datePurchased = datePurchased;
    }

    public BigDecimal getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(BigDecimal totalSpent) {
        this.totalSpent = totalSpent;
    }

    public String getCurrencySpent() {
        return currencySpent;
    }

    public void setCurrencySpent(String currencySpent) {
        this.currencySpent = currencySpent;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getBaseCurrencyAmount() {
        return baseCurrencyAmount;
    }

    public void setBaseCurrencyAmount(BigDecimal baseCurrencyAmount) {
        this.baseCurrencyAmount = baseCurrencyAmount;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}