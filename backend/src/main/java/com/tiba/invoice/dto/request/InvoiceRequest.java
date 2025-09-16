package com.tiba.invoice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record InvoiceRequest(
    @NotBlank(message = "REFERENCE_CANNOT_BE_EMPTY") String reference,
    @NotNull(message = "CUSTOMER_ID_CANNOT_BE_NULL") Long customerId,
    @NotNull(message = "INVOICE_DATE_CANNOT_BE_NULL") LocalDate invoiceDate,
    @NotEmpty(message = "INVOICE_MUST_HAVE_AT_LEAST_ONE_LINE")
        List<@Valid InvoiceLineRequest> invoiceLines) {}
