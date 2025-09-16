package com.tiba.invoice.service;

import com.tiba.invoice.dto.request.InvoiceLineRequest;
import com.tiba.invoice.dto.request.InvoiceRequest;
import com.tiba.invoice.entity.*;
import com.tiba.invoice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

  private final InvoiceRepository invoiceRepository;
  private final CustomerService customerService;
  private final ProductService productService;
  private final FiscalConfigService fiscalConfigService;

  @Transactional
  public Long createInvoice(InvoiceRequest request) {

    Customer customer = customerService.findCustomerByIdOrThrow(request.customerId());

    List<Long> productIdsFromRequest =
        request.invoiceLines().stream().map(InvoiceLineRequest::productId).toList();
    productService.validateProductsExistence(productIdsFromRequest);

    FiscalConfig fiscalConfig = fiscalConfigService.getFiscalConfig();

    Invoice invoice = new Invoice();
    invoice.setReference(request.reference());
    invoice.setCustomer(customer);
    invoice.setInvoiceDate(request.invoiceDate());

    List<InvoiceLine> invoiceLines = new ArrayList<>();
    BigDecimal subtotal = BigDecimal.ZERO;

    for (InvoiceLineRequest lineRequest : request.invoiceLines()) {

      BigDecimal unitPrice = lineRequest.unitPrice();
      BigDecimal quantity = new BigDecimal(lineRequest.quantity());

      BigDecimal lineSubtotal = unitPrice.multiply(quantity);

      BigDecimal discountAmount =
          lineSubtotal.multiply(new BigDecimal(lineRequest.discount())).divide(new BigDecimal(100));

      BigDecimal lineTotal = lineSubtotal.subtract(discountAmount);

      Product product = new Product();
      product.setId(lineRequest.productId());

      InvoiceLine invoiceLine =
          InvoiceLine.builder()
              .totalPrice(lineTotal)
              .unitPrice(lineRequest.unitPrice())
              .discount(lineRequest.discount())
              .quantity(lineRequest.quantity())
              .product(product)
              .invoice(invoice)
              .build();

      subtotal = subtotal.add(lineTotal);

      invoiceLines.add(invoiceLine);
    }

    BigDecimal vatMultiplier =
        BigDecimal.ONE.add(
            BigDecimal.valueOf(fiscalConfig.getVatRate()).divide(BigDecimal.valueOf(100)));
    BigDecimal totalWithVat = subtotal.multiply(vatMultiplier);

    invoice.setInvoiceLines(invoiceLines);
    invoice.setSubtotal(subtotal.doubleValue());
    invoice.setTotalAmount(totalWithVat.doubleValue());
    invoice.setVat(fiscalConfig.getVatRate());
    invoice.setTaxStamp(fiscalConfig.getTaxStamp().doubleValue());

    return invoiceRepository.save(invoice).getId();
  }
}
