package com.tbm.task_budget_manager.task;

import com.tbm.task_budget_manager.task.dto.CreateTaskRequest;
import com.tbm.task_budget_manager.task.dto.TaskResponse;
import com.tbm.task_budget_manager.task.dto.UpdateTaskRequest;
import com.tbm.task_budget_manager.user.User;
import com.tbm.task_budget_manager.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repo;
    private final UserRepository userRepo;

    public TaskService(TaskRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    /** جيب اليوزر الحالي من الـ SecurityContext */
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth == null ? null : auth.getName();
        if (email == null) throw new IllegalStateException("Unauthenticated");
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found: " + email));
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> listMine() {
        User me = getCurrentUser();
        return repo.findAllByUserOrderByCreatedAtDesc(me)
                .stream()
                .map(TaskResponse::of)
                .toList();
    }

    @Transactional
    public TaskResponse create(CreateTaskRequest req) {
        User me = getCurrentUser();
        Task t = new Task();
        t.setUser(me);
        t.setTitle(req.getTitle().trim());
        t.setDescription(req.getDescription());
        t.setStatus(TaskStatus.TODO);
        t = repo.save(t);
        return TaskResponse.of(t);
    }

    @Transactional
    public TaskResponse update(Long id, UpdateTaskRequest req) {
        User me = getCurrentUser();
        Task t = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        if (!t.getUser().getId().equals(me.getId())) throw new IllegalStateException("Forbidden");

        if (req.getTitle() != null) t.setTitle(req.getTitle().trim());
        if (req.getDescription() != null) t.setDescription(req.getDescription());
        if (req.getStatus() != null) t.setStatus(req.getStatus());

        return TaskResponse.of(t);
    }

    @Transactional
    public TaskResponse toggle(Long id) {
        User me = getCurrentUser();
        Task t = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        if (!t.getUser().getId().equals(me.getId())) throw new IllegalStateException("Forbidden");

        t.setStatus(t.getStatus() == TaskStatus.DONE ? TaskStatus.TODO : TaskStatus.DONE);
        return TaskResponse.of(t);
    }

    @Transactional
    public void delete(Long id) {
        User me = getCurrentUser();
        Task t = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        if (!t.getUser().getId().equals(me.getId())) throw new IllegalStateException("Forbidden");
        repo.delete(t);
    }
}
