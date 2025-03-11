package com.neoapps.library_management_system.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@JsonTypeName("loan")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Loan extends Transaction {

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private boolean isPaid;

    private LocalDateTime deadline;

}
