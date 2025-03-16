package com.neoapps.library_management_system.controllers;

import com.neoapps.library_management_system.dto.UserDTO;
import com.neoapps.library_management_system.entities.User;
import com.neoapps.library_management_system.repositories.UserRepository;
import com.neoapps.library_management_system.services.UserService;
import com.neoapps.library_management_system.utils.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        Optional<User> userOptional = userService.getUserById(id);
        return userOptional.isPresent() ? ResponseEntity.ok(userOptional.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserDTO userDTO = new UserDTO(user);
        return ResponseEntity.ok(userDTO);
    }


    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody User user, @PathVariable("id") Long id) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isPresent()) {
            User userDB = optionalUser.get();
            userDB.setFullName(user.getFullName());
            userDB.setEmail(user.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userDB));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable("id") Long id) {
        Optional<User> removedUser = userService.deleteUser(id);
        return removedUser.map(user -> ResponseEntity.status(HttpStatus.OK).body(user)).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
