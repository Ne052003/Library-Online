package com.neoapps.library_management_system.services;

import com.neoapps.library_management_system.entities.Book;
import com.neoapps.library_management_system.repositories.BookRepository;
import com.neoapps.library_management_system.utils.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> getById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Book> getByAuthor(String authorName) {
        return bookRepository.findByAuthor(authorName.stripIndent());
    }

    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Book update(Long id, Book book) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book bookDb = optionalBook.get();
            bookDb.setTitle(book.getTitle());
            bookDb.setAuthor(book.getAuthor());
            bookDb.setPrice(book.getPrice());
            bookDb.setRentalPrice(book.getRentalPrice());
            bookDb.setStock(book.getStock());
            return bookRepository.save(bookDb);
        }
        throw new ResourceNotFoundException("The book with id: " + id + " does not exist");
    }

    @Transactional
    public Book deleteById(Long id) {
        Optional<Book> deletedBookOpt = bookRepository.findById(id);
        if (deletedBookOpt.isPresent()) {
            bookRepository.deleteById(id);
            return deletedBookOpt.orElseThrow();
        }

        throw new ResourceNotFoundException("The Book with id: " + id + " does not exist");
    }
}
