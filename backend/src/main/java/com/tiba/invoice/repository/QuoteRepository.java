package com.tiba.invoice.repository;

import com.tiba.invoice.entity.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

  @Query("SELECT COUNT(i) FROM Quote i WHERE YEAR(i.createdDate) = :year")
  long countByYear(@Param("year") int year);

  @Query(
      """
      SELECT q FROM Quote q
      WHERE (:reference IS NULL OR LOWER(q.reference) LIKE LOWER(CONCAT('%', :reference, '%')))
        AND (:startDate IS NULL OR q.documentDate >= :startDate)
        AND (:endDate IS NULL OR q.documentDate <= :endDate)
        AND (:customerId IS NULL OR q.customer.id = :customerId)
        AND (:minTotalAmount IS NULL OR q.totalAmount >= :minTotalAmount)
        AND (:maxTotalAmount IS NULL OR q.totalAmount <= :maxTotalAmount)
      """)
  Page<Quote> getAllQuotesPaginated(
      @Param("reference") String reference,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate,
      @Param("customerId") Long customerId,
      @Param("minTotalAmount") Double minTotalAmount,
      @Param("maxTotalAmount") Double maxTotalAmount,
      Pageable pageable);
}
