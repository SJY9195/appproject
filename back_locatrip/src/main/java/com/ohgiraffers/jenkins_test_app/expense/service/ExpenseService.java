package com.ohgiraffers.jenkins_test_app.expense.service;

import com.ohgiraffers.jenkins_test_app.expense.entity.Expense;
import com.ohgiraffers.jenkins_test_app.expense.entity.ExpensePaidBy;
import com.ohgiraffers.jenkins_test_app.expense.entity.ExpenseParticipants;
import com.ohgiraffers.jenkins_test_app.expense.repository.ExpensePaidByRepository;
import com.ohgiraffers.jenkins_test_app.expense.repository.ExpenseParticipantsRepository;
import com.ohgiraffers.jenkins_test_app.expense.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private ExpensePaidByRepository paidByRepository;
    @Autowired
    private ExpenseParticipantsRepository participantsRepository;

    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public void savePaidBy(int expenseId, int userId, BigDecimal amount) {
        ExpensePaidBy paidBy = new ExpensePaidBy();
        paidBy.setExpense(expenseRepository.findById(expenseId).orElseThrow());
        paidBy.setUserId(userId);
        paidBy.setAmount(amount);
        paidByRepository.save(paidBy);
    }

    public void saveParticipant(int expenseId, int userId, BigDecimal amount) {
        ExpenseParticipants participant = new ExpenseParticipants();
        participant.setExpense(expenseRepository.findById(expenseId).orElseThrow());
        participant.setUserId(userId);
        participant.setAmount(amount);
        participantsRepository.save(participant);
    }
}
