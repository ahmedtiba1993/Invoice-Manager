package com.tiba.invoice.exception;

import java.util.List;

public class DuplicateEntityException extends RuntimeException {
  private final List<String> errors;

  public DuplicateEntityException(List<String> errors) {
    super();
    this.errors = errors;
  }

  public List<String> getErrors() {
    return errors;
  }
}