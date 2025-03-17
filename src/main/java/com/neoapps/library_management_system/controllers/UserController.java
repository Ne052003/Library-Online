package com.neoapps.library_management_system.controllers;

import com.neoapps.library_management_system.dto.UserResponseDTO;
import com.neoapps.library_management_system.entities.User;
import com.neoapps.library_management_system.repositories.UserRepository;
import com.neoapps.library_management_system.services.UserService;
import com.neoapps.library_management_system.utils.ResourceNotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library/profile")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<UserResponseDTO> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserResponseDTO userResponseDTO = new UserResponseDTO(user);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> updateProfile(@Valid @RequestBody UserResponseDTO userResponseDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.updateUser(userResponseDTO));
    }

    @DeleteMapping
    public ResponseEntity<UserResponseDTO> deleteProfile(@AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User userFound = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Optional<UserResponseDTO> removedUser = userService.deleteUser(userFound.getId());

        return removedUser.map(userResponseDTO -> ResponseEntity.status(HttpStatus.OK).body(userResponseDTO)).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
