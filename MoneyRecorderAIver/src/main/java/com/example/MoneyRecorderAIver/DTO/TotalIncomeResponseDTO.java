package com.example.MoneyRecorderAIver.DTO;

import java.util.Date;

public class TotalIncomeResponseDTO {
    private Date startDate;
    private Date endDate;
    private Double totalIncome;

    // Constructors
    public TotalIncomeResponseDTO() {
    }

    public TotalIncomeResponseDTO(Date startDate, Date endDate, Double totalIncome) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalIncome = totalIncome;
    }

    // Getters and Setters
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

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }
}

