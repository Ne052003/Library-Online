package com.neoapps.library_management_system.controllers;

import com.neoapps.library_management_system.entities.Book;
import com.neoapps.library_management_system.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable("id") Long id) {
        Optional<Book> optionalBook = bookService.getById(id);
        return optionalBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(params = "authorName")
    public ResponseEntity<List<Book>> getByAuthor(@RequestParam("authorName") String authorName) {
        return ResponseEntity.ok(bookService.getByAuthor(authorName));
    }

}
