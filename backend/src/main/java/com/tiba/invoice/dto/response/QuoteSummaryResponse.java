package com.tiba.invoice.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuoteSummaryResponse {
  private Long id;
  private String reference;
  private LocalDate quoteDate;
  private Double totalAmount;
  private String customerBusinessName;
}
