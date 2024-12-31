package com.ohgiraffers.jenkins_test_app.expense.service;

import com.ohgiraffers.jenkins_test_app.expense.entity.Expense;
import com.ohgiraffers.jenkins_test_app.expense.entity.ExpensePaidBy;
import com.ohgiraffers.jenkins_test_app.expense.entity.ExpenseParticipants;
import com.ohgiraffers.jenkins_test_app.expense.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExpenseSettlementService {
    @Autowired
    private ToPaymentRepository toPaymentRepository;
    @Autowired
    private FromPaymentRepository fromPaymentRepository;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private ExpensePaidByRepository expensePaidByRepository;
    @Autowired
    private ExpenseParticipantsRepository expenseParticipantsRepository;

    public void processSettlement(Integer expenseId){
        Expense expense = expenseRepository.findById(expenseId).orElseThrow();

        List<ExpensePaidBy> paidByList = expensePaidByRepository.findByExpenseId(expenseId);
        List<ExpenseParticipants> participantsList = expenseParticipantsRepository.findByExpenseId(expenseId);

        for (ExpenseParticipants participants : participantsList) {
            BigDecimal participantAmount = participants.getAmount();

        }
    }
}
