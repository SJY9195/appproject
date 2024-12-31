package com.ohgiraffers.jenkins_test_app.expense.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExpenseDTO {
    private int tripId;
    private LocalDateTime date;
    private String category;
    private String description;
    private BigDecimal amount;
    private Long userId;

    public ExpenseDTO() {
    }

    public ExpenseDTO(int tripId, LocalDateTime date, String category, String description, BigDecimal amount, Long userId) {
        this.tripId = tripId;
        this.date = date;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.userId = userId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ExpenseDTO{" +
                "tripId=" + tripId +
                ", date=" + date +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", userId=" + userId +
                '}';
    }

}
