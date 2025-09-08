package com.tiba.invoice.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CustomerResponse(
    Long id,
    String firstName,
    String lastName,
    String businessName,
    String email,
    String clientCode,
    String address,
    String phoneNumber,
    Double discount,
    LocalDateTime createdDate) {}
