package com.ohgiraffers.jenkins_test_app.expense.dto;

import com.ohgiraffers.jenkins_test_app.expense.entity.ExpensePaidBy;
import com.ohgiraffers.jenkins_test_app.expense.entity.ExpenseParticipants;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ExpenseDTO {
    private int tripId;
    private LocalDateTime date;
    private String category;
    private String description;
    private BigDecimal amount;
    private String paymentMethod;

    private List<ExpensePaidByDTO> paidByUsers;
    private List<ExpenseParticipantsDTO> participants;

    public ExpenseDTO() {
    }

    public ExpenseDTO(int tripId, LocalDateTime date, String category, String description, BigDecimal amount, String paymentMethod, List<ExpensePaidByDTO> paidByUsers, List<ExpenseParticipantsDTO> participants) {
        this.tripId = tripId;
        this.date = date;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paidByUsers = paidByUsers;
        this.participants = participants;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<ExpensePaidByDTO> getPaidByUsers() {
        return paidByUsers;
    }

    public void setPaidByUsers(List<ExpensePaidByDTO> paidByUsers) {
        this.paidByUsers = paidByUsers;
    }

    public List<ExpenseParticipantsDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ExpenseParticipantsDTO> participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "ExpenseDTO{" +
                "tripId=" + tripId +
                ", date=" + date +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paidByUsers=" + paidByUsers +
                ", participants=" + participants +
                '}';
    }

}
