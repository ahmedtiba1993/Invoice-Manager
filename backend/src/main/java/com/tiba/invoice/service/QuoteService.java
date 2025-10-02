package com.tiba.invoice.service;

import com.tiba.invoice.dto.request.QuoteFilterRequest;
import com.tiba.invoice.dto.request.QuoteLineRequest;
import com.tiba.invoice.dto.request.QuoteRequest;
import com.tiba.invoice.dto.response.PageResponseDto;
import com.tiba.invoice.dto.response.QuoteDetailResponse;
import com.tiba.invoice.dto.response.QuoteSummaryResponse;
import com.tiba.invoice.entity.*;
import com.tiba.invoice.mapper.QuoteMapper;
import com.tiba.invoice.repository.QuoteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuoteService {

  private final QuoteRepository quoteRepository;
  private final CustomerService customerService;
  private final ProductService productService;
  private final FiscalConfigService fiscalConfigService;
  private final QuoteMapper quoteMapper;

  @Transactional
  public Long createQuote(QuoteRequest request) {

    // Validate customer
    Customer customer = customerService.findCustomerByIdOrThrow(request.customerId());

    // Validate products
    validateProductsExistence(request);

    // Get fiscal configuration
    FiscalConfig fiscalConfig = fiscalConfigService.getFiscalConfig();

    // Build the base quote object
    Quote quote = buildQuoteBase(request, customer);

    // Build quote lines and calculate subtotal
    List<QuoteLine> quoteLines = buildQuoteLines(request, quote);
    BigDecimal subtotal = calculateSubtotal(quoteLines);

    // Calculate VAT
    BigDecimal totalWithVat = calculateTotalWithVat(subtotal, fiscalConfig.getVatRate());

    quote.setQuoteLines(quoteLines);
    quote.setSubtotal(subtotal.doubleValue());
    quote.setTotalAmount(totalWithVat.doubleValue());
    quote.setVat(fiscalConfig.getVatRate());

    return quoteRepository.save(quote).getId();
  }

  private void validateProductsExistence(QuoteRequest request) {
    List<Long> productIds = request.quoteLines().stream().map(QuoteLineRequest::productId).toList();
    productService.validateProductsExistence(productIds);
  }

  private Quote buildQuoteBase(QuoteRequest request, Customer customer) {
    Quote quote = new Quote();
    quote.setReference(generateReferenceQuote());
    quote.setCustomer(customer);
    quote.setDocumentDate(request.quoteDate());
    return quote;
  }

  private String generateReferenceQuote() {
    int currentYear = LocalDate.now().getYear();
    long count = quoteRepository.countByYear(currentYear);
    return String.format("Q-%d-%04d", currentYear, count + 1);
  }

  private List<QuoteLine> buildQuoteLines(QuoteRequest request, Quote quote) {
    List<QuoteLine> quoteLines = new ArrayList<>();

    for (QuoteLineRequest lineRequest : request.quoteLines()) {
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

      QuoteLine quoteLine =
          QuoteLine.builder()
              .totalPrice(lineTotal)
              .unitPrice(unitPrice)
              .discount(lineRequest.discount())
              .quantity(lineRequest.quantity())
              .product(product)
              .quote(quote)
              .build();

      quoteLines.add(quoteLine);
    }

    return quoteLines;
  }

  private BigDecimal calculateSubtotal(List<QuoteLine> quoteLines) {
    return quoteLines.stream()
        .map(QuoteLine::getTotalPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private BigDecimal calculateTotalWithVat(BigDecimal subtotal, double vatRate) {
    BigDecimal vatMultiplier =
        BigDecimal.ONE.add(BigDecimal.valueOf(vatRate).divide(BigDecimal.valueOf(100)));
    return subtotal.multiply(vatMultiplier);
  }

  @Transactional(readOnly = true)
  public PageResponseDto<QuoteSummaryResponse> getAllQuotesPaginated(
      QuoteFilterRequest filter, int page, int size) {

    Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

    Page<Quote> quotePage =
        quoteRepository.getAllQuotesPaginated(
            filter.reference(),
            filter.startDate(),
            filter.endDate(),
            filter.customerId(),
            filter.minTotalAmount(),
            filter.maxTotalAmount(),
            pageable);

    List<QuoteSummaryResponse> quoteList =
        quotePage.stream().map(quoteMapper::toQuoteSummary).toList();

    return PageResponseDto.fromPage(quotePage, quoteList);
  }

  @Transactional(readOnly = true)
  public QuoteDetailResponse getQuoteDetailsById(Long id) {
    Quote quote =
        quoteRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("QUOTE_NOT_FOUND"));
    return quoteMapper.toDetailResponse(quote);
  }

  public Quote getQuoteById(Long id) {
    return quoteRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("QUOTE_NOT_FOUND"));
  }
}
