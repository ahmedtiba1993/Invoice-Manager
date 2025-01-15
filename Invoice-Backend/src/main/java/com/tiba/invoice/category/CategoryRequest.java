package com.tiba.invoice.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(

        Long id,

        @NotBlank(message = "CATEGORY_NAME_REQUIRED")
        String name,

        @NotBlank(message = "CATEGORY_DESCRIPTION_REQUIRED")
        String description
) {
}
