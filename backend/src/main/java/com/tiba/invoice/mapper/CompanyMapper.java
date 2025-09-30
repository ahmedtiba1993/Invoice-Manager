package com.tiba.invoice.mapper;

import com.tiba.invoice.dto.request.CompanyRequest;
import com.tiba.invoice.dto.response.CompanyResponse;
import com.tiba.invoice.entity.Company;
import org.springframework.stereotype.Service;

@Service
public class CompanyMapper {

  public Company toEntity(CompanyRequest request) {
    if (request == null) {
      return null;
    }
    return Company.builder()
        .companyName(request.companyName())
        .address(request.address())
        .taxNumber(request.taxNumber())
        .tradeRegister(request.tradeRegister())
        .telephone(request.telephone())
        .fax(request.fax())
        .mobileNumber(request.mobileNumber())
        .build();
  }

  public CompanyResponse toResponse(Company company) {
    if (company == null) {
      return null;
    }
    return CompanyResponse.builder()
        .companyName(company.getCompanyName())
        .address(company.getAddress())
        .taxNumber(company.getTaxNumber())
        .tradeRegister(company.getTradeRegister())
        .telephone(company.getTelephone())
        .fax(company.getFax())
        .mobileNumber(company.getMobileNumber())
        .build();
  }
}
