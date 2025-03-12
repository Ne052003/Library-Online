package com.neoapps.library_management_system.repositories;

import com.neoapps.library_management_system.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {

}
