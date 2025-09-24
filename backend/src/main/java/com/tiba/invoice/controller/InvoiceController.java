package com.tiba.invoice.controller;

import com.tiba.invoice.dto.request.InvoiceFilterRequest;
import com.tiba.invoice.dto.request.InvoiceRequest;
import com.tiba.invoice.dto.response.ApiResponse;
import com.tiba.invoice.dto.response.InvoiceDetailResponse;
import com.tiba.invoice.dto.response.InvoiceSummaryResponse;
import com.tiba.invoice.dto.response.PageResponseDto;
import com.tiba.invoice.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @PostMapping("/search")
  public ResponseEntity<ApiResponse<PageResponseDto<InvoiceSummaryResponse>>>
      searchInvoicesPaginated(
          @RequestParam(name = "page", defaultValue = "0") int page,
          @RequestParam(name = "size", defaultValue = "10") int size,
          @RequestBody InvoiceFilterRequest filter) {

    PageResponseDto<InvoiceSummaryResponse> invoicePage =
        invoiceService.getAllInvoicesPaginated(filter, page, size);

    ApiResponse<PageResponseDto<InvoiceSummaryResponse>> response =
        ApiResponse.success(invoicePage, "INVOICES_FETCHED_SUCCESSFULLY");

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<InvoiceDetailResponse>> getInvoiceById(@PathVariable Long id) {

    InvoiceDetailResponse invoiceDetails = invoiceService.getInvoiceDetailsById(id);

    ApiResponse<InvoiceDetailResponse> response =
        ApiResponse.success(invoiceDetails, "INVOICE_DETAILS_FETCHED_SUCCESSFULLY");

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
