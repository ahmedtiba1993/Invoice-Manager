package com.tiba.invoice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public class AbstractCommercialDocument extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String reference;

  @Column(nullable = false)
  private LocalDate documentDate;

  @Column(nullable = false)
  private int vat; // TVA

  @Column(nullable = false)
  private Double subtotal;

  @Column(nullable = false)
  private Double totalAmount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;
}
