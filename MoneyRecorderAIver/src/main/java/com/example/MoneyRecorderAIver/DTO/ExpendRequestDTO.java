package com.example.MoneyRecorderAIver.DTO;

import java.util.Date;

public class ExpendRequestDTO {
    private Long userId;
    private Double amount;
    private String description;
    private Date expendDate;

    // Constructors
    public ExpendRequestDTO() {
    }

    public ExpendRequestDTO(Long userId, Double amount, String description, Date expendDate) {
        this.userId = userId;
        this.amount = amount;
        this.description = description;
        this.expendDate = expendDate;
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

    public Date getExpendDate() {
        return expendDate;
    }

    public void setExpendDate(Date expendDate) {
        this.expendDate = expendDate;
    }
}

