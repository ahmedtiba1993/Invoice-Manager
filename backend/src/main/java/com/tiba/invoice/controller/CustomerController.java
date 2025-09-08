package com.tiba.invoice.controller;

import com.tiba.invoice.dto.request.CustomerRequest;
import com.tiba.invoice.dto.response.ApiResponse;
import com.tiba.invoice.dto.response.CustomerResponse;
import com.tiba.invoice.dto.response.PageResponseDto;
import com.tiba.invoice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  @PostMapping
  public ResponseEntity<ApiResponse<Long>> createCustomer(
      @Valid @RequestBody CustomerRequest customerRequest) {
    Long customerId = customerService.addCustomer(customerRequest);
    ApiResponse<Long> response = ApiResponse.success(customerId, "CUSTOMER_CREATED_SUCCESSFULLY");
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<Long>> updateCustomer(
      @PathVariable Long id, @Valid @RequestBody CustomerRequest customerRequest) {

    Long updatedCustomerId = customerService.updateCustomer(id, customerRequest);

    ApiResponse<Long> response =
        ApiResponse.success(updatedCustomerId, "CUSTOMER_UPDATED_SUCCESSFULLY");

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<ApiResponse<PageResponseDto<CustomerResponse>>> getAllCustomersPaginated(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size) {
    PageResponseDto<CustomerResponse> customerPage =
        customerService.getAllCustomersPaginated(page, size);

    ApiResponse<PageResponseDto<CustomerResponse>> response =
        ApiResponse.success(customerPage, "CUSTOMERS_FETCHED_SUCCESSFULLY");

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAllCustomers() {
    List<CustomerResponse> customers = customerService.getAllCustomers();
    ApiResponse<List<CustomerResponse>> response =
        ApiResponse.success(customers, "ALL_CUSTOMERS_FETCHED");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
