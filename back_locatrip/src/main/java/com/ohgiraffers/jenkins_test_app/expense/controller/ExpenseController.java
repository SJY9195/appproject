package com.ohgiraffers.jenkins_test_app.expense.controller;

import com.ohgiraffers.jenkins_test_app.expense.dto.ExpenseDTO;
import com.ohgiraffers.jenkins_test_app.expense.entity.Expense;
import com.ohgiraffers.jenkins_test_app.expense.service.ExpenseService;
import com.ohgiraffers.jenkins_test_app.expense.service.ExpenseSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private ExpenseSettlementService settlementService;

    @PostMapping("/insert")
    public ResponseEntity<Expense> createExpense(@RequestBody ExpenseDTO expenseDTO) {
        Expense expense = new Expense();
        expense.setDate(expenseDTO.getDate());
        expense.setCategory(expenseDTO.getDescription());
        expense.setDescription(expenseDTO.getDescription());
        expense.setAmount(expenseDTO.getAmount());
        return ResponseEntity.ok(expenseService.saveExpense(expense));
    }

    @PostMapping("/settle/{expenseId}")
    public ResponseEntity<Void> settleExpense(@PathVariable Integer expenseId) {
        settlementService.processSettlement(expenseId);
        return ResponseEntity.ok().build();
    }
}
