package com.neoapps.library_management_system.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Purchase extends Transaction {
    @OneToMany
    @JoinColumn(name = "purchase_id")
    private List<PurchaseItem> items;

}
