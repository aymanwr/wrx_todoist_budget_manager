package com.tbm.task_budget_manager.task;

import com.tbm.task_budget_manager.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    List<Task> findAllByUserOrderByCreatedAtDesc(User user);
}
