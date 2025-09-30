package com.tiba.invoice.service;

import com.tiba.invoice.dto.request.CompanyRequest;
import com.tiba.invoice.dto.response.CompanyResponse;
import com.tiba.invoice.entity.Company;
import com.tiba.invoice.exception.DuplicateEntityException;
import com.tiba.invoice.mapper.CompanyMapper;
import com.tiba.invoice.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

  private final CompanyRepository companyRepository;
  private final CompanyMapper companyMapper;

  public Long addCompany(CompanyRequest request) {
    if (companyRepository.count() > 0) {
      throw new RuntimeException("COMPANY_ALREADY_EXISTS");
    }

    return companyRepository.save(companyMapper.toEntity(request)).getId();
  }

  public CompanyResponse getCompanyResponse() {
    return companyRepository.findAll().stream()
        .findFirst()
        .map(companyMapper::toResponse)
        .orElseThrow(() -> new RuntimeException("COMPANY_NOT_FOUND"));
  }

  public Company getCompany() {
    return companyRepository.findAll().stream()
        .findFirst()
        .orElseThrow(() -> new RuntimeException("COMPANY_NOT_FOUND"));
  }

  public Long updateCompany(CompanyRequest request) {
    Company company = getCompany();

    company.setCompanyName(request.companyName());
    company.setAddress(request.address());
    company.setTaxNumber(request.taxNumber());
    company.setTradeRegister(request.tradeRegister());
    company.setTelephone(request.telephone());
    company.setFax(request.fax());
    company.setMobileNumber(request.mobileNumber());

    return companyRepository.save(company).getId();
  }
}
