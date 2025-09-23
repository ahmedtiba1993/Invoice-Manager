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
public class Invoice extends AbstractCommercialDocument {

  @Column(nullable = false)
  private double taxStamp; // Timbre Fiscal

  @Column(nullable = false)
  private Boolean paymentStatus = false;

  @OneToMany(
      mappedBy = "invoice",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private List<InvoiceLine> invoiceLines;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "quote_id", unique = true)
  private Quote quote;
}
