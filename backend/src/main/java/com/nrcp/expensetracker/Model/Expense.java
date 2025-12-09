package com.nrcp.expensetracker.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "expense")
public class Expense {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Long expenseId; // Primary key
    
    @Column(name = "date_logged", nullable = false)
    private LocalDateTime dateLogged = LocalDateTime.now(); // Timestamp when expense was logged
    
    @Column(name = "logged_by", nullable = false, length = 100)
    private String loggedBy; // User who logged the expense
    
    @Column(name = "category", nullable = false, length = 50)
    private String category;
    
    @Column(name = "purpose_item", nullable = false, length = 255)
    private String purposeItem;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity; // Amount of items purchased
    
    @Column(name = "price_per", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePer;
    
    @Column(name = "date_purchased", nullable = false)
    private LocalDate datePurchased;
    
    @Column(name = "total_spent", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalSpent; // Amount spent in foreign currency
    
    @Column(name = "currency_spent", nullable = false, length = 3)
    private String currencySpent;
    
    @Column(name = "exchange_rate", precision = 10, scale = 4)
    private BigDecimal exchangeRate;
    
    @Column(name = "base_currency_amount", precision = 10, scale = 2)
    private BigDecimal baseCurrencyAmount; // Total converted to base currency
    
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false; // Soft delete flag (DB column: is_deleted)
    
    // Constructors (JPA requirement)
    public Expense() {}
    
    // Getters and Setters

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