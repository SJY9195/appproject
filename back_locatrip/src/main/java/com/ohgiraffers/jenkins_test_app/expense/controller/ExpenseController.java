package com.ohgiraffers.jenkins_test_app.expense.controller;

import com.ohgiraffers.jenkins_test_app.expense.dto.ExpenseDTO;
import com.ohgiraffers.jenkins_test_app.expense.entity.Expense;
import com.ohgiraffers.jenkins_test_app.expense.entity.ExpensePaidBy;
import com.ohgiraffers.jenkins_test_app.expense.entity.ExpenseParticipants;
import com.ohgiraffers.jenkins_test_app.expense.repository.ExpensePaidByRepository;
import com.ohgiraffers.jenkins_test_app.expense.repository.ExpenseParticipantsRepository;
import com.ohgiraffers.jenkins_test_app.expense.repository.ExpenseRepository;
import com.ohgiraffers.jenkins_test_app.expense.service.ExpenseService;
import com.ohgiraffers.jenkins_test_app.expense.service.ExpenseSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseSettlementService settlementService;
    @Autowired
    private ExpensePaidByRepository expensePaidByRepository;
    @Autowired
    private ExpenseParticipantsRepository expenseParticipantsRepository;
    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping("/grouped-by-days/{tripId}")
    public ResponseEntity<Map<String, Map<String, Object>>> getExpensesGroupedByDays(@PathVariable int tripId) {
        Map<String, Map<String, Object>> groupedExpenses = expenseService.getExpensesGroupedByTripDays(tripId);
        return ResponseEntity.ok(groupedExpenses);
    }

    @PostMapping("/insert")
    public ResponseEntity<Void> addExpense(@RequestBody ExpenseDTO expenseDTO) {
        Expense expense = new Expense();
        expense.setDate(expenseDTO.getDate());
        expense.setCategory(expenseDTO.getCategory());
        expense.setDescription(expenseDTO.getDescription());
        expense.setAmount(expenseDTO.getAmount());
        expense.setPaymentMethod(expenseDTO.getPaymentMethod());

        // 결제한 사람과 참여자 세부 정보 설정
        List<Map<String, Object>> paidByDetails = new ArrayList<>();
        if (expenseDTO.getPaidByUsers() != null) {
            expenseDTO.getPaidByUsers().forEach(paidBy -> {
                Map<String, Object> paidByMap = new HashMap<>();
                paidByMap.put("userId", paidBy.getUserId());
                paidByMap.put("amount", paidBy.getAmount());
                paidByDetails.add(paidByMap);
            });
        }

        List<Map<String, Object>> participantDetails = new ArrayList<>();
        if (expenseDTO.getParticipants() != null) {
            expenseDTO.getParticipants().forEach(participant -> {
                Map<String, Object> participantMap = new HashMap<>();
                participantMap.put("userId", participant.getUserId());
                participantMap.put("amount", participant.getAmount());
                participantDetails.add(participantMap);
            });
        }

        // Service 메서드 호출
        expenseService.saveExpenseWithDetails(expense, paidByDetails, participantDetails);

        return ResponseEntity.ok().build();
    }


    @PostMapping("/settle/{expenseId}")
    public ResponseEntity<Void> settleExpense(@PathVariable Integer expenseId) {
        settlementService.processSettlement(expenseId);
        return ResponseEntity.ok().build();
    }
}
