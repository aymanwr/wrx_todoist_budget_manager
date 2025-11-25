package com.tbm.task_budget_manager.task;

import com.tbm.task_budget_manager.user.User;
import org.springframework.data.jpa.domain.Specification;

public final class TaskSpecifications {
    private TaskSpecifications() {}

    public static Specification<Task> byUser(User user) {
        return (root, q, cb) -> cb.equal(root.get("user"), user);
    }
}
