package io.pareek.fin.auth.controller;

import io.pareek.fin.auth.dto.UserInfoDTO;
import io.pareek.fin.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<String> generateToken(@RequestParam String email) {
        String token = authService.generateToken(email);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/token/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token, @RequestParam String email) {
        boolean isValid = authService.validateToken(token, email);
        return ResponseEntity.ok(isValid);
    }

//    @GetMapping("/user")
//    public ResponseEntity<UserInfoDTO> getUserInfo(@RequestParam String email) {
//        UserInfoDTO userInfo = authService.getUserInfo(email);
//        return ResponseEntity.ok(userInfo);
//    }
}