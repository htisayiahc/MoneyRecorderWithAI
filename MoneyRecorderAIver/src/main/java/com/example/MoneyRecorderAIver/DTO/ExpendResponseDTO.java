package com.example.MoneyRecorderAIver.DTO;

import java.util.Date;

public class ExpendResponseDTO {
    private Long expendId;
    private Long userId;
    private String userName;
    private Double amount;
    private String description;
    private Date expendDate;

    // Constructors
    public ExpendResponseDTO() {
    }

    public ExpendResponseDTO(Long expendId, Long userId, String userName, Double amount, String description, Date expendDate) {
        this.expendId = expendId;
        this.userId = userId;
        this.userName = userName;
        this.amount = amount;
        this.description = description;
        this.expendDate = expendDate;
    }

    // Getters and Setters
    public Long getExpendId() {
        return expendId;
    }

    public void setExpendId(Long expendId) {
        this.expendId = expendId;
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

    public Date getExpendDate() {
        return expendDate;
    }

    public void setExpendDate(Date expendDate) {
        this.expendDate = expendDate;
    }
}

