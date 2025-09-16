package com.tiba.invoice.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record FiscalConfigRequest(
    @NotNull(message = "vat-rate-is-required")
        @Min(value = 0, message = "vat-rate-cannot-be-negative")
        @Max(value = 100, message = "vat-rate-cannot-exceed-100-percent")
        Integer vatRate,
    @NotNull(message = "tax-stamp-amount-is-required")
        @DecimalMin(value = "0.0", message = "tax-stamp-cannot-be-negative")
        @DecimalMax(value = "999.99", message = "tax-stamp-cannot-exceed-999.99")
        BigDecimal taxStamp) {}
