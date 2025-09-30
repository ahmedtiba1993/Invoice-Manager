package com.tiba.invoice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {
  private String companyName;
  private String address;
  private String taxNumber;
  private String tradeRegister;
  private String telephone;
  private String fax;
  private String mobileNumber;
}
