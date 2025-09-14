package com.tiba.invoice.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "default_seq", sequenceName = "product_seq", allocationSize = 1)
public class Product extends BaseEntity {

  @Column(name = "name", nullable = false, unique = true, length = 150)
  private String name;

  @Column(length = 1000)
  private String description;

  @Column(nullable = false, unique = true, length = 50)
  private String code;

  @Column(nullable = false)
  private Double price;

  @Column(nullable = false)
  private Boolean discountStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;
}
