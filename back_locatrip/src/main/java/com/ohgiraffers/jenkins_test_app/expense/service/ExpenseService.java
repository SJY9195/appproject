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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpensePaidByRepository paidByRepository;

    @Autowired
    private ExpenseParticipantsRepository participantsRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd/E", Locale.KOREAN);

    private String formatDate (LocalDate date) {
        return date.format(formatter);
    }

    public Map<String, Map<String, Object>> getExpensesGroupedByTripDays(int tripId) {
        List<Object[]> results = expenseRepository.findExpensesGroupedByTripDays(tripId);

        Map<String, Map<String, Object>> groupedExpenses = new LinkedHashMap<>();

        // 여행 준비 기본 섹션
        Map<String, Object> preparationExpenses = new HashMap<>();
        preparationExpenses.put("date", "여행 준비");
        preparationExpenses.put("expenses", new ArrayList<Map<String, Object>>());
        groupedExpenses.put("preparation", preparationExpenses);

        for (Object[] row : results) {
            int dayNumber = ((Number) row[0]).intValue();
            LocalDate date = ((java.sql.Date) row[1]).toLocalDate();
            String formattedDate = formatDate(date);

            String category = (String) row[2];
            double amount = ((Number) row[3]).doubleValue();
            String description = (String) row[4];

            String dayKey = "day" + dayNumber;
            groupedExpenses.putIfAbsent(dayKey, new LinkedHashMap<>());
            Map<String, Object> dayExpenses = groupedExpenses.get(dayKey);

            dayExpenses.putIfAbsent("date", formattedDate);
            List<Map<String, Object>> expenses = (List<Map<String, Object>>) dayExpenses.getOrDefault("expenses", new ArrayList<>());

            Map<String, Object> expense = new HashMap<>();
            expense.put("category", category);
            expense.put("amount", amount);
            expense.put("description", description);

            expenses.add(expense);
            dayExpenses.put("expenses", expenses);
        }

        return groupedExpenses;
    }

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
