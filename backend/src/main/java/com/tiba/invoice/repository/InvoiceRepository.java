package com.tiba.invoice.repository;

import com.tiba.invoice.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
  @Query("SELECT COUNT(i) FROM Invoice i WHERE YEAR(i.createdDate) = :year")
  long countByYear(@Param("year") int year);

  @Query(
"""
    SELECT i FROM Invoice i
    WHERE (:paymentStatus IS NULL OR i.paymentStatus = :paymentStatus)
      AND (:reference IS NULL OR LOWER(i.reference) LIKE LOWER(CONCAT('%', :reference, '%')))
      AND (:startDate IS NULL OR i.documentDate >= :startDate)
      AND (:endDate IS NULL OR i.documentDate <= :endDate)
      AND (:customerId IS NULL OR i.customer.id = :customerId)
      AND (:minTotalAmount IS NULL OR i.totalAmount >= :minTotalAmount)
      AND (:maxTotalAmount IS NULL OR i.totalAmount <= :maxTotalAmount)
""")
  Page<Invoice> getAllInvoicesPaginated(
      @Param("paymentStatus") Boolean paymentStatus,
      @Param("reference") String reference,
      @Param("startDate") java.time.LocalDate startDate,
      @Param("endDate") java.time.LocalDate endDate,
      @Param("customerId") Long customerId,
      @Param("minTotalAmount") Double minTotalAmount,
      @Param("maxTotalAmount") Double maxTotalAmount,
      Pageable pageable);
}
