package com.tiba.invoice.dto.request;

import java.time.LocalDate;

public record QuoteFilterRequest(
    String reference,
    LocalDate startDate,
    LocalDate endDate,
    Long customerId,
    Double minTotalAmount,
    Double maxTotalAmount) {}
