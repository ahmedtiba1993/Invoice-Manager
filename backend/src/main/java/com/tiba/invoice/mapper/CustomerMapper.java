package com.tiba.invoice.mapper;

import com.tiba.invoice.dto.request.CustomerRequest;
import com.tiba.invoice.dto.response.CustomerResponse;
import com.tiba.invoice.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

  public Customer toEntity(CustomerRequest customerRequest) {

    if (customerRequest == null) {
      return null;
    }

    return Customer.builder()
        .firstName(customerRequest.firstName())
        .lastName(customerRequest.lastName())
        .businessName(customerRequest.businessName())
        .address(customerRequest.address())
        .phoneNumber(customerRequest.phoneNumber())
        .clientCode(customerRequest.clientCode())
        .discount(customerRequest.discount())
        .email(customerRequest.email())
        .build();
  }

  public void updateEntity(Customer customer, CustomerRequest customerRequest) {
    if (customer == null || customerRequest == null) {
      return;
    }

    customer.setFirstName(customerRequest.firstName());
    customer.setLastName(customerRequest.lastName());
    customer.setBusinessName(customerRequest.businessName());
    customer.setAddress(customerRequest.address());
    customer.setPhoneNumber(customerRequest.phoneNumber());
    customer.setClientCode(customerRequest.clientCode());
    customer.setDiscount(customerRequest.discount());
    customer.setEmail(customerRequest.email());
  }

  public CustomerResponse toResponse(Customer customer) {
    return CustomerResponse.builder()
        .id(customer.getId())
        .firstName(customer.getFirstName())
        .lastName(customer.getLastName())
        .businessName(customer.getBusinessName())
        .address(customer.getAddress())
        .phoneNumber(customer.getPhoneNumber())
        .clientCode(customer.getClientCode())
        .discount(customer.getDiscount())
        .email(customer.getEmail())
        .createdDate(customer.getCreatedDate())
        .build();
  }
}
