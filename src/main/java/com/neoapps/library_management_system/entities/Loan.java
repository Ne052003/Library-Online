package com.neoapps.library_management_system.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.*;

import java.time.LocalDateTime;

@JsonTypeName("loan")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Loan extends Transaction {

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private boolean isPaid;

    private LocalDateTime deadline;

    @PrePersist
    public void prePersist() {
        this.deadline = LocalDateTime.now().plusMonths(1);
    }

}
