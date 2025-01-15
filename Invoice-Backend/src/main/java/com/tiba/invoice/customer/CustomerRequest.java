package com.tiba.invoice.customer;

import jakarta.validation.constraints.*;

public record CustomerRequest(
        @NotBlank(message = "FIRST_NAME_MANDATORY")
        @Size(max = 50, message = "FIRST_NAME_TOO_LONG")
        String firstName,

        @NotBlank(message = "LAST_NAME_MANDATORY")
        @Size(max = 50, message = "LAST_NAME_TOO_LONG")
        String lastName,

        @NotBlank(message = "COMMERCIAL_NAME_MANDATORY")
        @Size(max = 100, message = "COMMERCIAL_NAME_TOO_LONG")
        String commercialName,

        @NotBlank(message = "ADDRESS_MANDATORY")
        @Size(max = 255, message = "ADDRESS_TOO_LONG")
        String address,

        @NotBlank(message = "PHONE_MANDATORY")
        String phone,

        @NotBlank(message = "CODE_MANDATORY")
        @Size(max = 20, message = "CODE_TOO_LONG")
        String code,

        @NotNull(message = "DISCOUNT_MANDATORY")
        @Min(value = 0, message = "DISCOUNT_TOO_LOW")
        @Max(value = 100, message = "DISCOUNT_TOO_HIGH")
        Integer discount,

        String email
) {}