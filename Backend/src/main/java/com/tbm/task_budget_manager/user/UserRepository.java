// src/main/java/com/tbm/task_budget_manager/user/UserRepository.java
package com.tbm.task_budget_manager.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
