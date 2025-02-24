package io.pareek.fin.auth.service;

import io.pareek.fin.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    public boolean validateToken(String token, String email) {
        return jwtUtil.validateToken(token, email);
    }

    public String generateToken(String email) {
        return jwtUtil.generateToken(email);
    }
}