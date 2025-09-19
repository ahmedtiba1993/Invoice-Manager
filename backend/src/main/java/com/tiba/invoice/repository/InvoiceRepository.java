package com.tiba.invoice.repository;

import com.tiba.invoice.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
  @Query("SELECT COUNT(i) FROM Invoice i WHERE YEAR(i.createdDate) = :year")
  long countByYear(@Param("year") int year);
}
