package com.ohgiraffers.jenkins_test_app.expense.controller;

import com.ohgiraffers.jenkins_test_app.expense.dto.ExpenseDTO;
import com.ohgiraffers.jenkins_test_app.expense.entity.Expense;
import com.ohgiraffers.jenkins_test_app.expense.service.ExpenseService;
import com.ohgiraffers.jenkins_test_app.expense.service.ExpenseSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseSettlementService settlementService;

    @GetMapping("/grouped-by-days/{tripId}")
    public ResponseEntity<Map<String, Map<String, Object>>> getExpensesGroupedByDays(@PathVariable int tripId) {
        Map<String, Map<String, Object>> groupedExpenses = expenseService.getExpensesGroupedByTripDays(tripId);
        return ResponseEntity.ok(groupedExpenses);
    }

    @PostMapping("/insert")
    public ResponseEntity<Expense> createExpense(@RequestBody ExpenseDTO expenseDTO) {
        Expense expense = new Expense();
        expense.setDate(expenseDTO.getDate());
        expense.setCategory(expenseDTO.getCategory());
        expense.setDescription(expenseDTO.getDescription());
        expense.setAmount(expenseDTO.getAmount());
        expenseService.saveExpense(expense);
        return ResponseEntity.ok(expense);
    }

    @PostMapping("/paid")
    public ResponseEntity<Void> savePaid(@RequestParam int expenseId, @RequestParam int userId, @RequestParam BigDecimal amount) {
        expenseService.savePaidBy(expenseId, userId, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/participant")
    public ResponseEntity<Void> saveParticipant(@RequestParam int expenseId, @RequestParam int userId, @RequestParam BigDecimal amount) {
        expenseService.saveParticipant(expenseId, userId, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/settle/{expenseId}")
    public ResponseEntity<Void> settleExpense(@PathVariable Integer expenseId) {
        settlementService.processSettlement(expenseId);
        return ResponseEntity.ok().build();
    }
}
