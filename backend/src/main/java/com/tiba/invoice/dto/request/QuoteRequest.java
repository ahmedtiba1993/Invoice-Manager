package com.tiba.invoice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record QuoteRequest(
    @NotNull(message = "CUSTOMER_ID_CANNOT_BE_NULL") Long customerId,
    @NotNull(message = "QUOTE_DATE_CANNOT_BE_NULL") LocalDate quoteDate,
    @NotEmpty(message = "QUOTE_MUST_HAVE_AT_LEAST_ONE_LINE")
        List<@Valid QuoteLineRequest> quoteLines) {}
