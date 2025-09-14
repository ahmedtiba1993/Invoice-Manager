package com.tiba.invoice.controller;

import com.tiba.invoice.dto.request.ProductRequest;
import com.tiba.invoice.dto.response.ApiResponse;
import com.tiba.invoice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<ApiResponse<Long>> addProduct(@Valid @RequestBody ProductRequest request) {
    Long newProductId = productService.addProduct(request);
    ApiResponse<Long> response = ApiResponse.success(newProductId, "PRODUCT_CREATED_SUCCESSFULLY");
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
