package com.tiba.invoice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CompanyRequest(
    @NotBlank(message = "COMPANY_NAME_REQUIRED") @Size(max = 150, message = "COMPANY_NAME_MAX_150")
        String companyName,
    @NotBlank(message = "ADDRESS_REQUIRED") @Size(max = 255, message = "ADDRESS_MAX_255")
        String address,
    @NotBlank(message = "TAX_NUMBER_REQUIRED") @Size(max = 50, message = "TAX_NUMBER_MAX_50")
        String taxNumber,
    @NotBlank(message = "TRADE_REGISTER_REQUIRED")
        @Size(max = 50, message = "TRADE_REGISTER_MAX_50")
        String tradeRegister,
    @Size(max = 20, message = "TELEPHONE_MAX_20")
        @Pattern(regexp = "\\d{8,20}", message = "TELEPHONE_INVALID")
        String telephone,
    @Size(max = 20, message = "FAX_MAX_20") @Pattern(regexp = "\\d{8,20}", message = "FAX_INVALID")
        String fax,
    @Size(max = 20, message = "MOBILE_NUMBER_MAX_20")
        @Pattern(regexp = "\\d{8,20}", message = "MOBILE_NUMBER_INVALID")
        String mobileNumber) {}
