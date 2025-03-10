package com.neoapps.library_management_system.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "bill")
    private List<Transaction> transactions;

    @Setter(AccessLevel.NONE)
    private LocalDateTime time;

    private BigDecimal total;

    @PrePersist
    protected void prePersist() {
        this.time = LocalDateTime.now();
    }

}
