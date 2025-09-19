package com.tiba.invoice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class InvoiceDetailResponse {

  private Long id;
  private String reference;
  private LocalDate invoiceDate;
  private Boolean paymentStatus;
  private Double subtotal;
  private int vat;
  private double taxStamp;
  private Double totalAmount;
  private Customer customer;
  private List<InvoiceLine> invoiceLines;

  @Getter
  @Setter
  @Builder
  public static class Customer {
    private String firstName;
    private String lastName;
    private String businessName;
    private String email;
    private String address;
    private String phoneNumber;
  }

  @Getter
  @Setter
  @Builder
  public static class InvoiceLine {
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;
    private int discount;
    private BigDecimal totalPrice;
  }
}
