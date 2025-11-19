package com.example.MoneyRecorderAIver.DTO;

import java.util.Date;

public class TotalExpendResponseDTO {
    private Date startDate;
    private Date endDate;
    private Double totalExpend;

    // Constructors
    public TotalExpendResponseDTO() {
    }

    public TotalExpendResponseDTO(Date startDate, Date endDate, Double totalExpend) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalExpend = totalExpend;
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

    public Double getTotalExpend() {
        return totalExpend;
    }

    public void setTotalExpend(Double totalExpend) {
        this.totalExpend = totalExpend;
    }
}

