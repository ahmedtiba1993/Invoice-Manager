package com.tiba.invoice.controller;

import com.tiba.invoice.dto.request.QuoteFilterRequest;
import com.tiba.invoice.dto.request.QuoteRequest;
import com.tiba.invoice.dto.response.ApiResponse;
import com.tiba.invoice.dto.response.PageResponseDto;
import com.tiba.invoice.dto.response.QuoteDetailResponse;
import com.tiba.invoice.dto.response.QuoteSummaryResponse;
import com.tiba.invoice.service.QuoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quotes")
@RequiredArgsConstructor
public class QuoteController {

  private final QuoteService quoteService;

  @PostMapping
  public ResponseEntity<ApiResponse<Long>> createQuote(@Valid @RequestBody QuoteRequest request) {
    Long quoteId = quoteService.createQuote(request);
    ApiResponse<Long> response = ApiResponse.success(quoteId, "QUOTE_CREATED_SUCCESSFULLY");
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PostMapping("/search")
  public ResponseEntity<ApiResponse<PageResponseDto<QuoteSummaryResponse>>> searchQuotesPaginated(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      @RequestBody QuoteFilterRequest filter) {

    PageResponseDto<QuoteSummaryResponse> quotePage =
        quoteService.getAllQuotesPaginated(filter, page, size);

    ApiResponse<PageResponseDto<QuoteSummaryResponse>> response =
        ApiResponse.success(quotePage, "QUOTES_FETCHED_SUCCESSFULLY");

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<QuoteDetailResponse>> getQuoteById(@PathVariable Long id) {
    QuoteDetailResponse quoteDetails = quoteService.getQuoteDetailsById(id);
    ApiResponse<QuoteDetailResponse> response =
        ApiResponse.success(quoteDetails, "QUOTE_DETAILS_FETCHED_SUCCESSFULLY");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
