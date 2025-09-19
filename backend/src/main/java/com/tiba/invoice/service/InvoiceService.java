package com.tiba.invoice.service;

import com.tiba.invoice.dto.request.InvoiceLineRequest;
import com.tiba.invoice.dto.request.InvoiceRequest;
import com.tiba.invoice.entity.*;
import com.tiba.invoice.repository.InvoiceRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

  private final InvoiceRepository invoiceRepository;
  private final CustomerService customerService;
  private final ProductService productService;
  private final FiscalConfigService fiscalConfigService;
  private final EntityManager entityManager;

  @Transactional
  public Long createInvoice(InvoiceRequest request) {

    // Verify customer existence
    Customer customer = customerService.findCustomerByIdOrThrow(request.customerId());

    // Validate that all provided product IDs exist in the database
    List<Long> productIdsFromRequest =
        request.invoiceLines().stream().map(InvoiceLineRequest::productId).toList();
    productService.validateProductsExistence(productIdsFromRequest);

    // Get fiscal configuration (VAT rate, tax stamp, etc.)
    FiscalConfig fiscalConfig = fiscalConfigService.getFiscalConfig();

    // Create a new invoice and set basic information
    Invoice invoice = new Invoice();
    invoice.setReference(generateReferenceDev());
    invoice.setCustomer(customer);
    invoice.setInvoiceDate(request.invoiceDate());

    // Prepare list of invoice lines and initialize subtotal
    List<InvoiceLine> invoiceLines = new ArrayList<>();
    BigDecimal subtotal = BigDecimal.ZERO;

    // Process each invoice line by calculating the subtota and creating the invoice
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

    // Calculate VAT multiplier (e.g., 19% → 1.19)
    BigDecimal vatMultiplier =
        BigDecimal.ONE.add(
            BigDecimal.valueOf(fiscalConfig.getVatRate()).divide(BigDecimal.valueOf(100)));
    BigDecimal totalWithVat = subtotal.multiply(vatMultiplier);

    // Set calculated values into invoice
    invoice.setInvoiceLines(invoiceLines);
    invoice.setSubtotal(subtotal.doubleValue());
    invoice.setTotalAmount(totalWithVat.doubleValue());
    invoice.setVat(fiscalConfig.getVatRate());
    invoice.setTaxStamp(fiscalConfig.getTaxStamp().doubleValue());

    return invoiceRepository.save(invoice).getId();
  }

  /**
   * Generate a sequential invoice reference based on the current year. This implementation is
   * compatible with PostgreSQL (uses sequences). ⚠️ Not compatible with MySQL, as MySQL does not
   * support sequences in this way.
   */
  public String generateReference() {
    int currentYear = LocalDate.now().getYear();
    String sequenceName = "invoice_sequence_" + currentYear;
    createSequenceIfNotExists(sequenceName);
    String sql = "SELECT nextval('" + sequenceName + "')";
    BigInteger nextVal = (BigInteger) entityManager.createNativeQuery(sql).getSingleResult();

    return String.format("%d-%04d", currentYear, nextVal.intValue());
  }

  private void createSequenceIfNotExists(String sequenceName) {
    try {
      entityManager
          .createNativeQuery("CREATE SEQUENCE IF NOT EXISTS " + sequenceName + " START 1")
          .executeUpdate();
    } catch (Exception e) {
      // Séquence existe déjà
    }
  }

  private String generateReferenceDev() {
    int currentYear = LocalDate.now().getYear();
    long count = invoiceRepository.countByYear(currentYear);
    return String.format("%d-%04d", currentYear, count + 1);
  }
}
