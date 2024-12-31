package com.ohgiraffers.jenkins_test_app.expense.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "to_payment")
public class ToPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense;

    private int toUserId;

    public ToPayment() {
    }

    public ToPayment(int id, Expense expense, int toUserId) {
        this.id = id;
        this.expense = expense;
        this.toUserId = toUserId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }
}
