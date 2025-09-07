package com.tiba.invoice.mapper;

import com.tiba.invoice.dto.request.CustomerRequest;
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
}
