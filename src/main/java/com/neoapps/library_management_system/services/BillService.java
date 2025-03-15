package com.neoapps.library_management_system.services;

import com.neoapps.library_management_system.entities.*;
import com.neoapps.library_management_system.repositories.BillRepository;
import com.neoapps.library_management_system.repositories.BookRepository;
import com.neoapps.library_management_system.utils.NotEnoughStockException;
import com.neoapps.library_management_system.utils.ObjectNotSavedException;
import com.neoapps.library_management_system.utils.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<Bill> getAll() {
        return billRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Bill> getById(Long id) {
        return billRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Bill> getByUserId(Long userId) {
        return billRepository.findByUserId(userId);
    }


    @Transactional
    public Bill save(Bill bill) {
        try {
            bill.setTotal(getTotal(bill.getTransactions()));
            return billRepository.save(bill);
        } catch (Exception e) {
            throw new ObjectNotSavedException("Error saving the bill: " + e.getMessage());
        }
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (billRepository.findById(id).isPresent()) {
            billRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private BigDecimal getTotal(List<Transaction> transactions) {
        return transactions.stream()
                .map(this::calculateTransactionTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTransactionTotal(Transaction transaction) {

        if (transaction instanceof Purchase purchase) {

            purchase.getItems().forEach(this::validateBookStock);

            List<Long> bookIds = purchase.getItems().stream()
                    .map(purchaseItem -> purchaseItem.getBook().getId())
                    .toList();

            Map<Long, Book> books = bookRepository.findAllById(bookIds).stream()
                    .collect(Collectors.toMap(Book::getId, book -> book));

            BigDecimal totalPurchase = purchase.getItems().stream().parallel()
                    .map(purchaseItem -> books.get(purchaseItem.getBook().getId())
                            .getPrice()
                            .multiply(BigDecimal.valueOf(purchaseItem.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            purchase.setTotal(totalPurchase);

        } else if (transaction instanceof Loan loan) {

            Book bookDb = bookRepository.findById(loan.getBook().getId()).orElseThrow(() -> new ResourceNotFoundException("The book with id: " + loan.getBook().getId() + " does not exist"));
            loan.setTotal(bookDb.getRentalPrice());
        }

        return transaction.getTotal();
    }

    public void validateBookStock(PurchaseItem item) {

        Book bookDb = bookRepository.findById(item.getBook().getId()).orElseThrow(() -> new ResourceNotFoundException("The book with id: " + item.getBook().getId() + " does not exist"));
        int currentStock = bookDb.getStock();

        if (currentStock < item.getQuantity()) {
            throw new NotEnoughStockException("Not enough stock of book: " + bookDb.getTitle());
        } else {
            bookDb.setStock(currentStock - item.getQuantity());
            bookRepository.save(bookDb);
        }

    }

}
