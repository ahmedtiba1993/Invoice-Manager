package com.tiba.invoice.customer;

public record CustomerRequest(
        String firstName,
        String lastName,
        String commercialName,
        String address,
        String phone,
        String code,
        Integer discount,
        String email
) {}