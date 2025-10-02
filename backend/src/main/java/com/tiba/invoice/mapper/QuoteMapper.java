package com.tiba.invoice.mapper;

import com.tiba.invoice.dto.response.QuoteDetailResponse;
import com.tiba.invoice.dto.response.QuoteSummaryResponse;
import com.tiba.invoice.entity.Customer;
import com.tiba.invoice.entity.Quote;
import com.tiba.invoice.entity.QuoteLine;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class QuoteMapper {

  public QuoteSummaryResponse toQuoteSummary(Quote quote) {
    if (quote == null) {
      return null;
    }

    return QuoteSummaryResponse.builder()
        .id(quote.getId())
        .reference(quote.getReference())
        .quoteDate(quote.getDocumentDate())
        .totalAmount(quote.getTotalAmount())
        .customerBusinessName(quote.getCustomer().getBusinessName())
        .build();
  }

  public QuoteDetailResponse toDetailResponse(Quote quote) {
    if (quote == null) {
      return null;
    }

    return QuoteDetailResponse.builder()
        .id(quote.getId())
        .reference(quote.getReference())
        .quoteDate(quote.getDocumentDate())
        .totalAmount(quote.getTotalAmount())
        .subtotal(quote.getSubtotal())
        .vat(quote.getVat())
        .customer(mapToNestedCustomer(quote.getCustomer()))
        .quoteLines(
            quote.getQuoteLines().stream()
                .map(this::mapToNestedQuoteLine)
                .collect(Collectors.toList()))
        .build();
  }

  private QuoteDetailResponse.Customer mapToNestedCustomer(Customer entity) {
    if (entity == null) return null;
    return QuoteDetailResponse.Customer.builder()
        .email(entity.getEmail())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .businessName(entity.getBusinessName())
        .phoneNumber(entity.getPhoneNumber())
        .address(entity.getAddress())
        .build();
  }

  private QuoteDetailResponse.QuoteLine mapToNestedQuoteLine(QuoteLine entity) {
    if (entity == null) return null;
    return QuoteDetailResponse.QuoteLine.builder()
        .productName(entity.getProduct().getName())
        .quantity(entity.getQuantity())
        .unitPrice(entity.getUnitPrice())
        .discount(entity.getDiscount())
        .totalPrice(entity.getTotalPrice())
        .build();
  }
}
