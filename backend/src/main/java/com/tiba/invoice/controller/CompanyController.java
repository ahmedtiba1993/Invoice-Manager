package com.tiba.invoice.controller;

import com.tiba.invoice.dto.request.CompanyRequest;
import com.tiba.invoice.dto.response.ApiResponse;
import com.tiba.invoice.dto.response.CompanyResponse;
import com.tiba.invoice.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {

  private final CompanyService companyService;

  @PostMapping
  public ResponseEntity<ApiResponse<Long>> addCompany(@Valid @RequestBody CompanyRequest request) {
    Long newCompanyId = companyService.addCompany(request);
    ApiResponse<Long> response = ApiResponse.success(newCompanyId, "COMPANY_CREATED_SUCCESSFULLY");
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<ApiResponse<CompanyResponse>> getCompany() {
    CompanyResponse company = companyService.getCompanyResponse();
    ApiResponse<CompanyResponse> response =
        ApiResponse.success(company, "COMPANY_FETCHED_SUCCESSFULLY");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<ApiResponse<Long>> updateCompany(
      @Valid @RequestBody CompanyRequest request) {
    Long updatedCompanyId = companyService.updateCompany(request);
    ApiResponse<Long> response =
        ApiResponse.success(updatedCompanyId, "COMPANY_UPDATED_SUCCESSFULLY");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
