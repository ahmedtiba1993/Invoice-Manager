package com.tiba.invoice.customer;

import com.tiba.invoice.common.PageResponse;
import com.tiba.invoice.exception.DuplicateEntityException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public Long save(@Valid CustomerRequest request) {

        if (customerRepository.findByCode(request.code()).isPresent()) {
            throw new DuplicateEntityException("CUSTOMER_CODE_ALREADY_EXISTS");
        }

        if (customerRepository.findByCommercialName(request.commercialName()).isPresent()) {
            throw new DuplicateEntityException("COMMERCIAL_NAME_ALREADY_EXISTS");
        }

        Customer customer = customerMapper.toCustomer(request);
        return customerRepository.save(customer).getId();
    }

    public PageResponse<CustomerResponse> findAllPaged(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Customer> customers = customerRepository.findAll(pageRequest);
        List<CustomerResponse> customerList = customers.stream().map(customerMapper::toResponse).toList();
        return new PageResponse<>(customerList, customers.getNumber(), customers.getSize(), customers.getTotalElements(), customers.getTotalPages(), customers.isFirst(), customers.isLast());
    }

}
