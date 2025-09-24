package com.tiba.invoice.dto.request;

public record ProductFilterRequest(
    String name, String code, Boolean discountStatus, Long categoryId) {}
