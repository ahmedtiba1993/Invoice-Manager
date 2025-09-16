package com.tiba.invoice.mapper;

import com.tiba.invoice.dto.request.FiscalConfigRequest;
import com.tiba.invoice.dto.response.FiscalConfigResponse;
import com.tiba.invoice.entity.FiscalConfig;
import org.springframework.stereotype.Service;

@Service
public class FiscalConfigMapper {

  public FiscalConfig toEntity(FiscalConfigRequest request) {
    return FiscalConfig.builder().vatRate(request.vatRate()).taxStamp(request.taxStamp()).build();
  }

  public FiscalConfigResponse toResponse(FiscalConfig entity) {
    return FiscalConfigResponse.builder()
        .id(entity.getId())
        .vatRate(entity.getVatRate())
        .taxStamp(entity.getTaxStamp())
        .build();
  }
}
