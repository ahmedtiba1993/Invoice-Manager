package com.tiba.invoice.service;

import com.tiba.invoice.dto.request.CustomerRequest;
import com.tiba.invoice.entity.Customer;
import com.tiba.invoice.exception.DuplicateEntityException;
import com.tiba.invoice.mapper.CustomerMapper;
import com.tiba.invoice.repository.CustomerRepository;
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

        List<String> errors = new ArrayList<>();

        if (customerRepository.existsByEmail(customerRequest.email())) {
            errors.add("EMAIL_ALREADY_EXISTS");
        }

        if (customerRepository.existsByClientCode(customerRequest.clientCode())) {
            errors.add("CLIENTCODE_ALREADY_EXISTS");
        }

        if (!errors.isEmpty()) {
            throw new DuplicateEntityException(errors);
        }


        return customerRepository.save(customerMapper.toEntity(customerRequest)).getId();
    }
}
