package com.tbm.task_budget_manager.task;

import com.tbm.task_budget_manager.task.dto.CreateTaskRequest;
import com.tbm.task_budget_manager.task.dto.TaskResponse;
import com.tbm.task_budget_manager.task.dto.UpdateTaskRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> list() {
        return ResponseEntity.ok(service.listMine());
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest req) {
        return ResponseEntity.ok(service.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody UpdateTaskRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    /** ✅ هادي هي اللي كان خاصاك للـ checkbox */
    @PostMapping("/{id}/toggle")
    public ResponseEntity<TaskResponse> toggle(@PathVariable Long id) {
        return ResponseEntity.ok(service.toggle(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
