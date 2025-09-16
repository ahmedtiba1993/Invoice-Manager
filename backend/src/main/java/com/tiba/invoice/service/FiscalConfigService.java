package com.tiba.invoice.service;

import com.tiba.invoice.dto.request.FiscalConfigRequest;
import com.tiba.invoice.entity.FiscalConfig;
import com.tiba.invoice.mapper.FiscalConfigMapper;
import com.tiba.invoice.repository.fiscalConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FiscalConfigService {

  private final FiscalConfigMapper fiscalConfigMapper;
  private final fiscalConfigRepository fiscalConfigRepository;

  public Long createFiscalConfig(FiscalConfigRequest request) {

    Optional<FiscalConfig> existingConfig = fiscalConfigRepository.findAll().stream().findFirst();
    FiscalConfig fiscalConfig = new FiscalConfig();
    if (existingConfig.isPresent()) {
      fiscalConfig = existingConfig.get();
      fiscalConfig.setVatRate(request.vatRate());
      fiscalConfig.setTaxStamp(request.taxStamp());
    } else {
      fiscalConfig = fiscalConfigMapper.toEntity(request);
    }
    return fiscalConfigRepository.save(fiscalConfig).getId();
  }

  public FiscalConfig getFiscalConfig() {
    return fiscalConfigRepository.findAll().stream()
        .findFirst()
        .orElseThrow(() -> new RuntimeException("FISCAL_CONFIG_NOT_FOUND"));
  }
}
