package com.tiba.invoice.repository;

import com.tiba.invoice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
  boolean existsByCode(String code);

  boolean existsByName(String name);

  long countByIdIn(List<Long> ids);

  @Query(
"""
    SELECT p FROM Product p
    WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
      AND (:code IS NULL OR LOWER(p.code) LIKE LOWER(:code))
      AND (:discountStatus IS NULL OR p.discountStatus = :discountStatus)
      AND (:categoryId IS NULL OR p.category.id = :categoryId)
""")
  Page<Product> filterProducts(
      @Param("name") String name,
      @Param("code") String code,
      @Param("discountStatus") Boolean discountStatus,
      @Param("categoryId") Long categoryId,
      Pageable pageable);
}
