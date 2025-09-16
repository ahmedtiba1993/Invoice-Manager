package com.tiba.invoice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "default_seq", sequenceName = "fiscal_config_seq", allocationSize = 1)
public class FiscalConfig extends BaseEntity {

  @Column(name = "vat_rate", nullable = false)
  private Integer vatRate;

  @Column(name = "tax_stamp", nullable = false, precision = 10, scale = 2)
  private BigDecimal taxStamp;
}
