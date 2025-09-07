package com.tiba.invoice.dto.request;

import jakarta.validation.constraints.*;

public record CustomerRequest(
    @NotBlank(message = "FIRST_NAME_CANNOT_BE_EMPTY")
        @Size(min = 3, max = 50, message = "FIRST_NAME_MUST_BE_BETWEEN_3_AND_50_CHARACTERS")
        String firstName,
    @NotBlank(message = "LAST_NAME_CANNOT_BE_EMPTY")
        @Size(min = 3, max = 50, message = "LAST_NAME_MUST_BE_BETWEEN_3_AND_50_CHARACTERS")
        String lastName,
    @NotBlank(message = "BUSINESS_NAME_CANNOT_BE_EMPTY")
        @Size(min = 3, message = "BUSINESS_NAME_MUST_BE_BETWEEN_3_AND_50_CHARACTERS")
        String businessName,
    @NotBlank(message = "ADDRESS_CANNOT_BE_EMPTY")
        @Size(min = 3, message = "ADDRESS_MUST_BE_AT_LEAST_3_CHARACTERS")
        String address,
    @NotBlank(message = "PHONE_NUMBER_CANNOT_BE_EMPTY")
        @Size(min = 8, max = 8, message = "PHONE_NUMBER_MUST_BE_EXACTLY_8_CHARACTERS")
        String phoneNumber,
    @NotBlank(message = "CLIENT_CODE_CANNOT_BE_EMPTY") String clientCode,
    @NotNull(message = "DISCOUNT_CANNOT_BE_NULL")
        @PositiveOrZero(message = "DISCOUNT_CANNOT_BE_NEGATIVE")
        Double discount,
    @NotBlank(message = "EMAIL_CANNOT_BE_EMPTY") @Email(message = "INVALID_EMAIL_FORMAT")
        String email) {}
