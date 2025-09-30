package com.tiba.invoice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "default_seq", sequenceName = "company_seq", allocationSize = 1)
public class Company extends BaseEntity {

  @Column(name = "company_name", nullable = false, length = 150)
  private String companyName;

  @Column(nullable = false, length = 255)
  private String address;

  @Column(name = "tax_number", nullable = false, unique = true, length = 50)
  private String taxNumber; // Matricule fiscal

  @Column(name = "trade_register", nullable = false, unique = true, length = 50)
  private String tradeRegister; // Registre de commerce

  @Column(length = 20)
  private String telephone;

  @Column(length = 20)
  private String fax;

  @Column(length = 20)
  private String mobileNumber;
}
