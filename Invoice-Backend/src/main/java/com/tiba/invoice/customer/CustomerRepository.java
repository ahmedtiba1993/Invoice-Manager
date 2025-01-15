package com.tiba.invoice.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCode(String code);
    Optional<Customer> findByCommercialName(String commercialName);

}
