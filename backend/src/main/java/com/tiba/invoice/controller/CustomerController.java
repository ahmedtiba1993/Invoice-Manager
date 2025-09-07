package com.tiba.invoice.controller;

import com.tiba.invoice.dto.request.CustomerRequest;
import com.tiba.invoice.dto.response.ApiResponse;
import com.tiba.invoice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        Long customerId = customerService.addCustomer(customerRequest);
        ApiResponse<Long> response = ApiResponse.success(customerId, "CUSTOMER_CREATED_SUCCESSFULLY");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
