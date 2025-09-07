package com.tiba.invoice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(
        name = "default_seq",
        sequenceName = "customer_seq",
        allocationSize = 1
)
public class Customer extends BaseEntity{

    private String firstName;
    private String lastName;
    private String businessName;
    private String address;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String clientCode;

    private Double discount;

    @Column(unique = true)
    private String email;
}
