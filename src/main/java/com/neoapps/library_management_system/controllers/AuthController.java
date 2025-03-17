package com.neoapps.library_management_system.controllers;

import com.neoapps.library_management_system.config.AuthRequest;
import com.neoapps.library_management_system.dto.UserCreateDTO;
import com.neoapps.library_management_system.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(userCreateDTO.toDAO()));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest authRequest) {
        Map<String, Object> response = authService.authenticate(authRequest.getEmail(), authRequest.getPassword());
        return ResponseEntity.ok(response);
    }

}
