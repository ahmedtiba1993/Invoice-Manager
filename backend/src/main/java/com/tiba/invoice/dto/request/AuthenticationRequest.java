package com.tiba.invoice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {

    @NotNull(message = "USER_NAME_REQUIRED")
    @NotEmpty(message = "USER_NAME_REQUIRED")
    private String userName;

    @NotEmpty(message = "PASSWORD_REQUIRED")
    @NotNull(message = "PASSWORD_REQUIRED")
    @Size(min = 8, message = "PASSWORD_SIZE_8")
    private String password;
}
