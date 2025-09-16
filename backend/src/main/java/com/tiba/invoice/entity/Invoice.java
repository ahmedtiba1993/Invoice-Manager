package com.tiba.invoice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "default_seq", sequenceName = "invoice_seq", allocationSize = 1)
public class Invoice extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String reference;

  @Column(nullable = false)
  private LocalDate invoiceDate;

  @Column(nullable = false)
  private int vat; // TVA

  @Column(nullable = false)
  private Double subtotal;

  @Column(nullable = false)
  private Double totalAmount;

  @Column(nullable = false)
  private double taxStamp; // <- Timbre Fiscal

  @Column(nullable = false)
  private Boolean paymentStatus = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;

  @OneToMany(
      mappedBy = "invoice",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private List<InvoiceLine> invoiceLines;
}
