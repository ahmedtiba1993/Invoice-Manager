package com.tiba.invoice.controller;

import com.tiba.invoice.dto.request.FiscalConfigRequest;
import com.tiba.invoice.dto.response.ApiResponse;
import com.tiba.invoice.service.FiscalConfigService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fiscal-config")
@RequiredArgsConstructor
public class FiscalConfigController {

  private final FiscalConfigService fiscalConfigService;

  @PostMapping
  public ResponseEntity<ApiResponse<Long>> createFiscalConfig(
      @Valid @RequestBody FiscalConfigRequest fiscalConfigRequest) {
    Long fiscalConfigId = fiscalConfigService.createFiscalConfig(fiscalConfigRequest);
    ApiResponse<Long> response =
        ApiResponse.success(fiscalConfigId, "FISCAL_CONFIG_CREATED_SUCCESSFULLY");
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
