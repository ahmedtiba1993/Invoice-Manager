package com.tiba.invoice.service;

import com.tiba.invoice.dto.request.CustomerRequest;
import com.tiba.invoice.dto.response.CustomerResponse;
import com.tiba.invoice.dto.response.PageResponseDto;
import com.tiba.invoice.entity.Customer;
import com.tiba.invoice.exception.DuplicateEntityException;
import com.tiba.invoice.mapper.CustomerMapper;
import com.tiba.invoice.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

  public PageResponseDto<CustomerResponse> getAllCustomersPaginated(int page, int size) {

    Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
    Page<Customer> customerPage = customerRepository.findAll(pageable);
    List<CustomerResponse> customerList =
        customerPage.stream().map(customerMapper::toResponse).toList();

    return PageResponseDto.fromPage(customerPage, customerList);
  }

  public List<CustomerResponse> getAllCustomers() {
    List<Customer> customers = customerRepository.findAll();
    return customers.stream().map(customerMapper::toResponse).collect(Collectors.toList());
  }
}
