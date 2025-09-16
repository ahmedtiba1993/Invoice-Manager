package com.tiba.invoice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiscalConfigResponse {

  private Long id;
  private Integer vatRate;
  private BigDecimal taxStamp;
}
