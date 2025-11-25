package com.tbm.task_budget_manager.budget;

import com.tbm.task_budget_manager.user.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TransactionSpecifications {

    public static Specification<Transaction> byUser(User user) {
        return (root, q, cb) -> cb.equal(root.get("user"), user);
    }

    public static Specification<Transaction> dateGte(LocalDate from) {
        return (root, q, cb) -> cb.greaterThanOrEqualTo(root.get("date"), from);
    }

    public static Specification<Transaction> dateLte(LocalDate to) {
        return (root, q, cb) -> cb.lessThanOrEqualTo(root.get("date"), to);
    }

    public static Specification<Transaction> byUserAndDate(User user, LocalDate from, LocalDate to) {
        Specification<Transaction> spec = byUser(user);
        if (from != null) spec = spec.and(dateGte(from));
        if (to != null) spec = spec.and(dateLte(to));
        return spec;
    }
}
