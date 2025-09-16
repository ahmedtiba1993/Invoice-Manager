package com.tiba.invoice.controller;

import com.tiba.invoice.dto.request.InvoiceRequest;
import com.tiba.invoice.dto.response.ApiResponse;
import com.tiba.invoice.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

  private final InvoiceService invoiceService;

  @PostMapping
  public ResponseEntity<ApiResponse<Long>> createInvoice(
      @Valid @RequestBody InvoiceRequest request) {
    Long invoiceId = invoiceService.createInvoice(request);
    ApiResponse<Long> response = ApiResponse.success(invoiceId, "INVOICE_CREATED_SUCCESSFULLY");
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
