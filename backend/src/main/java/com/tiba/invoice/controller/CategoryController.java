package com.tiba.invoice.controller;

import com.tiba.invoice.dto.request.CategoryRequest;
import com.tiba.invoice.dto.response.ApiResponse;
import com.tiba.invoice.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping
  public ResponseEntity<ApiResponse<Long>> createCategory(
      @Valid @RequestBody CategoryRequest request) {

    Long newCategoryId = categoryService.addCategory(request);
    ApiResponse<Long> response =
        ApiResponse.success(newCategoryId, "CATEGORY_CREATED_SUCCESSFULLY");
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
