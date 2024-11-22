package com.tiba.invoice.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public Customer toCustomer(CustomerRequest customerRequest) {
        if (customerRequest == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setFirstName(customerRequest.firstName());
        customer.setLastName(customerRequest.lastName());
        customer.setCommercialName(customerRequest.commercialName());
        customer.setAddress(customerRequest.address());
        customer.setPhone(customerRequest.phone());
        customer.setCode(customerRequest.code());
        customer.setDiscount(customerRequest.discount());
        customer.setEmail(customerRequest.email());
        return customer;
    }

    public CustomerResponse toResponse(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setCode(customer.getCode());
        response.setCommercialName(customer.getCommercialName());
        response.setDiscount(customer.getDiscount());
        response.setPhone(customer.getPhone());
        return response;
    }
}
