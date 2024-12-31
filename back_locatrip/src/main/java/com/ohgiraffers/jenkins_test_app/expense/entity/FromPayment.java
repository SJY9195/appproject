package com.ohgiraffers.jenkins_test_app.expense.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "from_payment")
public class FromPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense;

    private int fromUserId;

    public FromPayment() {
    }

    public FromPayment(int id, Expense expense, int fromUserId) {
        this.id = id;
        this.expense = expense;
        this.fromUserId = fromUserId;
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

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

}
