package com.tiba.invoice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "default_seq", sequenceName = "Quote_seq", allocationSize = 1)
public class Quote extends AbstractCommercialDocument {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private Customer customer;

  @OneToMany(
      mappedBy = "quote",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private List<QuoteLine> quoteLines;

  @OneToOne(mappedBy = "quote", fetch = FetchType.LAZY)
  private Invoice invoice;
}
