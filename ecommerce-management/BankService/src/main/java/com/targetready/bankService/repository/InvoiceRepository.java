package com.targetready.bankService.repository;

import com.targetready.orderService.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    //  add custom query methods here if needed
}
