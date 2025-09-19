package com.tiba.invoice.mapper;

import com.tiba.invoice.dto.response.InvoiceDetailResponse;
import com.tiba.invoice.dto.response.InvoiceSummaryResponse;
import com.tiba.invoice.entity.Customer;
import com.tiba.invoice.entity.Invoice;
import com.tiba.invoice.entity.InvoiceLine;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class InvoiceMapper {

  public InvoiceSummaryResponse toInvoiceSummary(Invoice invoice) {
    if (invoice == null) {
      return null;
    }

    return InvoiceSummaryResponse.builder()
        .id(invoice.getId())
        .reference(invoice.getReference())
        .invoiceDate(invoice.getInvoiceDate())
        .totalAmount(invoice.getTotalAmount())
        .paymentStatus(invoice.getPaymentStatus())
        .customerBusinessName(invoice.getCustomer().getBusinessName())
        .build();
  }

  public InvoiceDetailResponse toDetailResponse(Invoice invoice) {
    if (invoice == null) {
      return null;
    }

    return InvoiceDetailResponse.builder()
        .id(invoice.getId())
        .reference(invoice.getReference())
        .invoiceDate(invoice.getInvoiceDate())
        .totalAmount(invoice.getTotalAmount())
        .paymentStatus(invoice.getPaymentStatus())
        .subtotal(invoice.getSubtotal())
        .customer(mapToNestedCustomer(invoice.getCustomer()))
        .invoiceLines(
            invoice.getInvoiceLines().stream()
                .map(this::mapToNestedInvoiceLine)
                .collect(Collectors.toList()))
        .build();
  }

  private InvoiceDetailResponse.Customer mapToNestedCustomer(Customer entity) {
    if (entity == null) return null;
    return InvoiceDetailResponse.Customer.builder()
        .email(entity.getEmail())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .businessName(entity.getBusinessName())
        .phoneNumber(entity.getPhoneNumber())
        .address(entity.getAddress())
        .build();
  }

  private InvoiceDetailResponse.InvoiceLine mapToNestedInvoiceLine(InvoiceLine entity) {
    if (entity == null) return null;
    return InvoiceDetailResponse.InvoiceLine.builder()
        .productName(entity.getProduct().getName())
        .quantity(entity.getQuantity())
        .unitPrice(entity.getUnitPrice())
        .discount(entity.getDiscount())
        .totalPrice(entity.getTotalPrice())
        .build();
  }
}
