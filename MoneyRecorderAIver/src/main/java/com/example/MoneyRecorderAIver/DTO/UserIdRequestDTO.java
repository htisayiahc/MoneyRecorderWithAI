package com.example.MoneyRecorderAIver.DTO;

public class UserIdRequestDTO {
    private Long userId;

    // Constructors
    public UserIdRequestDTO() {
    }

    public UserIdRequestDTO(Long userId) {
        this.userId = userId;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

