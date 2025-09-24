package com.tiba.invoice.controller;

import com.tiba.invoice.dto.request.ProductFilterRequest;
import com.tiba.invoice.dto.request.ProductRequest;
import com.tiba.invoice.dto.response.ApiResponse;
import com.tiba.invoice.dto.response.PageResponseDto;
import com.tiba.invoice.dto.response.ProductResponse;
import com.tiba.invoice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @PostMapping("/search")
  public ResponseEntity<ApiResponse<PageResponseDto<ProductResponse>>> getProductsPaginated(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      @RequestBody ProductFilterRequest filter) {

    PageResponseDto<ProductResponse> productPage =
        productService.getAllProductsPaginated(filter, page, size);
    ApiResponse<PageResponseDto<ProductResponse>> response =
        ApiResponse.success(productPage, "PRODUCTS_FETCHED_SUCCESSFULLY");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {

    List<ProductResponse> products = productService.getAllProducts();
    ApiResponse<List<ProductResponse>> response =
        ApiResponse.success(products, "ALL_PRODUCTS_FETCHED_SUCCESSFULLY");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
