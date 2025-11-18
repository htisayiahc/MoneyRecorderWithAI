package com.example.MoneyRecorderAIver.DTO;

import java.util.Date;

public class IncomeResponseDTO {
    private Long incomeId;
    private Long userId;
    private String userName;
    private Double amount;
    private String description;
    private Date incomeDate;

    // Constructors
    public IncomeResponseDTO() {
    }

    public IncomeResponseDTO(Long incomeId, Long userId, String userName, Double amount, String description, Date incomeDate) {
        this.incomeId = incomeId;
        this.userId = userId;
        this.userName = userName;
        this.amount = amount;
        this.description = description;
        this.incomeDate = incomeDate;
    }

    // Getters and Setters
    public Long getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Long incomeId) {
        this.incomeId = incomeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

