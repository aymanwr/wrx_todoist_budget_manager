package com.tbm.task_budget_manager.health;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health")
public class HealthController {
    @GetMapping
    public ResponseEntity<String> ok() { return ResponseEntity.ok("All good"); }
}
