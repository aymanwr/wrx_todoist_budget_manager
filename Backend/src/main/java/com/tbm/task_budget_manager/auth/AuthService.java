package com.tbm.task_budget_manager.auth;

import com.tbm.task_budget_manager.auth.dto.AuthRequest;
import com.tbm.task_budget_manager.auth.dto.AuthResponse;
import com.tbm.task_budget_manager.auth.dto.RegisterRequest;
import com.tbm.task_budget_manager.security.jwt.JwtService;
import com.tbm.task_budget_manager.user.Role;
import com.tbm.task_budget_manager.user.User;
import com.tbm.task_budget_manager.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        if (userRepository.findByEmail(req.email()).isPresent()) {
            // إن كان مسجل من قبل، نرجع توكن مباشرة لتسهيل التطوير
            String token = jwtService.generateUserToken(req.email());
            return new AuthResponse(token);
        }
        var user = User.builder()
                .email(req.email().toLowerCase().trim())
                .password(passwordEncoder.encode(req.password()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String token = jwtService.generateUserToken(user.getEmail());
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest req) {
        var auth = new UsernamePasswordAuthenticationToken(req.email(), req.password());
        authenticationManager.authenticate(auth);
        String token = jwtService.generateUserToken(req.email());
        return new AuthResponse(token);
    }
}
