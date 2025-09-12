package com.tiba.invoice.dto.response;

import java.time.Instant;

public record ApiResponse<T>(boolean success, String message, T data, Instant timestamp) {
  public static <T> ApiResponse<T> success(T data, String message) {
    return new ApiResponse<>(true, message, data, Instant.now());
  }

  public static <T> ApiResponse<T> error(String message) {
    return new ApiResponse<>(false, message, null, Instant.now());
  }

  public static <T> ApiResponse<T> error(T data, String message) {
    return new ApiResponse<>(false, message, data, Instant.now());
  }
}
