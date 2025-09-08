package com.tiba.invoice.service;

import com.tiba.invoice.dto.request.CustomerRequest;
import com.tiba.invoice.entity.Customer;
import com.tiba.invoice.exception.DuplicateEntityException;
import com.tiba.invoice.mapper.CustomerMapper;
import com.tiba.invoice.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  public Long addCustomer(CustomerRequest customerRequest) {

    validateCustomerUniqueness(customerRequest, null);

    return customerRepository.save(customerMapper.toEntity(customerRequest)).getId();
  }

  public Long updateCustomer(Long id, CustomerRequest customerRequest) {

    Customer existingCustomer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("CUSTOMER_NOT_FOUND_WITH_ID_" + id));

    validateCustomerUniqueness(customerRequest, id);

    customerMapper.updateEntity(existingCustomer, customerRequest);

    return customerRepository.save(existingCustomer).getId();
  }

  private void validateCustomerUniqueness(CustomerRequest request, Long id) {
    List<String> errors = new ArrayList<>();

    customerRepository
        .findByEmail(request.email())
        .ifPresent(
            customer -> {
              if (id == null || !customer.getId().equals(id)) {
                errors.add("EMAIL_ALREADY_EXISTS");
              }
            });

    customerRepository
        .findByPhoneNumber(request.phoneNumber())
        .ifPresent(
            customer -> {
              if (id == null || !customer.getId().equals(id)) {
                errors.add("PHONE_NUMBER_ALREADY_EXISTS");
              }
            });

    customerRepository
        .findByClientCode(request.clientCode())
        .ifPresent(
            customer -> {
              if (id == null || !customer.getId().equals(id)) {
                errors.add("CLIENT_CODE_ALREADY_EXISTS");
              }
            });

    if (!errors.isEmpty()) {
      throw new DuplicateEntityException(errors);
    }
  }
}
