package com.tiba.invoice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "default_seq", sequenceName = "invoice_line_seq", allocationSize = 1)
public class InvoiceLine extends BaseEntity {

  @Positive
  @Column(nullable = false)
  private int quantity;

  @Column(name = "unit_price", nullable = false)
  private BigDecimal unitPrice;

  @Column(nullable = false)
  private int discount; // Remise en pourcentage (ex: 10 pour 10%)

  @Column(name = "total_price", nullable = false)
  private BigDecimal totalPrice; // Prix total pour cette ligne (quantité * prix unitaire) - remise

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "invoice_id", nullable = false)
  private Invoice invoice;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;
}
