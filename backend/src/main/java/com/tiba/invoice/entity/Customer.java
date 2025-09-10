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
@SequenceGenerator(name = "default_seq", sequenceName = "customer_seq", allocationSize = 1)
public class Customer extends BaseEntity {

  @Column(nullable = false, length = 50)
  private String firstName;

  @Column(nullable = false, length = 50)
  private String lastName;

  @Column(length = 100)
  private String businessName;

  @Column(nullable = false, length = 255)
  private String address;

  @Column(nullable = false, length = 8)
  private String phoneNumber;

  @Column(unique = true)
  private String clientCode;

  private Double discount;

  @Column(unique = true, nullable = false, length = 100)
  private String email;
}
