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
@SequenceGenerator(name = "default_seq", sequenceName = "category_seq", allocationSize = 1)
public class Category extends BaseEntity {

  @Column(nullable = false, unique = true, length = 100)
  private String name;

  @Column(length = 1000)
  private String description;
}
