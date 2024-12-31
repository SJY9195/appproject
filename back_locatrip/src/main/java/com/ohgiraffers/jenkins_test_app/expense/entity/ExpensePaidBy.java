package com.ohgiraffers.jenkins_test_app.expense.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "expense_paid_by")
public class ExpensePaidBy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense;

    private int userId;

    private BigDecimal amount;

    public ExpensePaidBy() {
    }

    public ExpensePaidBy(Long id, Expense expense, int userId, BigDecimal amount) {
        this.id = id;
        this.expense = expense;
        this.userId = userId;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
