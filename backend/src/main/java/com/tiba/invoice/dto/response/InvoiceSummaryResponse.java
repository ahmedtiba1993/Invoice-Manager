package com.tiba.invoice.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceSummaryResponse {
  private Long id;
  private String reference;
  private LocalDate invoiceDate;
  private Double totalAmount;
  private boolean paymentStatus;
  private String customerBusinessName;
}
