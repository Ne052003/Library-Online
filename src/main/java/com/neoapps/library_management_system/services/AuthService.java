package com.neoapps.library_management_system.services;

import com.neoapps.library_management_system.dto.UserDTO;
import com.neoapps.library_management_system.entities.Role;
import com.neoapps.library_management_system.entities.User;
import com.neoapps.library_management_system.repositories.UserRepository;
import com.neoapps.library_management_system.utils.JwtUtil;
import com.neoapps.library_management_system.utils.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    public String register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return jwtUtil.generateToken(user);
    }

    public Map<String, Object> authenticate(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        String token = jwtUtil.generateToken(user);
        UserDTO userDTO = new UserDTO(user);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", userDTO);

        return response;
    }
}
