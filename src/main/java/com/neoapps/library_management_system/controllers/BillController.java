package com.neoapps.library_management_system.controllers;

import com.neoapps.library_management_system.entities.Bill;
import com.neoapps.library_management_system.services.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library/bills")
public class BillController {

    private final BillService billService;

    @GetMapping("/{id}")
    public ResponseEntity<Bill> getById(@PathVariable("id") Long id) {
        Optional<Bill> optionalBill = billService.getById(id);
        return optionalBill.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Bill>> getByUserId(@PathVariable("userId") Long userId) {
        List<Bill> bills = billService.getByUserId(userId);
        return ResponseEntity.ok(bills);
    }

    @PostMapping
    public ResponseEntity<Bill> create(@RequestBody Bill bill) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billService.save(bill));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        if (billService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
