package com.example.MoneyRecorderAIver.DTO;

import java.util.Date;

public class IncomeRequestDTO {
    private Long userId;
    private Double amount;
    private String description;
    private Date incomeDate;

    // Constructors
    public IncomeRequestDTO() {
    }

    public IncomeRequestDTO(Long userId, Double amount, String description, Date incomeDate) {
        this.userId = userId;
        this.amount = amount;
        this.description = description;
        this.incomeDate = incomeDate;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(Date incomeDate) {
        this.incomeDate = incomeDate;
    }
}

