package com.tiba.invoice.mapper;

import com.tiba.invoice.dto.response.InvoiceSummaryResponse;
import com.tiba.invoice.entity.Invoice;
import org.springframework.stereotype.Service;

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
}
