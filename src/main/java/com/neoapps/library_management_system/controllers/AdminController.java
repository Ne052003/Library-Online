package com.neoapps.library_management_system.controllers;

import com.neoapps.library_management_system.dto.UserCreateDTO;
import com.neoapps.library_management_system.dto.UserResponseDTO;
import com.neoapps.library_management_system.entities.Bill;
import com.neoapps.library_management_system.entities.Book;
import com.neoapps.library_management_system.services.BillService;
import com.neoapps.library_management_system.services.BookService;
import com.neoapps.library_management_system.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserService userService;
    private final BookService bookService;
    private final BillService billService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/bills")
    public ResponseEntity<List<Bill>> getAllBills() {
        return ResponseEntity.ok(billService.getAll());
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userCreateDTO));
    }

    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(book));
    }

    @PutMapping("/users")
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UserResponseDTO userResponseDTO) {
        return ResponseEntity.ok(userService.updateUser(userResponseDTO));
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable("id") Long id) {
        return ResponseEntity.ok(bookService.update(id, book));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> delete(@PathVariable("id") Long id) {
        Optional<UserResponseDTO> removedUser = userService.deleteUser(id);
        return removedUser.map(userDTO -> ResponseEntity.status(HttpStatus.OK).body(userDTO)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookService.deleteById(id));
    }
}
