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
@SequenceGenerator(name = "default_seq", sequenceName = "quote_line_seq", allocationSize = 1)
public class QuoteLine extends BaseEntity {

  @Positive
  @Column(nullable = false)
  private int quantity;

  @Column(name = "unit_price", nullable = false)
  private BigDecimal unitPrice;

  @Column(nullable = false)
  private int discount; // Discount in percentage (e.g., 10 for 10%)

  @Column(name = "total_price", nullable = false)
  private BigDecimal totalPrice; //  Total price for this line (quantity * unit price) - discount

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "quote_id", nullable = false)
  private Quote quote;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;
}
