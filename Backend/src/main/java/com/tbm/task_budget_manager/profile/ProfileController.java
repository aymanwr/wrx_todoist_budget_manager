package com.tbm.task_budget_manager.profile;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @GetMapping
    public ResponseEntity<?> me(Authentication auth) {
        return ResponseEntity.ok().body(auth.getName());
    }
}
