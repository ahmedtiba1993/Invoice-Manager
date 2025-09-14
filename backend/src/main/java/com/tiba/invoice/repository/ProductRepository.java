package com.tiba.invoice.repository;

import com.tiba.invoice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  boolean existsByCode(String code);

  boolean existsByName(String name);
}
