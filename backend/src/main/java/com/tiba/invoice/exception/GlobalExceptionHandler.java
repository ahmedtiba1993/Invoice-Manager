package com.tiba.invoice.exception;

import com.tiba.invoice.dto.response.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.tiba.invoice.exception.BusinessErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {LockedException.class})
  public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {
    return ResponseEntity.status(UNAUTHORIZED)
        .body(
            ExceptionResponse.builder()
                .businessErrorCode(ACCOUNT_LOCKED.getCode())
                .businessErrorDescription(ACCOUNT_LOCKED.getDescription())
                .error(exp.getMessage())
                .build());
  }

  @ExceptionHandler(DisabledException.class)
  public ResponseEntity<ExceptionResponse> handleException(DisabledException exp) {
    return ResponseEntity.status(UNAUTHORIZED)
        .body(
            ExceptionResponse.builder()
                .businessErrorCode(ACCOUNT_DISABLED.getCode())
                .businessErrorDescription(ACCOUNT_DISABLED.getDescription())
                .error(exp.getMessage())
                .build());
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ExceptionResponse> handleException() {
    return ResponseEntity.status(UNAUTHORIZED)
        .body(
            ExceptionResponse.builder()
                .businessErrorCode(BAD_CREDENTIALS.getCode())
                .businessErrorDescription(BAD_CREDENTIALS.getDescription())
                .build());
  }

  @ExceptionHandler(DuplicateEntityException.class)
  public ResponseEntity<ApiResponse<List<String>>> handleDuplicateEntity(
      DuplicateEntityException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(ApiResponse.error(ex.getErrors(), "Duplicate entity found"));
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiResponse<String>> handleEntityNotFoundException(
      EntityNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<List<String>>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {

    List<String> errors =
        ex.getBindingResult().getAllErrors().stream()
            .map(error -> error.getDefaultMessage())
            .toList();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.error(errors, "Validation failed"));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
    exp.printStackTrace();
    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
        .body(
            ExceptionResponse.builder()
                .businessErrorDescription("Internal error, please contact the admin")
                .error(exp.getMessage())
                .build());
  }
}
