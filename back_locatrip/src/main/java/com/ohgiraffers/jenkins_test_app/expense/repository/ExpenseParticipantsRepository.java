package com.ohgiraffers.jenkins_test_app.expense.repository;

import com.ohgiraffers.jenkins_test_app.expense.entity.ExpenseParticipants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseParticipantsRepository extends JpaRepository<ExpenseParticipants, Integer> {
}
