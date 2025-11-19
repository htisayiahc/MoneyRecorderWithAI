package com.example.MoneyRecorderAIver.DTO;

import java.util.Date;

public class TotalIncomeRequestDTO {
    private Long userId;
    private Date startDate;
    private Date endDate;

    // Constructors
    public TotalIncomeRequestDTO() {
    }

    public TotalIncomeRequestDTO(Long userId, Date startDate, Date endDate) {
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

