package com.tbm.task_budget_manager.budget;

import com.tbm.task_budget_manager.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findAll(Specification<Transaction> spec, Pageable pageable);

    @Query("""
           SELECT COALESCE(SUM(t.amount),0) 
           FROM Transaction t 
           WHERE t.user = :user 
             AND t.type = :type
             AND (:from IS NULL OR t.date >= :from)
             AND (:to   IS NULL OR t.date <= :to)
           """)
    BigDecimal sumByTypeAndDate(User user, TransactionType type, LocalDate from, LocalDate to);
}
