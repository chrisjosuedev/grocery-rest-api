package dev.chrisjosue.groceryrestapi.repository;

import dev.chrisjosue.groceryrestapi.entity.invoice.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {}
