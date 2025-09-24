package com.tiba.invoice.service;

import com.tiba.invoice.dto.request.InvoiceFilterRequest;
import com.tiba.invoice.dto.request.InvoiceLineRequest;
import com.tiba.invoice.dto.request.InvoiceRequest;
import com.tiba.invoice.dto.response.InvoiceDetailResponse;
import com.tiba.invoice.dto.response.InvoiceSummaryResponse;
import com.tiba.invoice.dto.response.PageResponseDto;
import com.tiba.invoice.entity.*;
import com.tiba.invoice.mapper.InvoiceMapper;
import com.tiba.invoice.repository.InvoiceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
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
  private final InvoiceMapper invoiceMapper;

  @Transactional
  public Long createInvoice(InvoiceRequest request) {

    // Validate customer
    Customer customer = customerService.findCustomerByIdOrThrow(request.customerId());

    // Validate products
    validateProductsExistence(request);

    // Get fiscal configuration
    FiscalConfig fiscalConfig = fiscalConfigService.getFiscalConfig();

    // Build the base invoice object
    Invoice invoice = buildInvoiceBase(request, customer);

    // Build invoice lines and calculate subtotal
    List<InvoiceLine> invoiceLines = buildInvoiceLines(request, invoice);
    BigDecimal subtotal = calculateSubtotal(invoiceLines);

    // Calculate VAT
    BigDecimal totalWithVat = calculateTotalWithVat(subtotal, fiscalConfig.getVatRate());

    // Populate the invoice with final values
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

  private void validateProductsExistence(InvoiceRequest request) {
    List<Long> productIds =
        request.invoiceLines().stream().map(InvoiceLineRequest::productId).toList();
    productService.validateProductsExistence(productIds);
  }

  private Invoice buildInvoiceBase(InvoiceRequest request, Customer customer) {
    Invoice invoice = new Invoice();
    invoice.setReference(generateReferenceDev());
    invoice.setCustomer(customer);
    invoice.setDocumentDate(request.invoiceDate());
    return invoice;
  }

  private List<InvoiceLine> buildInvoiceLines(InvoiceRequest request, Invoice invoice) {
    List<InvoiceLine> invoiceLines = new ArrayList<>();

    for (InvoiceLineRequest lineRequest : request.invoiceLines()) {
      BigDecimal unitPrice = lineRequest.unitPrice();
      BigDecimal quantity = BigDecimal.valueOf(lineRequest.quantity());

      BigDecimal lineSubtotal = unitPrice.multiply(quantity);
      BigDecimal discountAmount =
          lineSubtotal
              .multiply(BigDecimal.valueOf(lineRequest.discount()))
              .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

      BigDecimal lineTotal = lineSubtotal.subtract(discountAmount);

      Product product = new Product();
      product.setId(lineRequest.productId());

      InvoiceLine invoiceLine =
          InvoiceLine.builder()
              .totalPrice(lineTotal)
              .unitPrice(unitPrice)
              .discount(lineRequest.discount())
              .quantity(lineRequest.quantity())
              .product(product)
              .invoice(invoice)
              .build();

      invoiceLines.add(invoiceLine);
    }

    return invoiceLines;
  }

  private BigDecimal calculateSubtotal(List<InvoiceLine> invoiceLines) {
    return invoiceLines.stream()
        .map(InvoiceLine::getTotalPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private BigDecimal calculateTotalWithVat(BigDecimal subtotal, double vatRate) {
    BigDecimal vatMultiplier =
        BigDecimal.ONE.add(BigDecimal.valueOf(vatRate).divide(BigDecimal.valueOf(100)));
    return subtotal.multiply(vatMultiplier);
  }

  @Transactional(readOnly = true)
  public PageResponseDto<InvoiceSummaryResponse> getAllInvoicesPaginated(
      InvoiceFilterRequest filter, int page, int size) {

    Pageable pageable = PageRequest.of(page, size, Sort.by("documentDate").descending());

    Page<Invoice> invoicePage =
        invoiceRepository.getAllInvoicesPaginated(
            filter.paymentStatus(),
            filter.reference(),
            filter.startDate(),
            filter.endDate(),
            filter.customerId(),
            filter.minTotalAmount(),
            filter.maxTotalAmount(),
            pageable);
    List<InvoiceSummaryResponse> invoiceList =
        invoicePage.stream().map(invoiceMapper::toInvoiceSummary).toList();
    return PageResponseDto.fromPage(invoicePage, invoiceList);
  }

  @Transactional(readOnly = true)
  public InvoiceDetailResponse getInvoiceDetailsById(Long id) {
    Invoice invoice =
        invoiceRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Invoice not found with id: " + id));
    return invoiceMapper.toDetailResponse(invoice);
  }
}
