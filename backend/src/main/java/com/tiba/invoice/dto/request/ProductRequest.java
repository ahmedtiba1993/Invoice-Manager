package com.tiba.invoice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductRequest(
    @NotBlank(message = "PRODUCT_NAME_CANNOT_BE_EMPTY")
        @Size(min = 2, max = 150, message = "PRODUCT_NAME_MUST_BE_BETWEEN_2_AND_150_CHARACTERS")
        String name,
    @Size(max = 1000, message = "DESCRIPTION_CANNOT_EXCEED_1000_CHARACTERS") String description,
    @NotBlank(message = "PRODUCT_CODE_CANNOT_BE_EMPTY")
        @Size(min = 2, max = 50, message = "PRODUCT_CODE_MUST_BE_BETWEEN_2_AND_50_CHARACTERS")
        String code,
    @NotNull(message = "PRICE_CANNOT_BE_NULL") @Positive(message = "PRICE_MUST_BE_POSITIVE")
        Double price,
    @NotNull(message = "DISCOUNT_STATUS_CANNOT_BE_NULL") Boolean discountStatus,
    @NotNull(message = "CATEGORY_ID_CANNOT_BE_NULL") Long categoryId) {}
