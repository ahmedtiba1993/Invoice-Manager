package com.tiba.invoice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record InvoiceLineRequest(
    @NotNull(message = "PRODUCT_ID_CANNOT_BE_NULL") Long productId,
    @Positive(message = "QUANTITY_MUST_BE_POSITIVE") int quantity,
    @NotNull(message = "UNIT_PRICE_CANNOT_BE_NULL")
        @Positive(message = "UNIT_PRICE_MUST_BE_POSITIVE")
        BigDecimal unitPrice,
    @PositiveOrZero(message = "DISCOUNT_CANNOT_BE_NEGATIVE") int discount) {}
