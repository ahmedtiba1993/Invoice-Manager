package com.tiba.invoice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
    @NotBlank(message = "CATEGORY_NAME_CANNOT_BE_EMPTY")
        @Size(min = 2, max = 100, message = "CATEGORY_NAME_MUST_BE_BETWEEN_2_AND_100_CHARACTERS")
        String name,
    @Size(max = 1000, message = "DESCRIPTION_CANNOT_EXCEED_1000_CHARACTERS") String description) {}
